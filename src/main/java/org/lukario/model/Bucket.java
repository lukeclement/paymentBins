package org.lukario.model;

import lombok.Getter;

@Getter
public class Bucket {
    private final Income payment;
    private final TimeWindow paymentRate;
    private final Double target;
    private final TimeWindow resetRate;
    private final Double amountInBucket;

    private Bucket(Income payment, TimeWindow paymentRate, Double target, TimeWindow resetRate, Double amountInBucket) {
        this.payment = payment;
        this.paymentRate = paymentRate;
        this.target = target;
        this.resetRate = resetRate;
        this.amountInBucket = amountInBucket;
    }

    public static Bucket createBucket(TimeWindow paymentRate, Double target, TimeWindow resetRate) {
        Income goal = resetRate.income(target);
        Double paymentAmount = goal.amount(paymentRate);
        Income payment = paymentRate.income(paymentAmount);
        return new Bucket(payment, paymentRate, target, resetRate, 0.);
    }

    public Bucket increase() {
        return new Bucket(payment, paymentRate, target, resetRate, amountInBucket + payment.amount(paymentRate));
    }

    public Income getRemainingIncome(Income input) {
        return Income.sum(input, payment.negative());
    }
}
