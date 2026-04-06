package com.loan.repayment.strategy;

import com.loan.repayment.domain.RepaymentType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RepaymentStrategyRegistry {
    private final Map<RepaymentType, RepaymentStrategy> registry;

    public RepaymentStrategyRegistry(List<RepaymentStrategy> strategies) {
        this.registry = strategies.stream()
                .collect(Collectors.toMap(
                        RepaymentStrategy::getType,
                        Function.identity(),
                        (a, b) -> {
                            throw new IllegalStateException("상환방식 중복: " + a.getType());
                        },
                        () -> new EnumMap<>(RepaymentType.class)
                ));
    }

    public RepaymentStrategy getStrategy(RepaymentType type) {
        RepaymentStrategy strategy = registry.get(type);
        if (strategy == null) {
            throw new UnsupportedRepaymentTypeException(type);
        }
        return strategy;
    }
}
