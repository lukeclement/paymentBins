package org.lukario.model;

import java.util.Arrays;
import java.util.stream.Stream;

public record Income(Double annualAmount) {
    public static Income zero() {
        return new Income(0.);
    }

    public Double amount(TimeWindow timeWindow) {
        return annualAmount / timeWindow.getRatio();
    }

    public static Income sum(Income... incomes) {
        return sum(Arrays.stream(incomes));
    }

    public static Income sum(Stream<Income> incomeStream) {
        return incomeStream.reduce(zero(), (a, b) -> new Income(a.annualAmount + b.annualAmount));
    }

    public Income negative() {
        return new Income(-annualAmount);
    } //TODO: test
}
