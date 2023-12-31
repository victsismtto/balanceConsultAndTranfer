package com.challange.api.tranferAndBalanceConsult.service.impl;

import com.challange.api.tranferAndBalanceConsult.client.APIBacenClient;
import com.challange.api.tranferAndBalanceConsult.client.APICadastroClient;
import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.mapper.AccountMapper;
import com.challange.api.tranferAndBalanceConsult.model.dto.*;
import com.challange.api.tranferAndBalanceConsult.model.entity.BacenTransferEntity;
import com.challange.api.tranferAndBalanceConsult.model.entity.AccountsEntity;
import com.challange.api.tranferAndBalanceConsult.repository.BacenRepository;
import com.challange.api.tranferAndBalanceConsult.repository.TransferAndBalanceRepository;
import com.challange.api.tranferAndBalanceConsult.service.AccountService;
import com.challange.api.tranferAndBalanceConsult.validator.CheckingAccountValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private APICadastroClient cadastroClient;
    @Autowired private APIBacenClient bacenClient;
    @Autowired private AccountMapper accountMapper;
    @Autowired private TransferAndBalanceRepository transferAndBalanceRepository;
    @Autowired private BacenRepository bacenRepository;
    @Autowired private CheckingAccountValidator checkingAccountValidator;

    @Override
    public ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestBalanceDTO) throws Exception {
        log.info("Entering in the service - Balance Consult");

        CompletableFuture<Optional<AccountsEntity>> balanceConsultEntity = CompletableFuture.supplyAsync(() -> transferAndBalanceRepository.findByIssuerAndNumber(requestBalanceDTO.getIssuer(), requestBalanceDTO.getNumber()));
        if (balanceConsultEntity.get().isEmpty()) {
            throw new NotFoundException();
        }
        ResponseBalanceConsultDTO responseBalanceConsultDTO = accountMapper.toBalanceConsultResponse(balanceConsultEntity.get().get());
        log.info("Leaving in the service - Balance Consult");
        return responseBalanceConsultDTO;
    }

    @Override
    public ResponseCheckingAccountDTO checkingAccountTransfer(RequestCheckingAccountDTO requestDTO) throws Exception {

        APICadastroDTO apiCadastroDTO = cadastroClient.requestToAPICadastro(requestDTO.getIdBank());
        CompletableFuture<Optional<AccountsEntity>> transferEntity = CompletableFuture.supplyAsync(() -> transferAndBalanceRepository.findByName(apiCadastroDTO.getName())) ;
        CompletableFuture<Optional<AccountsEntity>> receiveEntity = CompletableFuture.supplyAsync(() -> transferAndBalanceRepository.findByIssuerAndNumber(requestDTO.getCheckingAccountTo().getIssuer(), requestDTO.getCheckingAccountTo().getNumber()));
        if (transferEntity.get().isEmpty() || receiveEntity.get().isEmpty()) {
            throw new NotFoundException();
        }
        checkingAccountValidator.transferAndReceiverAccountValidations(transferEntity.get().get(), receiveEntity.get().get(), requestDTO);
        BacenTransferEntity bacenTransferEntity = accountMapper.toBacenEntity(requestDTO);
        bacenRepository.save(bacenTransferEntity);
        String response = bacenClient.requestToAPIBacen(requestDTO.getCheckingAccountTo(), requestDTO.getCheckingAccountFrom(), requestDTO.getTransferAmount());
        verificationTransfer(response, bacenTransferEntity);
        transferAndBalanceRepository.save(transferEntity.get().get());
        transferAndBalanceRepository.save(receiveEntity.get().get());
        return accountMapper.toCheckingAccountTransferResponse();
    }

    @Override
    public void schedule() {
        Optional<List<BacenTransferEntity>> transferEntity = bacenRepository.findByCompletedIsFalse();
        if (transferEntity.isPresent()){
            for (BacenTransferEntity transfer : transferEntity.get()) {
                String bacenResponse = bacenClient.requestToAPIBacen(transfer.getAccountTo(), transfer.getAccountFrom(), transfer.getTransactionAmount());
                verificationTransfer(bacenResponse, transfer);
            }
        }
    }

    private void verificationTransfer(String response, BacenTransferEntity entity) {
        if (response != null) {
            entity.setCompleted(true);
            bacenRepository.save(entity);
        }
    }
}
