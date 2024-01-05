package com.challange.api.tranferAndBalanceConsult.client;

import com.challange.api.tranferAndBalanceConsult.exception.BadRequestException;
import com.challange.api.tranferAndBalanceConsult.exception.ServiceUnavailableException;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class APIBacenClientTest {

    @InjectMocks
    private APIBacenClient client;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void testRequestToAPIBacen() {

        ReflectionTestUtils.setField(client, "bacenEndpoint", "http://localhost:8081/transaction/informed");

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        Double transferAmount = 200.00;

        ResponseEntity<String> responseEntity = new ResponseEntity<>("teste", new HttpHeaders(), HttpStatus.OK);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class) ,Mockito.any(Class.class))).thenReturn(responseEntity);

        client.requestToAPIBacen(checkingAccountTo, checkingAccountFrom, transferAmount);
    }

    @Test
    void testRequestToAPIBacenError429() {

        ReflectionTestUtils.setField(client, "bacenEndpoint", "http://localhost:8081/transaction/informed");

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        Double transferAmount = 200.00;


        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

        client.requestToAPIBacen(checkingAccountTo, checkingAccountFrom, transferAmount);
    }

    @Test
    void testRequestToAPIBacenError400() {

        ReflectionTestUtils.setField(client, "bacenEndpoint", "http://localhost:8081/transaction/informed");

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        Double transferAmount = 200.00;


        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        Assertions.assertThrows(BadRequestException.class, () ->
                client.requestToAPIBacen (checkingAccountTo, checkingAccountFrom, transferAmount));
    }

    @Test
    void testRequestToAPIBacenError503() {

        ReflectionTestUtils.setField(client, "bacenEndpoint", "http://localhost:8081/transaction/informed");

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        Double transferAmount = 200.00;


        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

        Assertions.assertThrows(ServiceUnavailableException.class, () ->
                client.requestToAPIBacen (checkingAccountTo, checkingAccountFrom, transferAmount));
    }
}
