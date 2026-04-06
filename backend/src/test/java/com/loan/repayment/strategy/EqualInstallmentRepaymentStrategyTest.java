package com.loan.repayment.strategy;

import com.loan.repayment.domain.LoanRequest;
import com.loan.repayment.domain.RepaymentPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("원리금균등상환")
class EqualInstallmentRepaymentStrategyTest {

    private EqualInstallmentRepaymentStrategy equalInstallmentRepaymentStrategy;

    @BeforeEach
    void setUp() {
        equalInstallmentRepaymentStrategy = new EqualInstallmentRepaymentStrategy();
    }

    @Test
    @DisplayName("원금 합계 == 대출원금")
    void principalSumEqualsLoanAmount() {
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), 12, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = equalInstallmentRepaymentStrategy.calculate(request);

        BigDecimal totalPrincipal = schedules.stream()
                .map(RepaymentPlan::principal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(totalPrincipal).isEqualByComparingTo(request.principal());
    }


    @Test
    @DisplayName("첫 회차, 마지막 회차 제외 나머지 합계 일치")
    void middleInstallmentsHaveSameTotalPayment() {
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), 12, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = equalInstallmentRepaymentStrategy.calculate(request);

        List<BigDecimal> middlePayments = schedules.subList(1, schedules.size() - 1)
                .stream()
                .map(RepaymentPlan::totalPay)
                .toList();

        BigDecimal reference = middlePayments.getFirst();
        assertThat(middlePayments)
                .as("중간 회차의 원리금 합계 일치")
                .allSatisfy(payment ->
                        assertThat(payment).isEqualByComparingTo(reference));
    }

    @Test
    @DisplayName("return 리스트 크기 == 개월수")
    void scheduleCountEqualsTermInMonths() {
        int termInMonths = 12;
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), termInMonths, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = equalInstallmentRepaymentStrategy.calculate(request);

        assertThat(schedules).hasSize(termInMonths);
    }

    @Test
    @DisplayName("회차 번호 1번부터 순서대로")
    void installmentNumbersAreSequential() {
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), 6, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = equalInstallmentRepaymentStrategy.calculate(request);

        for (int i = 0; i < schedules.size(); i++) {
            assertThat(schedules.get(i).installmentNo()).isEqualTo(i + 1);
        }
    }
}