package com.loan.repayment.domain.dto;

import com.loan.repayment.domain.LoanRequest;
import com.loan.repayment.domain.RepaymentType;

import java.math.BigDecimal;

public record CalculateRequestDto(
        BigDecimal principal,
        int month,
        BigDecimal interestRate,
        RepaymentType type
) {

    public LoanRequest loanRequest() {
        return new LoanRequest(principal, month, interestRate);
    }
}
