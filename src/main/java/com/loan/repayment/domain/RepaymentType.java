package com.loan.repayment.domain;

public enum RepaymentType {
    EQUAL_INSTALLMENT("원리금균등상환"),
    BULLET("만기일시상환");

    private final String type;

    RepaymentType(String type) {
        this.type = type;
    }
}

