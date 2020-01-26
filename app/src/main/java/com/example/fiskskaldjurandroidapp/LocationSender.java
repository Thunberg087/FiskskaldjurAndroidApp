package com.example.fiskskaldjurandroidapp;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static java.lang.Thread.sleep;

public class LocationSender extends Service {

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private static SharedPreferences prefs;
    private static boolean isSendingLocation = false;
    private static String MySQLUrl = "jdbc:mysql://78.71.86.80:3306/testAppFiskskaldjur";
    private static String MySQLUser = "fiskskaldjur";
    private static String MySQLPass = "regergerg";
    private LocationManager locationManager;
    private LocationListener locationListener;



    @Override
    public void onCreate() {

        prefs = getSharedPreferences("userPerferences", MODE_PRIVATE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }



        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (prefs.getBoolean("isSendingLocation", false)) {
                    sendLocation(location.getLatitude(), location.getLongitude());

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };


        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);



    }

    private void sendLocation(final double latitude, final double longitude) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try
                {

                    System.out.println("Sending location!");
                    // create a mysql database connection
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(MySQLUrl, MySQLUser, MySQLPass);


                    // the mysql insert statement
                    String query = "INSERT INTO locations (username, latitude, longitude)" + " values (?, ?, ?)";

                    // create the mysql insert prepared statement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString(1, prefs.getString("currentUser", null));
                    preparedStmt.setDouble(2, longitude);
                    preparedStmt.setDouble(3, latitude);


                    // execute the prepared statement
                    preparedStmt.execute();

                    con.close();
                }
                catch (Exception e)
                {
                    System.err.println("Got an exception!");
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    if (locationManager != null) {
        locationManager.removeUpdates(locationListener);
    }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        // do your jobs here

//
//        prefs = getSharedPreferences("userPerferences", MODE_PRIVATE);
//
//
//
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(3000);
//                    while (true) {
//
//                        isSendingLocation = prefs.getBoolean("isSendingLocation", false);
//                        if (isSendingLocation) {
//                            System.out.println("Sending location...");
//
//                            Thread thread = new Thread() {
//                                @Override
//                                public void run() {
//                                    try
//                                    {
//
//
//                                        // create a mysql database connection
//                                        Class.forName("com.mysql.jdbc.Driver");
//                                        Connection con = DriverManager.getConnection(MySQLUrl, MySQLUser, MySQLPass);
//
//                                        double longitude = 0.0;
//                                        double latitude = 0.0;
//
//                                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                                                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                                                longitude = location.getLongitude();
//                                                latitude = location.getLatitude();
//
//                                            }
//                                        }
//
//                                        // the mysql insert statement
//                                        String query = "INSERT INTO locations (username, latitude, longitude)" + " values (?, ?, ?)";
//
//                                        // create the mysql insert prepared statement
//                                        PreparedStatement preparedStmt = con.prepareStatement(query);
//                                        preparedStmt.setString(1, prefs.getString("currentUser", null));
//                                        preparedStmt.setDouble(2, longitude);
//                                        preparedStmt.setDouble(3, latitude);
//
//
//                                        // execute the prepared statement
//                                        preparedStmt.execute();
//
//                                        con.close();
//                                    }
//                                    catch (Exception e)
//                                    {
//                                        System.err.println("Got an exception!");
//                                        System.err.println(e.getMessage());
//                                    }
//                                }
//                            };
//
//                            thread.start();
//                        }
//                        sleep(1000*10);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        thread.start();

        // do your jobs here

        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        createNotificationChannel();

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}