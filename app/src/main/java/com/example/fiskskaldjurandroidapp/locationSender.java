package com.example.fiskskaldjurandroidapp;

import android.util.Log;

import static java.lang.Thread.sleep;

public class locationSender {
    private static final locationSender ourInstance = new locationSender();

    public static locationSender getInstance() {
        return ourInstance;
    }

    private locationSender() {

        while (true) {
            System.out.println("hej");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


}
