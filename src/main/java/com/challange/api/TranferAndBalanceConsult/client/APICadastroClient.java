package com.challange.api.TranferAndBalanceConsult.client;

import com.challange.api.TranferAndBalanceConsult.model.dto.APICadastroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class APICadastroClient {

    @Value("${rest.cadastro}")
    private String cadastroEndpoint;
    @Autowired private RestTemplate restTemplate;

    public APICadastroDTO requestToAPICadastro(String id) {

        String cadastroUrl = cadastroEndpoint + "/" + id;

        ResponseEntity<APICadastroDTO> response = restTemplate.exchange(cadastroUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), APICadastroDTO.class);

        return response.getBody();
    }
}
