package com.example.fiskskaldjurandroidapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLFunctions {

    private static boolean isLoginValid = false;
    public static boolean login(final String username, final String password) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con= DriverManager.getConnection("jdbc:mysql://78.71.86.80:3306/testAppFiskskaldjur", "fiskskaldjur", "regergerg");
                    Statement stmt=con.createStatement();
                    String query = "SELECT passHash FROM test WHERE username = '"+ username +"'";
                    ResultSet rs=stmt.executeQuery(query);
                    while(rs.next()) {
                        isLoginValid = BCrypt.checkpw(password, rs.getString(1));
                    }
                    con.close();
                    Thread.interrupted();
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

    //TODO: Fix a method for changing the password
    public static boolean changePassword(String username, String password, String newPassword){
        System.out.println("To: Jonathan, From: Putte, Body: Fix MySQL for dis one pls, i dunno that");
        System.out.println("Username:" + username + " Password:" + password + " New Password:" + newPassword);

        //Return true or false depending on if password was changed or not.
        return true;
    }
}





