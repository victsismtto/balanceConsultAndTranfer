package com.challange.api.TranferAndBalanceConsult.enuns;

import java.util.Properties;

public enum DailyLimit {

    MAX_VALUE(1000.00);

    private Double value;

    DailyLimit(double value) {
        this.value = value;
    }

    public Double toDouble() {
        return Double.valueOf(value);
    }
}
