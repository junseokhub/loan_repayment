package com.loan.repayment.strategy;

import com.loan.repayment.domain.LoanRequest;
import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.RepaymentType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.loan.repayment.util.GlobalConstants.*;

@Component
public class EqulPrincipalRepaymentStrategy implements RepaymentStrategy {

    @Override
    public RepaymentType getType() {
        return RepaymentType.EQUAL_PRINCIPAL;
    }

    @Override
    public List<RepaymentPlan> calculate(LoanRequest request) {
        BigDecimal principal = request.principal();
        int n = request.month();
        BigDecimal r = request.rateMonthly();

        BigDecimal equalPrincipal = principal.divide(BigDecimal.valueOf(n), 0, INTEREST_ROUNDING);
        BigDecimal remainder = principal.subtract(equalPrincipal.multiply(BigDecimal.valueOf(n)));

        List<RepaymentPlan> schedules = new ArrayList<>(n);
        BigDecimal remaining = principal;
        for (int i = 1; i <= n; i++) {
            BigDecimal interest = remaining.multiply(r).setScale(0, INTEREST_ROUNDING);
            BigDecimal p = (i == n) ? equalPrincipal.add(remainder) : equalPrincipal;
            schedules.add(new RepaymentPlan(i, p, interest));
            remaining = remaining.subtract(p);
        }

        return schedules;
    }
}