package com.challange.api.TranferAndBalanceConsult.service.impl;

import com.challange.api.TranferAndBalanceConsult.client.APICadastroClient;
import com.challange.api.TranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.TranferAndBalanceConsult.mapper.AccountMapper;
import com.challange.api.TranferAndBalanceConsult.model.dto.*;
import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import com.challange.api.TranferAndBalanceConsult.model.entity.CheckingAccountTranferEntity;
import com.challange.api.TranferAndBalanceConsult.repository.BalanceConsultRepository;
import com.challange.api.TranferAndBalanceConsult.repository.CheckingAccountRepository;
import com.challange.api.TranferAndBalanceConsult.service.AccountService;
import com.challange.api.TranferAndBalanceConsult.validator.CheckingAccountValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private APICadastroClient cadastroClient;
    @Autowired private AccountMapper accountMapper;
    @Autowired private BalanceConsultRepository consultRepository;
    @Autowired private CheckingAccountRepository checkingAccountRepository;
    @Autowired private CheckingAccountValidator checkingAccountValidator;

    @Override
    public ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestBalanceDTO) throws Exception {
        log.info("Entering in the service - Balance Consult");

        Optional<BalanceConsultEntity> balanceConsultEntity = consultRepository.findByIssuerAndNumber(requestBalanceDTO.getIssuer(), requestBalanceDTO.getNumber());
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
        Optional<CheckingAccountTranferEntity> transferEntity = checkingAccountRepository.findByName(apiCadastroDTO.getName());
        Optional<CheckingAccountTranferEntity> receiveEntity = checkingAccountRepository.findByIssuerAndNumber(requestDTO.getCheckingAccountTo().getIssuer(), requestDTO.getCheckingAccountTo().getNumber());
        if (transferEntity.isEmpty() || receiveEntity.isEmpty()) {
            throw new NotFoundException();
        }
        checkingAccountValidator.transferAndReceiverAccountValidations(transferEntity, receiveEntity, requestDTO);
        //Mock BACEN here
        checkingAccountRepository.save(transferEntity.get());
        checkingAccountRepository.save(receiveEntity.get());
        return accountMapper.toCheckingAccountTransferResponse();
    }
}
