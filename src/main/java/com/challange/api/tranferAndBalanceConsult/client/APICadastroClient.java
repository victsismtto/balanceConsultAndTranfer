package com.challange.api.tranferAndBalanceConsult.client;

import com.challange.api.tranferAndBalanceConsult.exception.BusinessException;
import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.exception.ServiceUnavailableException;
import com.challange.api.tranferAndBalanceConsult.model.dto.APICadastroDTO;
import com.challange.api.tranferAndBalanceConsult.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Retryable(maxAttemptsExpression = "retry.max-attempts",
           backoff =
               @Backoff(delayExpression = "retry.delay",
               maxDelay = 2000,
               multiplier = 2))
@Configuration
public class APICadastroClient {

    @Value("${rest.cadastro}")
    private String cadastroEndpoint;
    @Autowired private RestTemplate restTemplate;

    public APICadastroDTO requestToAPICadastro(String id) {
        try {
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
