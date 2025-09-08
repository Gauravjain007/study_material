package com.study.designpatterns.observer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class SafeStockScreener implements Observer<SafeStock> {

    private static final String ALERT = "Stock Alert: ";

    private String screen;
    private String stockSymbol;
    private double stockPrice;

    public SafeStockScreener(SafeStock stock, String screen) {
        this.stockSymbol = stock.getSymbol();
        this.stockPrice = stock.getPrice();
        this.screen = screen;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    @Override
    public void update(SafeStock stock) {
        String screenAlert = "[" + screen + "] " + ALERT + stockSymbol;
        if (stock.getPrice() > stockPrice) {
            System.out.println(screenAlert + " has increased from "
                    + stockPrice + " to " + stock.getPrice());
        } else if (stock.getPrice() < stockPrice) {
            System.out.println(screenAlert + " has decreased from "
                    + stockPrice + " to " + stock.getPrice());
        } else {
            System.out.println(screenAlert + " remains unchanged at " + stockPrice);
        }
        this.stockPrice = stock.getPrice();
    }

}

public class SafeStock implements Subject<SafeStock> {
    private final List<WeakReference<Observer<SafeStock>>> observers = Collections.synchronizedList(new ArrayList<>());

    private String symbol;
    private double price;

    public SafeStock(String symbol, double price) {
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
    public void registerObserver(Observer<SafeStock> observer) {
        observers.add(new WeakReference<>(observer));
    }

    @Override
    public void removeObserver(Observer<SafeStock> observer) {
        synchronized (observers) {
            Iterator<WeakReference<Observer<SafeStock>>> iterator = observers.iterator();
            while (iterator.hasNext()) {
                WeakReference<Observer<SafeStock>> ref = iterator.next();
                Observer<SafeStock> obs = ref.get();
                if (obs == null || obs.equals(observer)) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public void notifyObservers() {
        List<Observer<SafeStock>> observersCopy;
        synchronized (observers) {
            observersCopy = new ArrayList<>();
            Iterator<WeakReference<Observer<SafeStock>>> iterator = observers.iterator();
            while (iterator.hasNext()) {
                WeakReference<Observer<SafeStock>> ref = iterator.next();
                Observer<SafeStock> obs = ref.get();
                if (obs != null) {
                    observersCopy.add(obs);
                } else {
                    iterator.remove(); // Clean up cleared references
                }
            }
        }

        for (Observer<SafeStock> observer : observersCopy) {
            try {
                observer.update(this);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + observer + ". Exception: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SafeStock hpcl = new SafeStock("HPCL", 275.5);
        SafeStockScreener screenerA = new SafeStockScreener(hpcl, "Screen A");
        SafeStockScreener screenerB = new SafeStockScreener(hpcl, "Screen B");
        hpcl.registerObserver(screenerA);
        hpcl.registerObserver(screenerB);

        SafeStock aapl = new SafeStock("AAPL", 100.0);
        SafeStockScreener screenerC = new SafeStockScreener(aapl, "Screen C");
        aapl.registerObserver(screenerC);

        aapl.setPrice(105.0);
        hpcl.setPrice(260.0); // Both screenerA and screenerB get notified
        aapl.setPrice(98.0);
        hpcl.removeObserver(screenerA);
        hpcl.setPrice(275.5); // No notification sent to screenerA
        hpcl.removeObserver(screenerB);
    }
}
