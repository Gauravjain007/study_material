package com.study.oops.interfaces;

public class Car implements Engine, Accessories {

    @Override
    public void getAllAccessories() {
        System.out.println("Has MediaPlayer");
    }

    @Override
    /**
     * Overridden Default Method
     */
    public void showEngine() {
        System.out.println("It's a Power Engine");
    }

    @Override
    public void engineType() {
        System.out.println("Has Diesel Engine");
    }

    public static void main(String[] args) {
        Car car = new Car();

        car.showEngine(); // Normal Method Call
        car.engineType(); // Normal Method Call
        Engine.enginePrice(); // Static Method Call

        car.getAllAccessories(); // Normal Method Call
        car.showMediaPlayer(); // Default Method Call
    }

}
