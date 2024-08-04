package org.lukario.model;

import java.util.Arrays;
import java.util.stream.Stream;

public record Flow(Double annualAmount) {
    public static Flow zero() {
        return new Flow(0.);
    }

    public Double amount(TimeWindow timeWindow) {
        return annualAmount / timeWindow.getRatio();
    }

    public TimeWindow window(Double amount) throws TimeWindowException {
        return TimeWindow.valueOf(annualAmount / amount);
    }

    public static Flow sum(Flow... flows) {
        return sum(Arrays.stream(flows));
    }

    public static Flow sum(Stream<Flow> incomeStream) {
        return incomeStream.reduce(zero(), (a, b) -> new Flow(a.annualAmount + b.annualAmount));
    }

    public Flow negative() {
        return new Flow(-annualAmount);
    }
}
