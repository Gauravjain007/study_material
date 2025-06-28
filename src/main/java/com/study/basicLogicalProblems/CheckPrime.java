package com.study.basicLogicalProblems;

public class CheckPrime {
    public static Boolean isPrime(int num) {
        if (num <= 1)
            return false;
        int sqrt = (int) Math.sqrt(num);
        for (int i = 2; i <= sqrt; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Is Prime: " + isPrime(10));
        System.out.println("Is Prime: " + isPrime(37));
    }
}
