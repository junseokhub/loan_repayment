package com.loan.repayment.controller;

import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.dto.CalculateRequestDto;
import com.loan.repayment.service.LoanRepaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanRepaymentController {

    private final LoanRepaymentService loanRepaymentService;

    public LoanRepaymentController(LoanRepaymentService loanRepaymentService) {
        this.loanRepaymentService = loanRepaymentService;
    }

    @PostMapping()
    public List<RepaymentPlan> calculatePlan(@RequestBody CalculateRequestDto calculateRequestdto) {
        return loanRepaymentService.calculatePlan(calculateRequestdto);
    }
}

