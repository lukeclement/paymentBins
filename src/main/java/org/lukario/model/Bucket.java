package org.lukario.model;

import lombok.Getter;

@Getter
public class Bucket {
    private final Flow payment;
    private final TimeWindow paymentRate;
    private final Double target;
    private final TimeWindow resetRate;
    private final Double amountInBucket;

    private Bucket(Flow payment, TimeWindow paymentRate, Double target, TimeWindow resetRate, Double amountInBucket) {
        this.payment = payment;
        this.paymentRate = paymentRate;
        this.target = target;
        this.resetRate = resetRate;
        this.amountInBucket = amountInBucket;
    }

    public static Bucket createBucket(TimeWindow paymentRate, Double target, TimeWindow resetRate) {
        Flow goal = resetRate.flow(target);
        Double paymentAmount = goal.amount(paymentRate);
        Flow payment = paymentRate.flow(paymentAmount);
        return new Bucket(payment, paymentRate, target, resetRate, 0.);
    }

    public Bucket increase() {
        return new Bucket(payment, paymentRate, target, resetRate, amountInBucket + payment.amount(paymentRate));
    }

    public Flow getRemainingIncome(Flow input) {
        return Flow.sum(input, payment.negative());
    }
}
