package com.study.designpatterns.template;

// Economic AI implementation
public class EconomicAI extends GameAI {

    private int economicStrength = 0;

    @Override
    protected void collectResources() {
        System.out.println("Maximizing resource collection efficiency");
        economicStrength += 2;
    }

    @Override
    protected void buildStructures() {
        System.out.println("Building markets, farms, and economic buildings");
        economicStrength += 1;
    }

    @Override
    protected void buildUnits() {
        System.out.println("Building minimal defensive units and many workers");
    }

    @Override
    protected boolean shouldAttack() {
        return economicStrength > 10; // Attack only when economically strong
    }

    @Override
    protected void sendWarriors() {
        if (economicStrength > 15) {
            System.out.println("Sending well-funded elite warriors!");
        } else {
            super.sendWarriors();
        }
    }
}
