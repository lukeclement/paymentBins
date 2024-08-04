package org.lukario.service;

import org.lukario.model.Flow;
import org.lukario.model.TaxBand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaxCalculator {
    //TODO: docs
    Flow getTax(Flow flow);
    Flow getTax(Flow flow, List<TaxBand> taxBands);
}
