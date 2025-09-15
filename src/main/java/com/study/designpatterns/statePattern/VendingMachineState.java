package com.study.designpatterns.statePattern;

public interface VendingMachineState {
    void insertCoin(VendingMachine context);

    void ejectCoin(VendingMachine context);

    void selectProduct(VendingMachine context);

    void dispense(VendingMachine context);

    String getDescription();
}
