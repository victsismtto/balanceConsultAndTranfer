package com.challange.api.tranferAndBalanceConsult.client;

import com.challange.api.tranferAndBalanceConsult.exception.BusinessException;
import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.exception.ServiceUnavailableException;
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
public class APICadastroClientTest {

    @InjectMocks private APICadastroClient client;

    @Mock private RestTemplate restTemplate;

    @Test
    void testRequestToAPICadastro() {

        ReflectionTestUtils.setField(client, "cadastroEndpoint", "http://localhost:8082/cadastro/consult/");

        APICadastroDTO apiCadastroDTO = new APICadastroDTO();
        apiCadastroDTO.setName("teste");
        ResponseEntity<APICadastroDTO> responseEntity = new ResponseEntity<>(apiCadastroDTO, new HttpHeaders(), HttpStatus.OK);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class) ,Mockito.any(Class.class))).thenReturn(responseEntity);

        client.requestToAPICadastro("id");
    }

    @Test
    void testRequestToAPICadastroError404() {

        ReflectionTestUtils.setField(client, "cadastroEndpoint", "http://localhost:8082/cadastro/consult/");

        APICadastroDTO apiCadastroDTO = new APICadastroDTO();
        apiCadastroDTO.setName("teste");

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Assertions.assertThrows(NotFoundException.class, () ->
        client.requestToAPICadastro("id"));
    }

    @Test
    void testRequestToAPICadastroError503() {

        ReflectionTestUtils.setField(client, "cadastroEndpoint", "http://localhost:8082/cadastro/consult/");

        APICadastroDTO apiCadastroDTO = new APICadastroDTO();
        apiCadastroDTO.setName("teste");

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

        Assertions.assertThrows(ServiceUnavailableException.class, () ->
                client.requestToAPICadastro("id"));
    }
}
