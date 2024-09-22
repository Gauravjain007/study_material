package com.study.oops.interfaces;

public interface Accessories {

    static int PRICE = 20000;

    // Normal Method Call
    void getAllAccessories();

    /**
     * Private Static Method - cannot be overridden and
     * can only be used inside the Interface
     */
    private static String getPrice() {
        return "PRICE=" + PRICE;
    }

    /**
     * Private Method - cannot be overridden and
     * can only be used inside the Interface
     */
    private String getMediaPlayerDetails() {
        return "Company: 'Sony', Manufacturing: 'Made in India'";
    }

    /**
     * Default Method - which may/may not be overridden
     */
    default void showMediaPlayer() {
        System.out.println("Media Player: \n" + getMediaPlayerDetails() + "\n" + getPrice());
    }
}
