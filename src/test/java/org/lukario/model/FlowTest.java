package org.lukario.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.lukario.service.DefaultTaxCalculator;
import org.lukario.service.TaxFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.lukario.model.TimeWindow.*;

public class FlowTest {
    @Test
    void incomeIsIndependentOfTimeWindow() {
        //Given a yearly income of 120 and a monthly income of 10
        Flow yearlyFlow = YEARLY.flow(120.0);
        Flow monthlyFlow = MONTHLY.flow(10.0);

        //Then they are equivalent
        assertEquals(yearlyFlow, monthlyFlow);
    }

    @Test
    void incomeCanBeDerivedFromTimeWindow() {
        //Given a yearly income of 6
        Flow yearlyFlow = YEARLY.flow(6.0);

        //When I extract the monthly amount
        Double monthlyIncome = yearlyFlow.amount(MONTHLY);

        //Then I expect a monthly amount of 0.5
        assertEquals(0.5, monthlyIncome);
    }

    static Stream<Arguments> incomeSumTestProvider() {
        Flow five = YEARLY.flow(5.);
        Flow ten = YEARLY.flow(10.);
        return Stream.of(
                arguments(new Flow[]{five, ten}, YEARLY.flow(15.)),
                arguments(new Flow[]{five, MONTHLY.flow(1.)}, YEARLY.flow(17.)),
                arguments(new Flow[]{five}, five),
                arguments(new Flow[]{WEEKLY.flow(1.), ten}, YEARLY.flow(62.)),
                arguments(new Flow[]{five, ten, five}, YEARLY.flow(20.)),
                arguments(new Flow[]{five, YEARLY.flow(-5.)}, YEARLY.flow(0.)),
                arguments(new Flow[]{five, YEARLY.flow(5.).negative()}, YEARLY.flow(0.))
        );
    }

    @ParameterizedTest
    @MethodSource("incomeSumTestProvider")
    void givenSomeIncomesICanAddThem(Flow[] incomesToAdd, Flow expectedTotal) {
        Flow total = Flow.sum(incomesToAdd);
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
                arguments(0., 0., 0.),
                arguments(12_570., 0., 0.),
                arguments(12_571., 0.2, 0.),
                arguments(12_670., 20., 6.88),
                arguments(80_000., 19_432., 3_610.32),
                arguments(200_000., 71_175., 6_010.32),
                arguments(50_270., 7_540., 3_014.88)
        );
    }

    @ParameterizedTest
    @MethodSource("incomeTestProvider")
    void givenIncomeICanFindIncomeTaxPaid(Double incomePerYear, Double expectedIncomeTax, Double expectedNationalInsurance) {
        //Given a tax calculator
        DefaultTaxCalculator taxCalculator = TaxFactory.createDefaultTaxCalculator();
        //Given a yearly income
        Flow flow = YEARLY.flow(incomePerYear);
        //When I get my taxes
        Flow incomeTax = taxCalculator.getIncomeTax(flow);
        Flow nationalInsurance = taxCalculator.getNationalInsurance(flow);
        Flow totalTax = taxCalculator.getTax(flow);
        //Then I expect the correct amounts
        assertFlowEquals(YEARLY.flow(expectedIncomeTax), incomeTax);
        assertFlowEquals(YEARLY.flow(expectedNationalInsurance), nationalInsurance);
        assertFlowEquals(YEARLY.flow(expectedNationalInsurance + expectedIncomeTax), totalTax);
    }

    public void assertFlowEquals(Flow a, Flow b) {
        assertEquals(a.annualAmount(), b.annualAmount(), 0.001);
    }
}
