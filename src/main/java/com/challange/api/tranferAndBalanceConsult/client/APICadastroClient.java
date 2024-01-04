package com.challange.api.tranferAndBalanceConsult.client;

import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.exception.ServiceUnavailableException;
import com.challange.api.tranferAndBalanceConsult.model.dto.APICadastroDTO;
import com.challange.api.tranferAndBalanceConsult.utils.MessageUtils;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Configuration
public class APICadastroClient {

    @Value("${rest.cadastro}")
    private String cadastroEndpoint;
    @Autowired private RestTemplate restTemplate;

    @Retry(name = "clientRetry")
    public APICadastroDTO requestToAPICadastro(String id) {
        try {
            log.info("preparing to call the enpoint cadastro");
            String cadastroUrl = cadastroEndpoint + "/" + id;
            ResponseEntity<APICadastroDTO> response = restTemplate.exchange(cadastroUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), APICadastroDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new ServiceUnavailableException(MessageUtils.SERVICE_UNAVAILABLE);
        }
    }
}
