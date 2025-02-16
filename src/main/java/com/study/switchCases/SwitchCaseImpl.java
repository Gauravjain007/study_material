package com.study.switchCases;

import java.util.Scanner;

public class SwitchCaseImpl {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Day: ");
        int day = sc.nextInt();
        sc.close();

        basicSwitchCase1(day);
        enhancedSwitchCase1(day);
        basicSwitchCase2(day);
        enhancedSwitchCase2(day);
        String result = enhancedSwitchCase3(day);
        System.out.println(result);
    }

    /**
     * Basic switch case implementation:
     * Prints the day of the week corresponding to the given day number
     * 
     * @param day the given day number
     */
    public static void basicSwitchCase1(int dayNumber) {
        System.out.println("Basic switch case implementation 1: ");
        switch (dayNumber) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Invalid Day");
        }
    }

    /**
     * Enhanced switch case implementation:
     * Prints the day of the week corresponding to the given day number
     * 
     * @param day the given day number
     */
    public static void enhancedSwitchCase1(int dayNumber) {
        System.out.println("Enhanced switch case implementation 1: ");
        switch (dayNumber) {
            case 1 -> System.out.println("Monday");
            case 2 -> System.out.println("Tuesday");
            case 3 -> System.out.println("Wednesday");
            case 4 -> System.out.println("Thursday");
            case 5 -> System.out.println("Friday");
            case 6 -> System.out.println("Saturday");
            case 7 -> System.out.println("Sunday");
            default -> System.out.println("Invalid Day");
        }
    }

    /**
     * Basic switch case implementation to print if the given day is a weekday or a
     * weekend.
     * 
     * @param day the given day
     *            1-5 corresponds to weekdays, 6-7 corresponds to the weekend.
     *            Prints "Invalid Day" for any other value.
     */
    public static void basicSwitchCase2(int dayNumber) {
        System.out.println("Basic switch case implementation 2: ");
        switch (dayNumber) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("Weekday");
                break;
            case 6:
            case 7:
                System.out.println("Weekend");
                break;
            default:
                System.out.println("Invalid Day");
        }
    }

    /**
     * Uses enhanced switch case implementation to print if the given day is a
     * weekday or a weekend.
     *
     * @param day the given day
     *            1-5 corresponds to weekdays, 6-7 corresponds to the weekend.
     *            Prints "Invalid Day" for any other value.
     */

    public static void enhancedSwitchCase2(int dayNumber) {
        System.out.println("Enhanced switch case implementation 2: ");
        switch (dayNumber) {
            case 1, 2, 3, 4, 5 -> System.out.println("Weekday");
            case 6, 7 -> System.out.println("Weekend");
            default -> System.out.println("Invalid Day");
        }
    }

    /**
     * Uses enhanced switch case implementation to print if the given day is a
     * weekday or a weekend
     * and returns a message based on the day
     * 
     * @param day the given day
     * @return a message, either "Weekdays are boring!" or "Weekends are best!" or
     *         "Invalid Day" if the day is invalid
     */
    public static String enhancedSwitchCase3(int dayNumber) {
        System.out.println("Enhanced switch case implementation 3: ");
        return switch (dayNumber) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("Weekday");
                yield "Weekdays are boring!";
            }
            case 6, 7 -> {
                System.out.println("Weekend");
                yield "Weekends are best!";
            }
            default -> "Invalid Day";
        };
    }
}
