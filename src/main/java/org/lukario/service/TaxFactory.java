package org.lukario.service;

import org.lukario.model.TaxBand;

import java.util.List;

import static org.lukario.model.TimeWindow.WEEKLY;
import static org.lukario.model.TimeWindow.YEARLY;

public class TaxFactory {
    public static DefaultTaxCalculator createDefaultTaxCalculator() {
        List<TaxBand> incomeTaxBands = List.of(
                new TaxBand("Personal allowance", 0., 12_570., 0., YEARLY),
                new TaxBand("Basic rate", 12_570., 50_270., 0.2, YEARLY),
                new TaxBand("Higher rate", 50_270., 125_140., 0.4, YEARLY),
                TaxBand.upperBand("Additional rate", 125_140., 0.45, YEARLY)
        );

        List<TaxBand> nationalInsuranceBands = List.of(
                new TaxBand("National base", 242., 967., 0.08, WEEKLY),
                TaxBand.upperBand("National higher", 967., 0.02, WEEKLY)
        );

        return new DefaultTaxCalculator(incomeTaxBands, nationalInsuranceBands);
    }
}
