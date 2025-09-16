package com.study.designpatterns.template;

// Aggressive AI implementation
public class AggressiveAI extends GameAI {

    @Override
    protected void collectResources() {
        System.out.println("Aggressively collecting resources for military units");
    }

    @Override
    protected void buildStructures() {
        System.out.println("Building barracks and weapon factories");
    }

    @Override
    protected void buildUnits() {
        System.out.println("Mass producing military units");
    }

    @Override
    protected boolean shouldAttack() {
        return true; // Always attack!
    }

    @Override
    protected void sendArchers() {
        // Aggressive AI prefers warriors even for defense
        sendWarriors();
    }
}
