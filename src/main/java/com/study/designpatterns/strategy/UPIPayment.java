package com.study.designpatterns.strategy;

public class UPIPayment implements PaymentMethod {

    String upiId;
    String upiPassword;
    String phoneNumber;

    public UPIPayment(String upiId, String upiPassword, String phoneNumber) {
        this.upiId = upiId;
        this.upiPassword = upiPassword;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPaymentMethod() {
        return "UPI";
    }

    @Override
    public boolean pay(double amount) {
        if (phoneNumber == null || upiId == null || upiPassword == null) {
            System.out.println("Invalid UPI details");
            return false;
        }
        System.out.println("Paying " + amount + " by UPI");
        return true;
    }

    @Override
    public String getDetails() {
        return "Transaction details for UPI: " + upiId + " Phone Number: " + phoneNumber;
    }
}
