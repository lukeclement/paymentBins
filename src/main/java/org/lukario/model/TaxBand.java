package org.lukario.model;

public record TaxBand(String name, Double minimum, Double maximum, Double rate, TimeWindow taxWindow) {
    public static TaxBand upperBand(String name, Double minimum, Double rate, TimeWindow taxWindow) {
        return new TaxBand(name, minimum, null, rate, taxWindow);
    }

    public Double getTaxAmount(Double income) {
        throw new UnsupportedOperationException();
    }
}