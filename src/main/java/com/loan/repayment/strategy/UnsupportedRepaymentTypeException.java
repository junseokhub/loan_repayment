package com.loan.repayment.strategy;

import com.loan.repayment.domain.RepaymentType;

public class UnsupportedRepaymentTypeException extends RuntimeException {
    public UnsupportedRepaymentTypeException(RepaymentType type) {
        super("Unsupported repayment type: " + type);
    }
}
