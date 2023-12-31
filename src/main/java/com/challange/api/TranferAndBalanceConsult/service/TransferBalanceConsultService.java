package com.challange.api.TranferAndBalanceConsult.service;

import com.challange.api.TranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;

public interface TransferBalanceConsultService {

    ResponseBalanceConsultDTO consultBalance(RequestBalanceConsultDTO requestBalanceDTO);
}
