package com.study.designpatterns.strategy;

public class CardPayment implements PaymentMethod {

    private String cardNumber;
    private Integer pin;

    public CardPayment(String cardNumber, Integer pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    @Override
    public String getPaymentMethod() {
        return "Card";
    }

    @Override
    public boolean pay(double amount) {
        if (pin == null) {
            System.out.println("Invalid PIN");
            return false;
        }
        System.out.println("Paying " + amount + " by Credit Card");
        return true;
    }

    @Override
    public String getDetails() {
        return "Card Number: " + cardNumber;
    }
}
