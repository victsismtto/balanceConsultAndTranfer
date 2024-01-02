package com.challange.api.tranferAndBalanceConsult.exception;

import com.challange.api.tranferAndBalanceConsult.utils.MessageUtils;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super(MessageUtils.NO_RECORDS_FOUND);
    }
}
