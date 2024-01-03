package com.challange.api.tranferAndBalanceConsult.client;

import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.exception.ServiceUnavailableException;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import com.challange.api.tranferAndBalanceConsult.model.dto.APICadastroDTO;
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

        ResponseEntity<String> responseEntity = new ResponseEntity<>("teste", new HttpHeaders(), HttpStatus.OK);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class) ,Mockito.any(Class.class))).thenReturn(responseEntity);

        client.requestToAPIBacen(checkingAccountTo, checkingAccountFrom);
    }

    @Test
    void testRequestToAPIBacenError404() {

        ReflectionTestUtils.setField(client, "bacenEndpoint", "http://localhost:8081/transaction/informed");

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();


        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Assertions.assertDoesNotThrow(
            () -> client.requestToAPIBacen (checkingAccountTo, checkingAccountFrom));
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


        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

        Assertions.assertThrows(ServiceUnavailableException.class, () ->
                client.requestToAPIBacen (checkingAccountTo, checkingAccountFrom));
    }
}
