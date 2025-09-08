package com.study.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

public class Stock implements Subject<Stock> {

    private final List<Observer<Stock>> observers = new ArrayList<>();

    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer<Stock> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Stock> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<Stock> observer : observers) {
            observer.update(this);
        }
    }

    public static void main(String[] args) {
        Stock hpcl = new Stock("HPCL", 275.5);
        StockScreener screenerA = new StockScreener(hpcl, "Screen A");
        StockScreener screenerB = new StockScreener(hpcl, "Screen B");
        hpcl.registerObserver(screenerA);
        hpcl.registerObserver(screenerB);

        Stock aapl = new Stock("AAPL", 100.0);
        StockScreener screenerC = new StockScreener(aapl, "Screen C");
        aapl.registerObserver(screenerC);

        aapl.setPrice(105.0);
        hpcl.setPrice(260.0); // Both screenerA and screenerB get notified
        aapl.setPrice(98.0);
        hpcl.removeObserver(screenerA);
        hpcl.setPrice(275.5); // No notification sent to screenerA
        hpcl.removeObserver(screenerB);
    }

}
