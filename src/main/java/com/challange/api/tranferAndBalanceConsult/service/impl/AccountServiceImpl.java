package com.challange.api.tranferAndBalanceConsult.service.impl;

import com.challange.api.tranferAndBalanceConsult.client.APICadastroClient;
import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.mapper.AccountMapper;
import com.challange.api.tranferAndBalanceConsult.model.dto.*;
import com.challange.api.tranferAndBalanceConsult.model.entity.TransferAndBalanceConsultEntity;
import com.challange.api.tranferAndBalanceConsult.repository.TransferAndBalanceRepository;
import com.challange.api.tranferAndBalanceConsult.service.AccountService;
import com.challange.api.tranferAndBalanceConsult.validator.CheckingAccountValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private APICadastroClient cadastroClient;
    @Autowired private AccountMapper accountMapper;
    @Autowired private TransferAndBalanceRepository transferAndBalanceRepository;
    @Autowired private CheckingAccountValidator checkingAccountValidator;

    @Override
    public ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestBalanceDTO) throws Exception {
        log.info("Entering in the service - Balance Consult");

        Optional<TransferAndBalanceConsultEntity> balanceConsultEntity = transferAndBalanceRepository.findByIssuerAndNumber(requestBalanceDTO.getIssuer(), requestBalanceDTO.getNumber());
        if (balanceConsultEntity.isEmpty()) {
            throw new NotFoundException();
        }
        ResponseBalanceConsultDTO responseBalanceConsultDTO = accountMapper.toBalanceConsultResponse(balanceConsultEntity.get());
        log.info("Leaving in the service - Balance Consult");
        return responseBalanceConsultDTO;
    }

    @Override
    public ResponseCheckingAccountTransferDTO checkingAccountTransfer(RequestCheckingAccountTransferDTO requestDTO) throws Exception {

        APICadastroDTO apiCadastroDTO = cadastroClient.requestToAPICadastro(requestDTO.getIdBank()); //mock API Cadastro
        Optional<TransferAndBalanceConsultEntity> transferEntity = transferAndBalanceRepository.findByName(apiCadastroDTO.getName());
        Optional<TransferAndBalanceConsultEntity> receiveEntity = transferAndBalanceRepository.findByIssuerAndNumber(requestDTO.getCheckingAccountTo().getIssuer(), requestDTO.getCheckingAccountTo().getNumber());
        if (transferEntity.isEmpty() || receiveEntity.isEmpty()) {
            throw new NotFoundException();
        }
        checkingAccountValidator.transferAndReceiverAccountValidations(transferEntity, receiveEntity, requestDTO);
        //Mock BACEN here
        transferAndBalanceRepository.save(transferEntity.get());
        transferAndBalanceRepository.save(receiveEntity.get());
        return accountMapper.toCheckingAccountTransferResponse();
    }
}
