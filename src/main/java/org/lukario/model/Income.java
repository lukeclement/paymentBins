package org.lukario.model;

import java.util.Arrays;

public record Income (Double annualAmount) {
    private static Income identity() {
        return new Income(0.);
    }

    public Double amount(TimeWindow timeWindow) {
        return annualAmount / timeWindow.getRatio();
    }

    public static Income sum(Income... incomes) {
        return Arrays.stream(incomes)
                .reduce(identity(), (a, b) -> new Income(a.annualAmount + b.annualAmount));
    }
}
