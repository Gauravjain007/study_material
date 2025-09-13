package com.study.designpatterns.strategy;

public class StrategyImpl {
    public static void main(String[] args) {
        PaymentStrategyBuilder paymentStrategyBuilder = new PaymentStrategyBuilder(new CardPayment("123", 1234));
        paymentStrategyBuilder.confirmPayment(100.0);

        paymentStrategyBuilder.setPaymentMethod(new NetBankingPayment("user", "password"));
        paymentStrategyBuilder.confirmPayment(500.0);

        paymentStrategyBuilder.setPaymentMethod(new UPIPayment("upiId", "upiPassword", "phoneNumber"));
        paymentStrategyBuilder.confirmPayment(20.0);
    }
}
