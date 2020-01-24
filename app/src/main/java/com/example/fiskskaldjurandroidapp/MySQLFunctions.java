package com.example.fiskskaldjurandroidapp;

import com.jcabi.aspects.Parallel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLFunctions {



    @Parallel
    public static boolean login(final String username, final String password) {

        boolean isValid = false;


        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://78.71.86.80:3306/testAppFiskskaldjur","fiskskaldjur","regergerg");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT passHash FROM test WHERE username = " + username);

            while(rs.next()) {

                boolean s = BCrypt.checkpw("password", rs.getString(1));
                isValid = s;
            }


            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        return isValid;
    }

}



