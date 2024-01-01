package com.challange.api.TranferAndBalanceConsult.controller;

import com.challange.api.TranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseCheckingAccountTransferDTO;
import com.challange.api.TranferAndBalanceConsult.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired private AccountService service;

    @GetMapping("/balance/consult")
    public ResponseEntity<ResponseBalanceConsultDTO> consultBalance(@RequestBody RequestBalanceConsultDTO requestDTO) throws Exception {
        log.info("Entering in the controller - Balance Account");
        ResponseBalanceConsultDTO response = service.consultBalance(requestDTO);
        log.info("leaving in the controller - Balance Account");
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/transfer")
    public ResponseEntity<ResponseCheckingAccountTransferDTO> checkingAccountTransfer(@RequestBody RequestCheckingAccountTransferDTO requestDTO) throws Exception {
        log.info("Entering in the controller - Account transfer");
        ResponseCheckingAccountTransferDTO response = service.checkingAccountTransfer(requestDTO);
        log.info("leaving in the controller - Account transfer");
        return ResponseEntity.ok().body(response);
    }
}
