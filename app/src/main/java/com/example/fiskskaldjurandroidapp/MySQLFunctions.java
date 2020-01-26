package com.example.fiskskaldjurandroidapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLFunctions {

    private static String MySQLUrl = "jdbc:mysql://78.71.86.80:3306/testAppFiskskaldjur";
    private static String MySQLUser = "fiskskaldjur";
    private static String MySQLPass = "regergerg";


    private static boolean isLoginValid = false;
    public static boolean login(final String username, final String password) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection(MySQLUrl, MySQLUser, MySQLPass);
                    Statement stmt=con.createStatement();
                    String query = "SELECT passHash FROM test WHERE username = '"+ username +"'";
                    ResultSet rs=stmt.executeQuery(query);
                    while(rs.next()) {
                        isLoginValid = BCrypt.checkpw(password, rs.getString(1));
                    }
                    con.close();
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        };

        thread.start();
        try{
            thread.join();
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }

        return isLoginValid;
    }


    public static void sendLocationToDB(final String currentUser, final Context context) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try
                {


                    // create a mysql database connection
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(MySQLUrl, MySQLUser, MySQLPass);

                    double longitude = 0.0;
                    double latitude = 0.0;

                    LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();

                        }
                    }

                    // the mysql insert statement
                    String query = "INSERT INTO locations (username, latitude, longitude)" + " values (?, ?, ?)";

                    // create the mysql insert prepared statement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString(1, currentUser);
                    preparedStmt.setDouble(2, longitude);
                    preparedStmt.setDouble(3, latitude);


                    // execute the prepared statement
                    preparedStmt.execute();

                    con.close();
                }
                catch (Exception e)
                {
                    System.err.println("Got an exception!");
                    System.err.println(e.getMessage());
                }
            }
        };

        thread.start();

    }


}





