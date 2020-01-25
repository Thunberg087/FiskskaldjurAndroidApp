package com.example.fiskskaldjurandroidapp;

import android.net.IpSecManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLFunctions {

    private static boolean isValid = false;
    public static boolean login(final String username, final String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection(
                            "jdbc:mysql://78.71.86.80:3306/testAppFiskskaldjur","fiskskaldjur","regergerg");
                    Statement stmt=con.createStatement();
                    String query = "SELECT passHash FROM test WHERE username = '"+ username +"'";
                    ResultSet rs=stmt.executeQuery(query);

                    while(rs.next()) {
                        isValid = BCrypt.checkpw(password, rs.getString(1));

                        System.out.println(isValid + "<.daspdfsafpa");
                    }
                    con.close();
                    Thread.currentThread().interrupt();
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }).start();

        //Waiting for thread to finish.
        try{
            Thread.currentThread().join(1000); //Får se hur stabil denna blir. :)
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }


        System.out.println(isValid + " isvalid5");
        return isValid;
    }


}





