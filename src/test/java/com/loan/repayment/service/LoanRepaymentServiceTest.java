package com.loan.repayment.service;

import com.loan.repayment.domain.RepaymentPlan;
import com.loan.repayment.domain.RepaymentType;
import com.loan.repayment.domain.dto.CalculateRequestDto;
import com.loan.repayment.strategy.BulletStrategy;
import com.loan.repayment.strategy.EqualInstallmentStrategy;
import com.loan.repayment.strategy.RepaymentStrategyRegistry;
import com.loan.repayment.strategy.UnsupportedRepaymentTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoanRepaymentServiceTest {

    private LoanRepaymentService loanRepaymentService;


    @BeforeEach
    void setUp() {
        RepaymentStrategyRegistry registry = new RepaymentStrategyRegistry(
                List.of(
                        new EqualInstallmentStrategy(),
                        new BulletStrategy()
                )
        );
        loanRepaymentService = new LoanRepaymentService(registry);
    }

    @Test
    @DisplayName("원리금균등상환")
    void calculateEqualInstallmentSchedule() throws UnsupportedRepaymentTypeException {
        CalculateRequestDto request = new CalculateRequestDto(
                new BigDecimal("10000000"),
                12,
                new BigDecimal("0.05"),
                RepaymentType.EQUAL_INSTALLMENT
                );

        List<RepaymentPlan> schedules =
                loanRepaymentService.calculatePlan(request);

        assertThat(schedules).hasSize(12);
    }

    @Test
    @DisplayName("만기일시상환")
    void calculateBulletSchedule() throws UnsupportedRepaymentTypeException {
        CalculateRequestDto request = new CalculateRequestDto(
                new BigDecimal("10000000"),
                12,
                new BigDecimal("0.05"),
                RepaymentType.BULLET
        );

        List<RepaymentPlan> schedules =
                loanRepaymentService.calculatePlan(request);

        assertThat(schedules).hasSize(12);

        assertThat(schedules.get(11).principal())
                .isEqualByComparingTo(request.principal());
    }


    @Test
    @DisplayName("UnsupportedRepaymentTypeException 발생")
    void unregisteredRepaymentTypeThrowsException() {
        RepaymentStrategyRegistry emptyRegistry = new RepaymentStrategyRegistry(List.of());
        LoanRepaymentService emptyService = new LoanRepaymentService(emptyRegistry);

        CalculateRequestDto request = new CalculateRequestDto(
                new BigDecimal("10000000"),
                12,
                new BigDecimal("0.05"),
                RepaymentType.BULLET
        );

        assertThatThrownBy(() ->
                emptyService.calculatePlan(request)
        ).isInstanceOf(UnsupportedRepaymentTypeException.class);
    }
}