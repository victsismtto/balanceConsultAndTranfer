package com.challange.api.TranferAndBalanceConsult.client;

import com.challange.api.TranferAndBalanceConsult.model.dto.APICadastroDTO;
import org.springframework.stereotype.Component;

@Component
public class APICadastroClient {

    public APICadastroDTO requestToAPICadastro(String id) {
        return APICadastroDTO.builder().name("Client_1").build();
    }
}
