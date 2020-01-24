package com.example.fiskskaldjurandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        thread.start();

    }


    Thread thread = new Thread() {
        @Override
        public void run() {


                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con= DriverManager.getConnection(
                                "jdbc:mysql://78.71.86.80:3306/testAppFiskskaldjur","fiskskaldjur","regergerg");
                        Statement stmt=con.createStatement();
                        ResultSet rs=stmt.executeQuery("SELECT * FROM test");

                        while(rs.next())
                            System.out.println(rs.getInt(1)+"  "+rs.getString(2));
                        con.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }


        }
    };



}


