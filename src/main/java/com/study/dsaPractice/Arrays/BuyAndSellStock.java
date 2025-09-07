package com.study.dsaPractice.Arrays;

/**
 * Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 */
public class BuyAndSellStock {
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int low = prices[0];
        for (int price : prices) {
            int profit = price - low;
            if (profit > maxProfit) {
                maxProfit = profit;
            }
            if (price < low) {
                low = price;
            }
        }
        return maxProfit;
    }

    public static int maxProfit2(int[] prices) {
        int maxProfit = 0;
        int low = prices[0];
        for (int price : prices) {
            maxProfit = Math.max(maxProfit, price - low);
            low = Math.min(low, price);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] prices = { 7, 1, 5, 3, 6, 4 };
        // Output: 5
        System.out.println("Max Profit: " + maxProfit(prices));
        System.out.println("Max Profit: " + maxProfit2(prices));
    }
}
