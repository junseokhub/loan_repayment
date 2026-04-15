package com.loan.repayment.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_REPAYMENT_TYPE("Duplicate repayment type: %s"),
    UNSUPPORTED_REPAYMENT_TYPE("Unsupported repayment type: %s"),

    INVALID_PRINCIPAL("Principal must be greater than zero. Input: %s"),
    INVALID_LOAN_TERM("Loan term must be greater than zero. Input: %d"),
    INVALID_INTEREST_RATE("Interest rate must be zero or positive. Input: %s"),

    INVALID_INSTALLMENT_NO("Installment number must be greater than zero. Input: %d"),
    INVALID_PLAN_PRINCIPAL("Principal must be zero or positive. Input: %s"),
    INVALID_PLAN_INTEREST("Interest must be zero or positive. Input: %s"),

    INTERNAL_SERVER_ERROR("Internal Server Error");

    private final String message;
}