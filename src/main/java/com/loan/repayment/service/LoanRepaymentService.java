package com.loan.repayment.service;

import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.dto.CalculateRequestDto;
import com.loan.repayment.strategy.RepaymentStrategyRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanRepaymentService {

    private final RepaymentStrategyRegistry repaymentStrategyRegistry;
    private static final Logger logger = LoggerFactory.getLogger(LoanRepaymentService.class);
    public LoanRepaymentService(RepaymentStrategyRegistry repaymentStrategyRegistry) {
        this.repaymentStrategyRegistry = repaymentStrategyRegistry;
    }

    public List<RepaymentPlan> calculatePlan(CalculateRequestDto calculateRequestdto) {
        return repaymentStrategyRegistry.getStrategy(calculateRequestdto.type()).calculate(calculateRequestdto.loanRequest());
    }
}
