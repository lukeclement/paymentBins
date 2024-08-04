package org.lukario.model;

import lombok.ToString;

@ToString
public class IncomeHandler {
    private final Flow flow;

    public IncomeHandler(Flow baseFlow) {
        this.flow = baseFlow;
    }

    public Flow getTotalIncome(TimeWindow window) {
//        Double sourceIncomeRatio = Double.valueOf(income.timeWindow().getRatio());
//        Double targetIncomeRatio = Double.valueOf(window.getRatio());
//        return new Income(income.amount() * sourceIncomeRatio /  targetIncomeRatio, window);
        throw new UnsupportedOperationException();
    }

    public Flow getTaxedIncome(TimeWindow timeWindow) {
        return null;
    }
}
