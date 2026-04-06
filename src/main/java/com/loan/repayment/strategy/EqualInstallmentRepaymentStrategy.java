package com.loan.repayment.strategy;

import com.loan.repayment.domain.LoanRequest;
import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.RepaymentType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.loan.repayment.util.GlobalConstants.*;

// 원리금균등상환
// emi P x r x (1 + r)^n / ((1 + r)^n -1)
@Component
public class EqualInstallmentRepaymentStrategy implements RepaymentStrategy {
    @Override
    public RepaymentType getType() {
        return RepaymentType.EQUAL_INSTALLMENT;
    }

    @Override
    public List<RepaymentPlan> calculate(LoanRequest loanRequest) {
        BigDecimal principal = loanRequest.principal();
        int n = loanRequest.month();
        BigDecimal r = loanRequest.rateMonthly();

        if (r.compareTo(BigDecimal.ZERO) == 0) {
            return calculateZeroInterest(principal, n);
        }

        BigDecimal emi = computeEmi(principal, r, n);
        return buildSchedule(principal, r, n, emi);
    }

    // emi 계산
    private BigDecimal computeEmi(BigDecimal principal, BigDecimal r, int n) {
        BigDecimal onePlusR = BigDecimal.ONE.add(r, PRECISION).pow(n, PRECISION);
        BigDecimal numerator = principal.multiply(r, PRECISION).multiply(onePlusR, PRECISION);
        BigDecimal denominator = onePlusR.subtract(BigDecimal.ONE, PRECISION);
        return numerator.divide(denominator, PRECISION);
    }

    // 회차별 스케줄
    private List<RepaymentPlan> buildSchedule(BigDecimal principal, BigDecimal r, int n, BigDecimal emi) {
        List<RepaymentPlan> schedules = new ArrayList<>(n);
        BigDecimal remaining = principal;

        for (int i = 1; i <= n; i++) {
            BigDecimal interest = remaining.multiply(r).setScale(0, INTEREST_ROUNDING);
            BigDecimal principalPayment;

            if (i == n) {
                principalPayment = remaining;
            } else {
                principalPayment = emi.subtract(interest).setScale(0, PRINCIPAL_ROUNDING);
            }

            schedules.add(new RepaymentPlan(i, principalPayment, interest));
            remaining = remaining.subtract(principalPayment);
        }

        return schedules;
    }

    // 금리가 0% 일 경우
    private List<RepaymentPlan> calculateZeroInterest(BigDecimal principal, int n) {
        List<RepaymentPlan> schedules = new ArrayList<>(n);
        BigDecimal equalPrincipal = principal.divide(BigDecimal.valueOf(n), 0, INTEREST_ROUNDING);
        BigDecimal remainder = principal.subtract(equalPrincipal.multiply(BigDecimal.valueOf(n)));

        for (int i = 1; i <= n; i++) {
            BigDecimal payment = (i == n) ? equalPrincipal.add(remainder) : equalPrincipal;
            schedules.add(new RepaymentPlan(i, payment, BigDecimal.ZERO));
        }

        return schedules;
    }
}
