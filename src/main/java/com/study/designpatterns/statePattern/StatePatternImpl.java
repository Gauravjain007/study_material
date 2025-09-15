package com.study.designpatterns.statePattern;

public class StatePatternImpl {

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(3);
        machine.insertCoin();
        machine.selectProduct();
        machine.dispense();
        machine.ejectCoin();
        machine.dispense();
        machine.insertCoin();
        machine.dispense();
        machine.selectProduct();
        machine.dispense();
        machine.insertCoin();
        machine.selectProduct();
        machine.dispense();
        machine.insertCoin();
        machine.ejectCoin();
    }
}
