package com.study.designpatterns.observer;

public class StockScreener implements Observer<Stock> {

    private static final String ALERT = "Stock Alert: ";

    private String screen;
    private String stockSymbol;
    private double stockPrice;

    public StockScreener(Stock stock, String screen) {
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
    public void update(Stock stock) {
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
