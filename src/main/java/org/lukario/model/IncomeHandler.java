package org.lukario.model;

import lombok.ToString;

@ToString
public class IncomeHandler {
    private final Income income;

    public IncomeHandler(Income baseIncome) {
        this.income = baseIncome;
    }

    public Income getTotalIncome(TimeWindow window) {
//        Double sourceIncomeRatio = Double.valueOf(income.timeWindow().getRatio());
//        Double targetIncomeRatio = Double.valueOf(window.getRatio());
//        return new Income(income.amount() * sourceIncomeRatio /  targetIncomeRatio, window);
        throw new UnsupportedOperationException();
    }

    public Income getTaxedIncome(TimeWindow timeWindow) {
        return null;
    }
}
