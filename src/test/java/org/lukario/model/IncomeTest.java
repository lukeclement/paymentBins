package org.lukario.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.lukario.service.DefaultTaxCalculator;
import org.lukario.service.TaxCalculator;
import org.lukario.service.TaxFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.lukario.model.TimeWindow.*;

public class IncomeTest {
    @Test
    void incomeIsIndependentOfTimeWindow() {
        //Given a yearly income of 120 and a monthly income of 10
        Income yearlyIncome = YEARLY.income(120.0);
        Income monthlyIncome = MONTHLY.income(10.0);

        //Then they are equivalent
        assertEquals(yearlyIncome, monthlyIncome);
    }

    @Test
    void incomeCanBeDerivedFromTimeWindow() {
        //Given a yearly income of 6
        Income yearlyIncome = YEARLY.income(6.0);

        //When I extract the monthly amount
        Double monthlyIncome = yearlyIncome.amount(MONTHLY);

        //Then I expect a monthly amount of 0.5
        assertEquals(0.5, monthlyIncome);
    }

    static Stream<Arguments> incomeSumTestProvider() {
        Income five = YEARLY.income(5.);
        Income ten = YEARLY.income(10.);
        return Stream.of(
                arguments(new Income[]{five, ten}, YEARLY.income(15.)),
                arguments(new Income[]{five, MONTHLY.income(1.)}, YEARLY.income(17.)),
                arguments(new Income[]{five}, five),
                arguments(new Income[]{WEEKLY.income(1.), ten}, YEARLY.income(62.)),
                arguments(new Income[]{five, ten, five}, YEARLY.income(20.)),
                arguments(new Income[]{five, YEARLY.income(-5.)}, YEARLY.income(0.))
        );
    }

    @ParameterizedTest
    @MethodSource("incomeSumTestProvider")
    void givenSomeIncomesICanAddThem(Income[] incomesToAdd, Income expectedTotal) {
        Income total = Income.sum(incomesToAdd);
        assertEquals(expectedTotal, total);
    }

    @Test
    void givenADefaultTaxCalculatorIExpectCertainTaxBands() {
        DefaultTaxCalculator defaultTaxCalculator = TaxFactory.createDefaultTaxCalculator();
        List<TaxBand> defaultIncomeTaxBands = defaultTaxCalculator.getIncomeTaxBands();
        List<TaxBand> defaultNationalInsuranceBands = defaultTaxCalculator.getNationalInsuranceBands();

        assertThat(defaultIncomeTaxBands.size()).isEqualTo(4);
        assertThat(defaultNationalInsuranceBands.size()).isEqualTo(2);
    }

    static Stream<Arguments> incomeTestProvider() {
        return Stream.of(
                arguments(0.0, 0.0),
                arguments(12570, 0.0),
                arguments(12571.0, 0.0),
                arguments(12670.0, 18.20),
                arguments(80000, 19428.4),
                arguments(50270.0, 7538.2)
        );
    }

    @ParameterizedTest
    @MethodSource("incomeTestProvider")
    void givenIncomeICanFindIncomeTaxPaid(Double incomePerAnnum, Double expectedIncomeTax) {
        //Given a tax calculator
        TaxCalculator taxCalculator = TaxFactory.createDefaultTaxCalculator();
        //Given a yearly income
        Income income = YEARLY.income(incomePerAnnum);

//        Income incomeTax = taxCalculator.getIncomeTax(income);
//        assertEquals(YEARLY.income(expectedIncomeTax), incomeTax);
    }
}
