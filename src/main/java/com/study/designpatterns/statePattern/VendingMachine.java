package com.study.designpatterns.statePattern;

public class VendingMachine {

    private VendingMachineState noCoinState;
    private VendingMachineState hasCoinState;
    private VendingMachineState soldState;
    private VendingMachineState soldOutState;

    private VendingMachineState currentState;
    private int productCount;

    public VendingMachine(int productCount) {
        // Initialize all states
        noCoinState = new NoCoinState();
        hasCoinState = new HasCoinState();
        soldState = new SoldState();
        soldOutState = new SoldOutState();

        this.productCount = productCount;
        this.currentState = productCount > 0 ? noCoinState : soldOutState;
    }

    public void insertCoin() {
        currentState.insertCoin(this);
    }

    public void ejectCoin() {
        currentState.ejectCoin(this);
    }

    public void selectProduct() {
        currentState.selectProduct(this);
    }

    public void dispense() {
        currentState.dispense(this);
    }

    // State transition methods
    public void setState(VendingMachineState state) {
        this.currentState = state;
        System.out.println("State changed to: " + state.getDescription());
    }

    // Getters for states
    public VendingMachineState getNoCoinState() {
        return noCoinState;
    }

    public VendingMachineState getHasCoinState() {
        return hasCoinState;
    }

    public VendingMachineState getSoldState() {
        return soldState;
    }

    public VendingMachineState getSoldOutState() {
        return soldOutState;
    }

    public int getProductCount() {
        return productCount;
    }

    public String getCurrentState() {
        return currentState.getDescription();
    }

    // Product management
    public void releaseProduct() {
        if (productCount > 0) {
            productCount--;
            System.out.println("Product dispensed. Remaining: " + productCount);
        }
    }
}
