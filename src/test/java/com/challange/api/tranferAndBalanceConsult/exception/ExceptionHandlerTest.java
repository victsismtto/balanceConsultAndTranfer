package com.challange.api.tranferAndBalanceConsult.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerTest {

    @InjectMocks ExceptionsHandler exceptionsHandler;

    @Test
    void handleSecurityBusinessTest() {
        exceptionsHandler.handleSecurity(new BusinessException("message"));
    }

    @Test
    void handleSecurityServiceUnavailableTest() {
        exceptionsHandler.handleSecurity(new ServiceUnavailableException("message"));
    }

    @Test
    void handleSecurityNotFoundTest() {
        exceptionsHandler.handleSecurity(new NotFoundException());
    }

    @Test
    void handleSecurityBadRequestTest() {
        exceptionsHandler.handleSecurity(new BadRequestException("message"));
    }
}
