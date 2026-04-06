package com.loan.repayment.service;

import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.dto.CalculateRequestDto;
import com.loan.repayment.strategy.RepaymentStrategyRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanRepaymentService {

    private final RepaymentStrategyRegistry repaymentStrategyRegistry;
    public LoanRepaymentService(RepaymentStrategyRegistry repaymentStrategyRegistry) {
        this.repaymentStrategyRegistry = repaymentStrategyRegistry;
    }

    public List<RepaymentPlan> calculatePlan(CalculateRequestDto calculateRequestdto) {
        List<RepaymentPlan> result = repaymentStrategyRegistry.getStrategy(calculateRequestdto.type()).calculate(calculateRequestdto.loanRequest());

        result.forEach(System.out::println);
        return result;
    }
}
