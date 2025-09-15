package com.study.designpatterns.statePattern;

public class SoldOutState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Machine sold out - coin ejected");
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("No coin to eject");
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Machine sold out");
    }

    @Override
    public void dispense(VendingMachine context) {
        System.out.println("Machine sold out");
    }

    @Override
    public String getDescription() {
        return "Sold out";
    }
}
