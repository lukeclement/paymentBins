package org.lukario.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BucketTest {
    @Test
    void givenAnIncomeAndABucketIExpectFlowToBeDetermined() {
        //Given I have an income of 10 per month
        Flow flow = TimeWindow.MONTHLY.flow(10.);
        //And I have a bucket, target 120, to be paid in monthly, with a reset rate of 1 year
        Bucket bucket = Bucket.createBucket("test", TimeWindow.MONTHLY, 120., TimeWindow.YEARLY);
        //When I get the income after paying into the bucket
        Flow remainingFlow = bucket.getRemainingIncome(flow);
        //Then I expect a remaining income of zero
        assertEquals(remainingFlow, Flow.zero());
    }

    @Test
    void givenASetOfParametersIGetValidBuckets() {
        //Given a set of 4 variables
        TimeWindow paymentRate = TimeWindow.WEEKLY;
        TimeWindow resetRate = TimeWindow.YEARLY;
        Double target = 520.;
        Double payments = 10.;
        String name = "test";
        //When I create a bucket with any 3 of those variables
        Bucket bucketA = Bucket.createBucket(name, paymentRate, target, resetRate);
        Bucket bucketB = Bucket.createBucket(name, paymentRate, resetRate, payments);
        Bucket bucketC = Bucket.createBucketFromPaymentWindow(name, paymentRate, target, payments);
        Bucket bucketD = Bucket.createBucketFromResetWindow(name, resetRate, target, payments);
        //Then I get a valid bucket created
        assertValidBucket(bucketA, paymentRate, resetRate, target, payments, name);
        assertValidBucket(bucketB, paymentRate, resetRate, target, payments, name);
        assertValidBucket(bucketC, paymentRate, resetRate, target, payments, name);
        assertValidBucket(bucketD, paymentRate, resetRate, target, payments, name);
    }

    void assertValidBucket(
            Bucket actual,
            TimeWindow expectedPaymentRate,
            TimeWindow expectedResetRate,
            Double expectedTarget,
            Double expectedPayments,
            String expectedName) {
        assertEquals(actual.getPaymentRate(), expectedPaymentRate);
        assertEquals(actual.getTarget(), expectedTarget);
        assertEquals(actual.getResetRate(), expectedResetRate);
        assertEquals(actual.getPayment().amount(expectedPaymentRate), expectedPayments);
        assertEquals(actual.getName(), expectedName);
    }
}
