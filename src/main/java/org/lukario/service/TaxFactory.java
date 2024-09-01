package org.lukario.service;

import org.lukario.model.TaxBand;
import org.lukario.model.TimeWindow;

import java.util.List;

import static org.lukario.model.TimeWindow.Window.WEEKLY;
import static org.lukario.model.TimeWindow.Window.YEARLY;

public class TaxFactory {
    public static DefaultTaxCalculator createDefaultTaxCalculator() {
        List<TaxBand> incomeTaxBands = List.of(
                new TaxBand("Personal allowance", 0., 12_570., 0., TimeWindow.create(YEARLY)),
                new TaxBand("Basic rate", 12_570., 50_270., 0.2, TimeWindow.create(YEARLY)),
                new TaxBand("Higher rate", 50_270., 125_140., 0.4, TimeWindow.create(YEARLY)),
                TaxBand.upperBand("Additional rate", 125_140., 0.45, TimeWindow.create(YEARLY))
        );

        List<TaxBand> nationalInsuranceBands = List.of(
                new TaxBand("National base", 242., 967., 0.08, TimeWindow.create(WEEKLY)),
                TaxBand.upperBand("National higher", 967., 0.02, TimeWindow.create(WEEKLY))
        );

        return new DefaultTaxCalculator(incomeTaxBands, nationalInsuranceBands);
    }
}
