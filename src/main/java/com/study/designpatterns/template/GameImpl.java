package com.study.designpatterns.template;

public class GameImpl {

    public static void main(String[] args) {
        GameAI ai = new EconomicAI();
        ai.takeTurn();

        ai = new DefensiveAI();
        ai.takeTurn();

        ai = new AggressiveAI();
        ai.takeTurn();
    }
}
