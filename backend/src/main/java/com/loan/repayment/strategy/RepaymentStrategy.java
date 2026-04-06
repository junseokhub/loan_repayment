package com.loan.repayment.strategy;

import com.loan.repayment.domain.LoanRequest;
import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.RepaymentType;

import java.util.List;

public interface RepaymentStrategy {
    List<RepaymentPlan> calculate(LoanRequest loanRequest);
    RepaymentType getType();
}
