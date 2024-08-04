package org.lukario.service;

import org.lukario.model.Flow;
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
    public Flow getTax(Flow flow) {
        Flow nationalInsurance = getNationalInsurance(flow);
        Flow flowTax = getIncomeTax(flow);
        return Flow.sum(nationalInsurance, flowTax);
    }

    @Override
    public Flow getTax(Flow flow, List<TaxBand> taxBands) {
        return Flow.sum(taxBands.stream().map(taxBand -> taxBand.getTaxAmount(flow)));
    }

    public Flow getIncomeTax(Flow flow) {
        return getTax(flow, incomeTaxBands);
    }

    public Flow getNationalInsurance(Flow flow) {
        return getTax(flow, nationalInsuranceBands);
    }

    public List<TaxBand> getIncomeTaxBands() {
        return List.copyOf(incomeTaxBands);
    }

    public List<TaxBand> getNationalInsuranceBands() {
        return List.copyOf(nationalInsuranceBands);
    }
}
