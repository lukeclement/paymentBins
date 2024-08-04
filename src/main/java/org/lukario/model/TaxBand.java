package org.lukario.model;

public record TaxBand(String name, Double minimum, Double maximum, Double rate, TimeWindow taxWindow) {
    public static TaxBand upperBand(String name, Double minimum, Double rate, TimeWindow taxWindow) {
        return new TaxBand(name, minimum, Double.POSITIVE_INFINITY, rate, taxWindow);
    }

    public Flow getTaxAmount(Flow flow) {
        Double amount = flow.amount(taxWindow);
        return taxWindow.flow(getIncomeWithinBand(amount) * rate);
    }

    private double getIncomeWithinBand(Double amount) {
        double incomeWithinBand;
        if (amount <= minimum) {
            incomeWithinBand = 0;
        } else if (amount >= maximum) {
            incomeWithinBand = maximum - minimum;
        } else {
            incomeWithinBand = amount - minimum;
        }
        return incomeWithinBand;
    }
}