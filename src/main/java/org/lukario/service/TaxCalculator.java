package org.lukario.service;

import org.lukario.model.Income;
import org.lukario.model.TaxBand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaxCalculator {

    Income getTax(Income income);
}
