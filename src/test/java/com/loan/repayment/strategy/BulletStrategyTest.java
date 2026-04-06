package com.loan.repayment.strategy;

import com.loan.repayment.domain.LoanRequest;
import com.loan.repayment.domain.RepaymentPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("만기일시상환")
class BulletStrategyTest {

    private BulletStrategy bulletStrategy;

    @BeforeEach
    void setUp() {
        bulletStrategy = new BulletStrategy();
    }

    @Test
    @DisplayName("마지막 회차에만 원금 상환, 나머지 0")
    void onlyLastInstallmentHasPrincipal() {
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), 12, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = bulletStrategy.calculate(request);
        int lastIndex = schedules.size() - 1;

        schedules.subList(0, lastIndex).forEach(s ->
                assertThat(s.principal()).isEqualByComparingTo(BigDecimal.ZERO));

        assertThat(schedules.get(lastIndex).principal())
                .isEqualByComparingTo(request.principal());
    }

    @Test
    @DisplayName("원금합계 == 대출원금")
    void principalSumEqualsLoanAmount() {
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), 12, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = bulletStrategy.calculate(request);

        BigDecimal totalPrincipal = schedules.stream()
                .map(RepaymentPlan::principal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(totalPrincipal).isEqualByComparingTo(request.principal());
    }

    @Test
    @DisplayName("마지막 회차 제외 모든 회차의 월 이자 똑같음")
    void allIntermediateInterestsAreEqual() {
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), 12, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = bulletStrategy.calculate(request);

        List<BigDecimal> interests = schedules.stream()
                .map(RepaymentPlan::interest)
                .toList();

        BigDecimal reference = interests.getFirst();
        assertThat(interests)
                .as("모든 회차의 이자가 동일해야 한다")
                .allSatisfy(interest -> assertThat(interest).isEqualByComparingTo(reference));
    }


    @Test
    @DisplayName("월 이자 = 대출원금 × (연이자율 / 12)")
    void monthlyInterestIsCorrectlyCalculated() {
        LoanRequest request = new LoanRequest(
                new BigDecimal("10000000"), 12, new BigDecimal("0.05"));

        List<RepaymentPlan> schedules = bulletStrategy.calculate(request);

        assertThat(schedules.getFirst().interest())
                .isEqualByComparingTo(new BigDecimal("41666"));
    }
}