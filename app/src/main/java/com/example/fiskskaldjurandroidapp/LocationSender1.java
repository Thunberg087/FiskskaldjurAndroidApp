package com.example.fiskskaldjurandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class LocationSender1 extends AppCompatActivity {
    private static final LocationSender1 ourInstance = new LocationSender1();

    public static void setIsSendingLocation(boolean isSendingLocation) {
        LocationSender1.isSendingLocation = isSendingLocation;
    }

    private static SharedPreferences prefs;
    private static boolean isSendingLocation = false;

    public static LocationSender1 getInstance() {
        return ourInstance;
    }

    static Context context;

    public static void init(Context context)
    {
        LocationSender1.context = context;
        if(prefs == null)
            prefs = context.getSharedPreferences("userPerferences", Activity.MODE_PRIVATE);
    }

    private LocationSender1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    while (true) {

                        isSendingLocation = prefs.getBoolean("isSendingLocation", false);
                        System.out.println("awddd");
                        if (isSendingLocation) {
                            System.out.println("Sending location...");

                            MySQLFunctions.sendLocationToDB(prefs.getString("currentUser", null), context);
                        }
                        sleep(1000*10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();


    }


}
