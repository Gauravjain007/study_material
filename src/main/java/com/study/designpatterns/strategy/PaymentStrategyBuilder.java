package com.study.designpatterns.strategy;

public class PaymentStrategyBuilder {
    private PaymentMethod paymentMethod;

    public PaymentStrategyBuilder(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean confirmPayment(Double amount) {
        if (amount == null || amount <= 0) {
            System.out.println("Invalid amount");
            return false;
        }
        System.out.println("Details: " + paymentMethod.getDetails());
        return paymentMethod.pay(amount);
    }
}
