package com.study.designpatterns.strategy;

public class NetBankingPayment implements PaymentMethod {
    private String username;
    private String password;

    NetBankingPayment(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getPaymentMethod() {
        return "Net Banking";
    }

    @Override
    public boolean pay(double amount) {
        if (password == null) {
            System.out.println("Invalid Password");
            return false;
        }
        System.out.println("Paying " + amount + " by Net Banking");
        return true;
    }

    @Override
    public String getDetails() {
        return "Username: " + username;
    }
}
