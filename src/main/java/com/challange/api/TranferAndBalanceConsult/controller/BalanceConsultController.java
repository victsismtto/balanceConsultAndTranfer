package com.challange.api.TranferAndBalanceConsult.controller;

import com.challange.api.TranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.service.TransferBalanceConsultService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/account")
public class BalanceConsultController {

    @Autowired private TransferBalanceConsultService service;

    @GetMapping("/balance/consult")
    public ResponseEntity<ResponseBalanceConsultDTO> consultBalance(@RequestBody RequestBalanceConsultDTO requestDTO) {
        log.info("Entering in the controller - Balance Account");
        ResponseBalanceConsultDTO response = service.consultBalance(requestDTO);
        log.info("leaving in the controller - Balance Account");
        return ResponseEntity.ok().body(response);
    }
}
