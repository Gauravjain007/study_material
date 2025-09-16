package com.study.designpatterns.template;

// Abstract game AI template
public abstract class GameAI {

    // Template method defining AI turn sequence
    // Final method
    public final void takeTurn() {
        collectResources();
        buildStructures();
        buildUnits();
        sendScouts();
        if (shouldAttack()) {
            sendWarriors();
        } else {
            sendArchers();
        }
        afterTurn();
    }

    // Abstract methods - must be implemented
    protected abstract void collectResources();

    protected abstract void buildStructures();

    protected abstract void buildUnits();

    // Concrete methods with default behavior
    protected void sendScouts() {
        System.out.println("Sending scouts to explore...");
    }

    protected void sendWarriors() {
        System.out.println("Sending warriors to attack!");
    }

    protected void sendArchers() {
        System.out.println("Sending archers for defense...");
    }

    // Hook methods
    protected boolean shouldAttack() {
        return Math.random() > 0.5; // 50% chance to attack
    }

    protected void afterTurn() {
        System.out.println("Turn completed.\n");
    }
}
