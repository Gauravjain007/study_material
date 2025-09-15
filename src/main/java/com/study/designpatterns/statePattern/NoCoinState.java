package com.study.designpatterns.statePattern;

public class NoCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Coin Inserted Successfully");
        context.setState(context.getHasCoinState());
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("No coin to eject");
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Please insert a coin first");
    }

    @Override
    public void dispense(VendingMachine context) {
        System.out.println("Please insert coin and select product");
    }

    @Override
    public String getDescription() {
        return "Waiting for coin";
    }
}
