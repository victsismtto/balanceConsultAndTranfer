package com.challange.api.tranferAndBalanceConsult.client;

import com.challange.api.tranferAndBalanceConsult.model.dto.APICadastroDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
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
}
