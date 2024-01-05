package com.challange.api.tranferAndBalanceConsult.client;


import com.challange.api.tranferAndBalanceConsult.exception.BadRequestException;
import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.exception.ServiceUnavailableException;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
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

@Configuration
@Log4j2
public class APIBacenClient {

    @Value("${rest.bacen}")
    private String bacenEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    @Retry(name = "clientRetry")
    public String requestToAPIBacen(CheckingAccountTo accountTo, CheckingAccountFrom accountFrom, Double transferAmount) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("issuerTo", accountTo.getIssuer());
            headers.set("numberTo", accountTo.getNumber());
            headers.set("issuerFrom", accountFrom.getIssuer());
            headers.set("numberFrom", accountFrom.getNumber());
            headers.set("transferAmount", String.valueOf(transferAmount));
            ResponseEntity<String> response = restTemplate.exchange(bacenEndpoint, HttpMethod.POST, new HttpEntity<>(headers), String.class);
            log.info(response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 429) {
                return null;
            }
            throw new BadRequestException(MessageUtils.BAD_REQUEST_BACEN);

        } catch (Exception e) {
            throw new ServiceUnavailableException(MessageUtils.SERVICE_UNAVAILABLE);
        }

    }
}
