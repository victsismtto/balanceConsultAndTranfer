package com.challange.api.TranferAndBalanceConsult.exception;

import com.challange.api.TranferAndBalanceConsult.utils.MessageUtils;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super(MessageUtils.NO_RECORDS_FOUND);
    }
}
