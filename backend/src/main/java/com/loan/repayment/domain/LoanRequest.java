package com.loan.repayment.domain;

import java.math.BigDecimal;

import static com.loan.repayment.util.GlobalConstants.*;

public record LoanRequest(
        BigDecimal principal,
        int month,
        BigDecimal interestRate
) {

    public LoanRequest {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    String.format("대출원금은 양수여야 함. 입력: %s", principal)
            );
        }
        if (month <= 0) {
            throw new IllegalArgumentException(
                    String.format("대출기간은 양수여야 함. 입력: %d", month)
            );
        }
        if (interestRate == null || interestRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    String.format("연이자율은 0 이상이어야 함. 입력: %s", interestRate)
            );
        }
    }

    public BigDecimal rateMonthly() {
        return interestRate.divide(
                BigDecimal.valueOf(MONTHS_IN_YEAR * 100L),
                RATE_SCALE,
                INTEREST_ROUNDING
        );
    }
}