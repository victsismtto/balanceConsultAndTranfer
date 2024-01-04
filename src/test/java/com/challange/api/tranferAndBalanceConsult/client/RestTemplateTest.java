package com.challange.api.tranferAndBalanceConsult.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestTemplateTest {

    @InjectMocks
    private RestTemplateConfig restTemplateConfig;

    @Test
    void testRestTemplate() {
        restTemplateConfig.restTemplate();
    }
}
