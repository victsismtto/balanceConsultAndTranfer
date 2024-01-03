package com.challange.api.tranferAndBalanceConsult.service;

import com.challange.api.tranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.ResponseCheckingAccountTransferDTO;

public interface AccountService {

    ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestDTO) throws Exception;
    ResponseCheckingAccountTransferDTO checkingAccountTransfer(RequestCheckingAccountTransferDTO requestDTO) throws Exception;

    void schedule();
}
