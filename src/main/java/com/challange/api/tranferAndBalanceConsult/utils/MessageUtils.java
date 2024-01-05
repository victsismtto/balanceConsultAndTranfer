package com.challange.api.tranferAndBalanceConsult.utils;

public class MessageUtils {
    public static final String NO_RECORDS_FOUND = "No records found";

    public static final String BAD_REQUEST_BACEN = "A parameter sent was invalid";
    public static final String NO_ACTIVE = "The sender or the received account is not active";
    public static final String NO_AVAILABLE_BALANCE = "The transfer amount is greater than the balance";
    public static final String DAILY_AMOUNT_ALREADY_USED = "The amount with this transfer is greater then the daily limit";
    public static final String WRONG_ISSUER_OR_NUMBER = "The transfer account has invalid parameters";
    public static final String SERVICE_UNAVAILABLE = "The API cadastro or bacen is temporally unavailable";
}
