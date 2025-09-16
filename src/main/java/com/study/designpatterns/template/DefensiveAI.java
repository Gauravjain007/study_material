package com.study.designpatterns.template;

// Defensive AI implementation
public class DefensiveAI extends GameAI {

    @Override
    protected void collectResources() {
        System.out.println("Steadily collecting resources with focus on sustainability");
    }

    @Override
    protected void buildStructures() {
        System.out.println("Building walls, towers, and defensive structures");
    }

    @Override
    protected void buildUnits() {
        System.out.println("Building balanced army with emphasis on defense");
    }

    @Override
    protected boolean shouldAttack() {
        return false; // Never initiate attacks
    }

    @Override
    protected void afterTurn() {
        super.afterTurn();
        System.out.println("Strengthening defenses...");
    }
}
