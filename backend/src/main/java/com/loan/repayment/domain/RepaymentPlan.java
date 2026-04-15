package com.loan.repayment.domain;

import com.loan.repayment.global.exception.BusinessException;
import com.loan.repayment.global.exception.ErrorCode;

import java.math.BigDecimal;

public record RepaymentPlan(
        int installmentNo,
        BigDecimal principal,
        BigDecimal interest
){
    public RepaymentPlan {
        if (installmentNo <= 0) {
            throw new BusinessException(ErrorCode.INVALID_INSTALLMENT_NO, String.valueOf(installmentNo));
        }
        if (principal == null || principal.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorCode.INVALID_PLAN_PRINCIPAL, String.valueOf(principal));
        }
        if (interest == null || interest.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorCode.INVALID_PLAN_INTEREST, String.valueOf(interest));
        }
    }
        public BigDecimal totalPay() {
            return principal.add(interest);
        }
}
