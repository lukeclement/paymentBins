package org.lukario.model;

import lombok.Getter;

@Getter
public class Bucket {
    private final String name;
    private final Flow payment;
    private final TimeWindow paymentRate;
    private final Double target;
    private final TimeWindow resetRate;

    private Bucket(String name, Flow payment, TimeWindow paymentRate, Double target, TimeWindow resetRate) {
        this.name = name;
        this.payment = payment;
        this.paymentRate = paymentRate;
        this.target = target;
        this.resetRate = resetRate;
    }

    public static Bucket createBucket(String name, TimeWindow paymentRate, Double target, TimeWindow resetRate) {
        Flow goal = resetRate.flow(target);
        Double paymentAmount = goal.amount(paymentRate);
        Flow payment = paymentRate.flow(paymentAmount);
        return new Bucket(name, payment, paymentRate, target, resetRate);
    }

    public static Bucket createBucket(String name, TimeWindow paymentRate, TimeWindow resetRate, Double payments) {
        Flow payment = paymentRate.flow(payments);
        Double target = payment.amount(resetRate);
        return new Bucket(name, payment, paymentRate, target, resetRate);
    }

    public static Bucket createBucketFromPaymentWindow(String name, TimeWindow paymentRate, Double target, Double payments) throws TimeWindowException {
        Flow payment = paymentRate.flow(payments);
        TimeWindow resetRate = payment.window(target);
        return new Bucket(name, payment, paymentRate, target, resetRate);
    }

    public static Bucket createBucketFromResetWindow(String name, TimeWindow resetRate, Double target, Double payments) throws TimeWindowException {
        Flow goal = resetRate.flow(target);
        TimeWindow paymentRate = goal.window(payments);
        return new Bucket(name, paymentRate.flow(payments), paymentRate, target, resetRate);
    }

    public Flow getRemainingIncome(Flow input) {
        return Flow.sum(input, payment.negative());
    }
}
