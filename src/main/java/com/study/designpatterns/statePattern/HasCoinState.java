package com.study.designpatterns.statePattern;

public class HasCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Coin already inserted");
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("Coin ejected successfully");
        context.setState(context.getNoCoinState());
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Product selected");
        context.setState(context.getSoldState());
    }

    @Override
    public void dispense(VendingMachine context) {
        System.out.println("Please select a product first");
    }

    @Override
    public String getDescription() {
        return "Coin inserted - Select product";
    }
}
