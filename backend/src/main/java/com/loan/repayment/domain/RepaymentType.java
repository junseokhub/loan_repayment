package com.loan.repayment.domain;

public enum RepaymentType {
    EQUAL_INSTALLMENT("Equal Installment Repayment"),
    BULLET("Bullet Repayment"),
    EQUAL_PRINCIPAL("Equal Principal Repayment");

    private final String type;

    RepaymentType(String type) {
        this.type = type;
    }
}

