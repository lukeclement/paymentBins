package org.lukario.service;

import org.lukario.model.Income;
import org.lukario.model.TaxBand;

import java.util.List;

public class DefaultTaxCalculator implements TaxCalculator {
    private final List<TaxBand> incomeTaxBands;
    private final List<TaxBand> nationalInsuranceBands;

    public DefaultTaxCalculator(List<TaxBand> incomeTaxBands, List<TaxBand> nationalInsuranceBands) {
        this.incomeTaxBands = incomeTaxBands;
        this.nationalInsuranceBands = nationalInsuranceBands;
    }

    @Override
    public Income getTax(Income income) {
        Income nationalInsurance = getNationalInsurance(income);
        Income incomeTax = getIncomeTax(income);
        return Income.sum(nationalInsurance, incomeTax);
    }

    public Income getIncomeTax(Income income) {
        throw new UnsupportedOperationException();
    }

    public Income getNationalInsurance(Income income) {
        throw new UnsupportedOperationException();
    }

    public List<TaxBand> getIncomeTaxBands() {
        return List.copyOf(incomeTaxBands);
    }

    public List<TaxBand> getNationalInsuranceBands() {
        return List.copyOf(nationalInsuranceBands);
    }
}
