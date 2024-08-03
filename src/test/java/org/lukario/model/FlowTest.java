package org.lukario.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlowTest {
    @Test
    void givenAnIncomeAndABucketIExpectFlowToBeDetermined() {
        //Given I have an income of 10 per month
        Income income = TimeWindow.MONTHLY.income(10.);
        //And I have a bucket, target 120, to be paid in monthly, with a reset rate of 1 year
        Bucket bucket = Bucket.createBucket(TimeWindow.MONTHLY, 120., TimeWindow.YEARLY);
        //When I get the income after paying into the bucket
        Income remainingIncome = bucket.getRemainingIncome(income);
        //Then I expect a remaining income of zero
        assertEquals(remainingIncome, Income.zero());
    }
}