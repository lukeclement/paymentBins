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

    @Override
    public Income getTax(Income income, List<TaxBand> taxBands) {
        return Income.sum(taxBands.stream().map(taxBand -> taxBand.getTaxAmount(income)));
    }

    public Income getIncomeTax(Income income) {
        return getTax(income, incomeTaxBands);
    }

    public Income getNationalInsurance(Income income) {
        return getTax(income, nationalInsuranceBands);
    }

    public List<TaxBand> getIncomeTaxBands() {
        return List.copyOf(incomeTaxBands);
    }

    public List<TaxBand> getNationalInsuranceBands() {
        return List.copyOf(nationalInsuranceBands);
    }
}
