package com.loan.repayment.domain;

import java.math.BigDecimal;

public record RepaymentPlan(
        int installmentNo,
        BigDecimal principal,
        BigDecimal interest
){
    public RepaymentPlan {
        if (installmentNo <= 0) {
            throw new IllegalArgumentException("회차 1이상이어야 함. 입력: %s " + installmentNo);
        }
        if (principal == null || principal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("원금 양수여야 함. 입력: %s " + principal);
        }
        if (interest == null || interest.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("이자 양수여야 함. 입력: %s " + interest);
        }
    }
        public BigDecimal totalPay() {
            return principal.add(interest);
        }
}
