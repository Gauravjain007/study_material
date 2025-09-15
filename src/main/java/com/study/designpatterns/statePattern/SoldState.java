package com.study.designpatterns.statePattern;

public class SoldState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Please wait, dispensing product");
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("Cannot eject coin, product being dispensed");
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Product already selected, dispensing...");
    }

    @Override
    public void dispense(VendingMachine context) {
        context.releaseProduct();
        if (context.getProductCount() > 0) {
            context.setState(context.getNoCoinState());
        } else {
            context.setState(context.getSoldOutState());
        }
    }

    @Override
    public String getDescription() {
        return "Dispensing product";
    }
}
