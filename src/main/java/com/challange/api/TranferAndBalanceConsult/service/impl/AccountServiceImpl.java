package com.challange.api.TranferAndBalanceConsult.service.impl;

import com.challange.api.TranferAndBalanceConsult.client.APICadastroClient;
import com.challange.api.TranferAndBalanceConsult.enuns.DailyLimit;
import com.challange.api.TranferAndBalanceConsult.mapper.AccountMapper;
import com.challange.api.TranferAndBalanceConsult.model.dto.*;
import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import com.challange.api.TranferAndBalanceConsult.model.entity.CheckingAccountTranferEntity;
import com.challange.api.TranferAndBalanceConsult.repository.BalanceConsultRepository;
import com.challange.api.TranferAndBalanceConsult.repository.CheckingAccountRepository;
import com.challange.api.TranferAndBalanceConsult.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired private APICadastroClient cadastroClient;
    @Autowired private AccountMapper accountMapper;
    @Autowired private BalanceConsultRepository consultRepository;
    @Autowired private CheckingAccountRepository checkingAccountRepository;

    @Override
    public ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestBalanceDTO) {
        log.info("Entering in the service - Balance Consult");

        BalanceConsultEntity balanceConsultEntity = consultRepository.findByIssuerAndNumber(requestBalanceDTO.getIssuer(), requestBalanceDTO.getNumber());
        ResponseBalanceConsultDTO responseBalanceConsultDTO = accountMapper.toBalanceConsultResponse(balanceConsultEntity);

        log.info("Leaving in the service - Balance Consult");
        return responseBalanceConsultDTO;
    }

    @Override
    public ResponseCheckingAccountTransferDTO checkingAccountTransfer(RequestCheckingAccountTransferDTO requestDTO) throws Exception {

        APICadastroDTO apiCadastroDTO = cadastroClient.requestToAPICadastro(requestDTO.getIdBank());
        CheckingAccountTranferEntity transferEntity = checkingAccountRepository.findByName(apiCadastroDTO.getName());
//        LocalDateTime today = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDateTime localDateTime = LocalDateTime.parse(transferEntity.getDate(), formatter);
        //jogar erro caso nao ache a conta
//        if (localDateTime.isBefore(today)) {
//            transferEntity.setDailyLimitUsed(0.00);
//            transferEntity.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        }
        if (!transferEntity.getIsActive()){
            throw new Exception();      //tratar esse erro depois
        }
        double transferAmount = requestDTO.getTransferAmount();
        double amountAvailable = transferEntity.getBalance();
        int compareAmountAvailable = Double.compare(transferAmount, amountAvailable);
        if (compareAmountAvailable > 0) {
            throw new Exception();      //tratar esse erro depois
        }
        double resultBalance = amountAvailable - transferAmount;

        double limitUsed = transferEntity.getDailyLimitUsed();
        limitUsed = limitUsed + transferAmount;
        double maxAmountDaily = DailyLimit.MAX_VALUE.toDouble();
        int compareLimitDaily = Double.compare(limitUsed, maxAmountDaily);
        if (compareLimitDaily > 0) {
            throw new Exception();      //tratar esse erro depois
        }
        //Preciso ainda fazer a notificacao para o Bacen
        transferEntity.setDailyLimitUsed(limitUsed);
        transferEntity.setBalance(resultBalance);
        checkingAccountRepository.save(transferEntity);
        //Preciso descobrir porque o Mongo nao atualiza e cria outra entidade
        //Preciso fazer que o dinheiro chegue na outra conta

        return accountMapper.toCheckingAccountTransferResponse();
    }
}
