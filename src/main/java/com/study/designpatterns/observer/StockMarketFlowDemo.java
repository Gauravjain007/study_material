package com.study.designpatterns.observer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ============================================================================
// REAL-WORLD EXAMPLE: STOCK MARKET DATA STREAMING SYSTEM
// Using Java Flow API (java.util.concurrent.Flow)
// ============================================================================

/**
 * Stock price data model
 */
class StockPrice {
    private final String symbol;
    private final double price;
    private final double change;
    private final double changePercent;
    private final long volume;
    private final LocalDateTime timestamp;

    public StockPrice(String symbol, double price, double previousPrice, long volume) {
        this.symbol = symbol;
        this.price = price;
        this.change = price - previousPrice;
        this.changePercent = previousPrice != 0 ? (change / previousPrice) * 100 : 0;
        this.volume = volume;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getChange() {
        return change;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public long getVolume() {
        return volume;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f (%.2f%%) Vol: %,d [%s]",
                symbol, price, changePercent, volume,
                timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}

/**
 * Stock Market Data Publisher
 * Implements Publisher to stream stock price updates
 */
class StockMarketPublisher implements Publisher<StockPrice> {
    private final List<Subscriber<? super StockPrice>> subscribers = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = ForkJoinPool.commonPool();
    private final Map<String, Double> lastPrices = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ScheduledExecutorService scheduler;

    // Stock symbols to simulate
    private final String[] symbols = { "AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "NVDA", "META", "NFLX" };

    public StockMarketPublisher() {
        // Initialize with random starting prices
        for (String symbol : symbols) {
            lastPrices.put(symbol, 100.0 + random.nextDouble() * 400); // $100-$500 range
        }
    }

    @Override
    public void subscribe(Subscriber<? super StockPrice> subscriber) {
        subscribers.add(subscriber);
        subscriber.onSubscribe(new StockSubscription(subscriber));
        System.out.println("New subscriber added. Total subscribers: " + subscribers.size());
    }

    /**
     * Start generating stock price updates
     */
    public void startMarket() {
        if (running.compareAndSet(false, true)) {
            scheduler = Executors.newScheduledThreadPool(2);

            // Generate price updates every 500ms to 1 second
            scheduler.scheduleWithFixedDelay(this::generatePriceUpdate,
                    0, 500 + random.nextInt(500), TimeUnit.MILLISECONDS);

            System.out.println("Stock market started!");
        }
    }

    /**
     * Stop generating stock price updates
     */
    public void stopMarket() {
        if (running.compareAndSet(true, false)) {
            if (scheduler != null) {
                scheduler.shutdown();
            }

            // Notify all subscribers that the stream is complete
            subscribers.forEach(subscriber -> {
                try {
                    subscriber.onComplete();
                } catch (Exception e) {
                    // Ignore subscriber errors during shutdown
                }
            });

            System.out.println("Stock market stopped!");
        }
    }

    private void generatePriceUpdate() {
        if (!running.get())
            return;

        // Pick a random stock
        String symbol = symbols[random.nextInt(symbols.length)];
        double lastPrice = lastPrices.get(symbol);

        // Generate new price with some volatility (-5% to +5% change)
        double changePercent = (random.nextDouble() - 0.5) * 0.1; // -5% to +5%
        double newPrice = lastPrice * (1 + changePercent);
        newPrice = Math.max(1.0, newPrice); // Ensure price doesn't go below $1

        // Update last price
        lastPrices.put(symbol, newPrice);

        // Generate random volume
        long volume = 1000 + random.nextLong(50000L);

        StockPrice stockPrice = new StockPrice(symbol, newPrice, lastPrice, volume);

        // Publish to all subscribers
        publishToSubscribers(stockPrice);
    }

    private void publishToSubscribers(StockPrice stockPrice) {
        // Create a copy to avoid ConcurrentModificationException
        List<Subscriber<? super StockPrice>> currentSubscribers = new ArrayList<>(subscribers);

        for (Subscriber<? super StockPrice> subscriber : currentSubscribers) {
            executor.submit(() -> {
                try {
                    subscriber.onNext(stockPrice);
                } catch (Exception e) {
                    // Remove problematic subscriber and notify of error
                    subscribers.remove(subscriber);
                    try {
                        subscriber.onError(e);
                    } catch (Exception ignored) {
                        // Ignore errors in error handling
                    }
                }
            });
        }
    }

    /**
     * Custom Subscription implementation
     */
    private class StockSubscription implements Subscription {
        private final Subscriber<? super StockPrice> subscriber;
        private final AtomicLong demand = new AtomicLong(0);
        private final AtomicBoolean cancelled = new AtomicBoolean(false);

        public StockSubscription(Subscriber<? super StockPrice> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            if (n <= 0) {
                subscriber.onError(new IllegalArgumentException("Request count must be positive"));
                return;
            }

            // Add to demand (backpressure handling)
            demand.addAndGet(n);
        }

        @Override
        public void cancel() {
            if (cancelled.compareAndSet(false, true)) {
                subscribers.remove(subscriber);
                System.out.println("Subscription cancelled. Remaining subscribers: " + subscribers.size());
            }
        }

        public boolean hasDemand() {
            return demand.get() > 0;
        }

        public void consumeDemand() {
            demand.decrementAndGet();
        }
    }
}

/**
 * Portfolio Tracker Subscriber
 * Tracks stock prices for a portfolio and calculates total value
 */
class PortfolioTracker implements Subscriber<StockPrice> {

    private final String portfolioName;
    private final Map<String, Integer> holdings; // symbol -> quantity
    private final Map<String, Double> currentPrices = new ConcurrentHashMap<>();
    private Subscription subscription;
    private double totalValue = 0.0;

    public PortfolioTracker(String portfolioName, Map<String, Integer> holdings) {
        this.portfolioName = portfolioName;
        this.holdings = new HashMap<>(holdings);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        System.out.println("Portfolio '" + portfolioName + "' subscribed to market data");

        // Request unlimited items (in real world, you might want to implement
        // backpressure)
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(StockPrice stockPrice) {
        String symbol = stockPrice.getSymbol();

        // Only track stocks we hold
        if (holdings.containsKey(symbol)) {
            currentPrices.put(symbol, stockPrice.getPrice());
            calculatePortfolioValue();

            System.out.printf("[%s] Updated %s: %s | Portfolio Value: $%,.2f%n",
                    portfolioName, symbol, stockPrice, totalValue);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Portfolio '" + portfolioName + "' encountered error: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Portfolio '" + portfolioName + "' - Market data stream completed");
        calculatePortfolioValue();
        System.out.printf("Final Portfolio Value for '%s': $%,.2f%n", portfolioName, totalValue);
    }

    private void calculatePortfolioValue() {
        this.totalValue = holdings.entrySet().stream()
                .mapToDouble(entry -> {
                    String symbol = entry.getKey();
                    int quantity = entry.getValue();
                    double price = currentPrices.getOrDefault(symbol, 0.0);
                    return quantity * price;
                })
                .sum();
    }

    public void unsubscribe() {
        if (subscription != null) {
            subscription.cancel();
        }
    }

    public double getTotalValue() {
        return this.totalValue;
    }

    public Map<String, Double> getCurrentPrices() {
        return new HashMap<>(currentPrices);
    }
}

/**
 * Alert System Subscriber
 * Monitors stock prices and triggers alerts based on conditions
 */
class StockAlertSystem implements Subscriber<StockPrice> {
    private final String systemName;
    private final Map<String, AlertRule> alertRules = new ConcurrentHashMap<>();
    private Subscription subscription;

    public StockAlertSystem(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        System.out.println("Alert System '" + systemName + "' subscribed to market data");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(StockPrice stockPrice) {
        String symbol = stockPrice.getSymbol();
        AlertRule rule = alertRules.get(symbol);

        if (rule != null) {
            rule.checkPrice(stockPrice);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Alert System '" + systemName + "' error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Alert System '" + systemName + "' - Market data stream completed");
    }

    /**
     * Add price alert rule
     */
    public void addPriceAlert(String symbol, double thresholdPrice, boolean isUpperThreshold) {
        alertRules.put(symbol, new PriceAlert(symbol, thresholdPrice, isUpperThreshold));
        System.out.printf("Added price alert for %s: %s $%.2f%n",
                symbol, isUpperThreshold ? "above" : "below", thresholdPrice);
    }

    /**
     * Add volume alert rule
     */
    public void addVolumeAlert(String symbol, long thresholdVolume) {
        alertRules.put(symbol + "_VOLUME", new VolumeAlert(symbol, thresholdVolume));
        System.out.printf("Added volume alert for %s: above %,d%n", symbol, thresholdVolume);
    }

    private abstract class AlertRule {
        protected final String symbol;

        public AlertRule(String symbol) {
            this.symbol = symbol;
        }

        public abstract void checkPrice(StockPrice stockPrice);
    }

    private class PriceAlert extends AlertRule {
        private final double thresholdPrice;
        private final boolean isUpperThreshold;
        private boolean alerted = false;

        public PriceAlert(String symbol, double thresholdPrice, boolean isUpperThreshold) {
            super(symbol);
            this.thresholdPrice = thresholdPrice;
            this.isUpperThreshold = isUpperThreshold;
        }

        @Override
        public void checkPrice(StockPrice stockPrice) {
            if (!symbol.equals(stockPrice.getSymbol()))
                return;

            boolean conditionMet = isUpperThreshold ? stockPrice.getPrice() >= thresholdPrice
                    : stockPrice.getPrice() <= thresholdPrice;

            if (conditionMet && !alerted) {
                triggerAlert(String.format("PRICE ALERT: %s is %s $%.2f (Current: $%.2f)",
                        symbol,
                        isUpperThreshold ? "above" : "below",
                        thresholdPrice,
                        stockPrice.getPrice()));
                alerted = true;
            } else if (!conditionMet) {
                alerted = false; // Reset alert when condition is no longer met
            }
        }
    }

    private class VolumeAlert extends AlertRule {
        private final long thresholdVolume;

        public VolumeAlert(String symbol, long thresholdVolume) {
            super(symbol);
            this.thresholdVolume = thresholdVolume;
        }

        @Override
        public void checkPrice(StockPrice stockPrice) {
            if (!symbol.equals(stockPrice.getSymbol()))
                return;

            if (stockPrice.getVolume() > thresholdVolume) {
                triggerAlert(String.format("VOLUME ALERT: %s volume spike! %,d shares (threshold: %,d)",
                        symbol, stockPrice.getVolume(), thresholdVolume));
            }
        }
    }

    private void triggerAlert(String message) {
        System.out.println("!!! [" + systemName + "] " + message + " !!!");
        // In real implementation: send email, SMS, push notification, etc.
    }
}

/**
 * Market Data Analytics Processor
 * Processes market data for statistical analysis
 */
class MarketAnalytics implements Processor<StockPrice, String> {
    private final Map<String, List<Double>> priceHistory = new ConcurrentHashMap<>();
    private final Map<String, Double> movingAverages = new ConcurrentHashMap<>();
    private static final int WINDOW_SIZE = 10; // 10-period moving average

    private Subscription upstreamSubscription;
    private final List<Subscriber<? super String>> downstreamSubscribers = new CopyOnWriteArrayList<>();

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        downstreamSubscribers.add(subscriber);
        subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                // Simple implementation - in production you'd handle backpressure properly
                if (n <= 0) {
                    subscriber.onError(new IllegalArgumentException("Request count must be positive"));
                }
            }

            @Override
            public void cancel() {
                downstreamSubscribers.remove(subscriber);
            }
        });
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.upstreamSubscription = subscription;
        subscription.request(Long.MAX_VALUE);
        System.out.println("Market Analytics processor subscribed");
    }

    @Override
    public void onNext(StockPrice stockPrice) {
        String symbol = stockPrice.getSymbol();
        double price = stockPrice.getPrice();

        // Update price history
        priceHistory.computeIfAbsent(symbol, k -> new ArrayList<>()).add(price);
        List<Double> prices = priceHistory.get(symbol);

        // Keep only last 'WINDOW_SIZE' prices
        if (prices.size() > WINDOW_SIZE) {
            prices.remove(0);
        }

        // Calculate moving average
        if (prices.size() >= WINDOW_SIZE) {
            double movingAverage = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            movingAverages.put(symbol, movingAverage);

            // Generate analysis report
            String analysis = generateAnalysis(stockPrice, movingAverage);

            // Send to downstream subscribers
            for (Subscriber<? super String> subscriber : downstreamSubscribers) {
                try {
                    subscriber.onNext(analysis);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        downstreamSubscribers.forEach(subscriber -> subscriber.onError(throwable));
    }

    @Override
    public void onComplete() {
        downstreamSubscribers.forEach(Subscriber::onComplete);
    }

    private String generateAnalysis(StockPrice stockPrice, double movingAverage) {
        String symbol = stockPrice.getSymbol();
        double currentPrice = stockPrice.getPrice();
        double deviation = ((currentPrice - movingAverage) / movingAverage) * 100;

        String trend = deviation > 2 ? "ABOVE" : deviation < -2 ? "BELOW" : "NEAR";

        return String.format("ANALYSIS: %s current: $%.2f, MA(10): $%.2f, Status: %s MA (%.1f%%)",
                symbol, currentPrice, movingAverage, trend, deviation);
    }
}

/**
 * Analysis Report Subscriber
 */
class AnalysisReportSubscriber implements Subscriber<String> {
    private final String name;

    public AnalysisReportSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("Analysis Report Subscriber '" + name + "' subscribed");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(String analysis) {
        System.out.println("~~~~~ [" + name + "] ~~~~~ " + analysis);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Analysis Report error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Analysis Report '" + name + "' completed");
    }
}

// ============================================================================
// DEMO APPLICATION
// ============================================================================

public class StockMarketFlowDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Java Flow API Stock Market Demo ===\n");

        // Create the stock market data publisher
        StockMarketPublisher marketPublisher = new StockMarketPublisher();

        // Create portfolio trackers
        Map<String, Integer> techPortfolio = Map.of(
                "AAPL", 100,
                "GOOGL", 50,
                "MSFT", 75,
                "NVDA", 25);

        Map<String, Integer> diversifiedPortfolio = Map.of(
                "AAPL", 50,
                "AMZN", 30,
                "TSLA", 40,
                "META", 60);

        PortfolioTracker techTracker = new PortfolioTracker("Tech Portfolio", techPortfolio);
        PortfolioTracker diversifiedTracker = new PortfolioTracker("Diversified Portfolio", diversifiedPortfolio);

        // Create alert system
        StockAlertSystem alertSystem = new StockAlertSystem("Main Alert System");
        alertSystem.addPriceAlert("AAPL", 180.0, true); // Alert if AAPL goes above $180
        alertSystem.addPriceAlert("TSLA", 200.0, false); // Alert if TSLA goes below $200
        alertSystem.addVolumeAlert("GOOGL", 30000); // Alert if GOOGL volume > 30K

        // Create analytics processor
        MarketAnalytics analytics = new MarketAnalytics();
        AnalysisReportSubscriber reportSubscriber = new AnalysisReportSubscriber("Technical Analysis");

        // Subscribe to market data
        marketPublisher.subscribe(techTracker);
        marketPublisher.subscribe(diversifiedTracker);
        marketPublisher.subscribe(alertSystem);
        marketPublisher.subscribe(analytics);

        // Subscribe to analytics
        analytics.subscribe(reportSubscriber);

        // Start the market
        marketPublisher.startMarket();

        // Let it run for 45 seconds
        Thread.sleep(45000);

        System.out.println("\n=== FINAL PORTFOLIO VALUES ===");
        System.out.printf("Tech Portfolio: $%,.2f%n", techTracker.getTotalValue());
        System.out.printf("Diversified Portfolio: $%,.2f%n", diversifiedTracker.getTotalValue());

        // Stop the market
        marketPublisher.stopMarket();

        System.out.println("\nDemo completed!");
    }
}