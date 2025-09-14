package com.study.designpatterns.strategy;

public class StrategyImpl {
    public static void main(String[] args) {
        PaymentStrategyBuilder paymentStrategyBuilder = new PaymentStrategyBuilder(
                new CardPayment("12326262517818", 1234));
        paymentStrategyBuilder.confirmPayment(100.0);

        paymentStrategyBuilder.setPaymentMethod(new NetBankingPayment("user1", "[pwd1%"));
        paymentStrategyBuilder.confirmPayment(500.0);

        paymentStrategyBuilder.setPaymentMethod(new UPIPayment("1213415@bank", "Pass131451", "7516752211"));
        paymentStrategyBuilder.confirmPayment(20.0);
    }
}
