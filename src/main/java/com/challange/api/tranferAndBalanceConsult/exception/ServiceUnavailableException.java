package com.challange.api.tranferAndBalanceConsult.exception;

import com.challange.api.tranferAndBalanceConsult.utils.MessageUtils;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
