package com.study.designpatterns.strategy;

public interface PaymentMethod {

    boolean pay(double amount);

    String getPaymentMethod();

    String getDetails();
}
