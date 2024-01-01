package com.challange.api.TranferAndBalanceConsult.service;

import com.challange.api.TranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseCheckingAccountTransferDTO;

public interface AccountService {

    ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestDTO) throws Exception;
    ResponseCheckingAccountTransferDTO checkingAccountTransfer(RequestCheckingAccountTransferDTO requestDTO) throws Exception;
}
