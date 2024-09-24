package com.study.oops.concepts;

public class EnumsImpl {
    /**
     * Declaring an Enum
     */
    enum Week {
        // Enum Constants always public, static and final
        // Type = Week
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

        /**
         * Enum Constructor
         * It can be either private/default only and cannot be public/protected
         * To avoid creating of multiple new Objects
         * Internally: public static final Week MONDAY = new Week();
         */
        Week() {
            System.out.println("Constructor called for: " + this);
        }
    }

    public static void main(String[] args) {
        Week week = Week.THURSDAY;
        System.out.println(week + " - " + week.ordinal());

        System.out.println("Printing Enum Values: ");
        for (Week day : Week.values()) {
            System.out.println(day.name());
        }
    }
}