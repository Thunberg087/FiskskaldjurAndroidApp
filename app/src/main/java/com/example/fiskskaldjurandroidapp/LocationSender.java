package com.example.fiskskaldjurandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class LocationSender extends AppCompatActivity {
    private static final LocationSender ourInstance = new LocationSender();

    public static void setIsSendingLocation(boolean isSendingLocation) {
        LocationSender.isSendingLocation = isSendingLocation;
    }

    private static SharedPreferences prefs;
    private static boolean isSendingLocation = false;

    public static LocationSender getInstance() {
        return ourInstance;
    }

    public static void init(Context context)
    {
        if(prefs == null)
            prefs = context.getSharedPreferences("userPerferences", Activity.MODE_PRIVATE);
    }

    private LocationSender() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sleep(5000);
                        isSendingLocation = prefs.getBoolean("isSendingLocation", false);

                        if (isSendingLocation) {
                            System.out.println("Sending location...");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();


    }


}
