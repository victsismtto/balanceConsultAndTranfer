package com.challange.api.TranferAndBalanceConsult.service.impl;

import com.challange.api.TranferAndBalanceConsult.mapper.AccountMapper;
import com.challange.api.TranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import com.challange.api.TranferAndBalanceConsult.repository.BalanceConsultRepository;
import com.challange.api.TranferAndBalanceConsult.service.TransferBalanceConsultService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;

@Log4j2
@Service
public class TransferBalanceConsultServiceImpl implements TransferBalanceConsultService {

    @Autowired private BalanceConsultRepository consultRepository;
    @Autowired private AccountMapper accountMapper;
    @Override
    public ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestBalanceDTO) {
        log.info("Entering in the service - Balance Consult");

        BalanceConsultEntity balanceConsultEntity = consultRepository.findByIssuerAndNumber(requestBalanceDTO.getIssuer(), requestBalanceDTO.getNumber());
        ResponseBalanceConsultDTO responseBalanceConsultDTO = accountMapper.toBalanceConsultResponse(balanceConsultEntity);
        responseBalanceConsultDTO.setData(LocalDateTime.now().toString());

        log.info("Leaving in the service - Balance Consult");
        return responseBalanceConsultDTO;
    }
}
