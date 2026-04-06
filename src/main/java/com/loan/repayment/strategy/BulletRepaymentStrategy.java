package com.loan.repayment.strategy;

import com.loan.repayment.domain.LoanRequest;
import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.RepaymentType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// 만기 일시 상환
// 만기까지 매월 이자만 납부하고, 마지막 회차에 원금 전액을 일시 상환
@Component
public class BulletRepaymentStrategy implements RepaymentStrategy{

    @Override
    public RepaymentType getType() {
        return RepaymentType.BULLET;
    }

    @Override
    public List<RepaymentPlan> calculate(LoanRequest loanRequest) {
        BigDecimal principal = loanRequest.principal();
        int n = loanRequest.month();
        BigDecimal monthlyInterest = principal.multiply(loanRequest.rateMonthly())
                .setScale(0, RoundingMode.FLOOR);

        List<RepaymentPlan> schedules = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            BigDecimal principalPayment = (i == n) ? principal : BigDecimal.ZERO;
            schedules.add(new RepaymentPlan(i, principalPayment, monthlyInterest));
        }

        return schedules;
    }
}
