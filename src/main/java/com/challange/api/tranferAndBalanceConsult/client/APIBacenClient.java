package com.challange.api.tranferAndBalanceConsult.client;


import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.exception.ServiceUnavailableException;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import com.challange.api.tranferAndBalanceConsult.model.dto.APICadastroDTO;
import com.challange.api.tranferAndBalanceConsult.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.web.client.RestTemplate;

@Retryable(maxAttemptsExpression = "retry.max-attempts",
        backoff =
        @Backoff(delayExpression = "retry.delay",
                maxDelay = 2000,
                multiplier = 2))
@Configuration
@Log4j2
public class APIBacenClient {

    @Value("${rest.bacen}")
    private String bacenEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    public void requestToAPIBacen(CheckingAccountTo accountTo, CheckingAccountFrom accountFrom) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("issuerTo", accountTo.getIssuer());
            headers.set("numberTo", accountTo.getNumber());
            headers.set("issuerFrom", accountFrom.getIssuer());
            headers.set("numberFrom", accountFrom.getNumber());
            ResponseEntity<String> response = restTemplate.exchange(bacenEndpoint, HttpMethod.POST, new HttpEntity<>(headers), String.class);
            log.info(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new ServiceUnavailableException(MessageUtils.SERVICE_UNAVAILABLE);
        }

    }
}
