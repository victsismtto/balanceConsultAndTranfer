package com.challange.api.tranferAndBalanceConsult.service;

import com.challange.api.tranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.ResponseCheckingAccountDTO;

public interface AccountService {

    ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestDTO) throws Exception;
    ResponseCheckingAccountDTO checkingAccountTransfer(RequestCheckingAccountDTO requestDTO) throws Exception;

    void schedule();
}
