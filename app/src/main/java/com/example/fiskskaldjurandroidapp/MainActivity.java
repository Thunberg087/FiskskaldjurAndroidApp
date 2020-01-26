package com.example.fiskskaldjurandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

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

        retrieveLoginState();
    }

    //Button for logging in user
    public void loginUser(View view) {
        //Retrieving data by ID.
        EditText username = findViewById(R.id.login_username);
        EditText password = findViewById(R.id.login_password);

        //Converting EditText to Strings.
        String user = username.getText().toString();
        String pass = password.getText().toString();

        //Sending a login request to MySQL.
        boolean x = MySQLFunctions.login(user, pass);

        //Check if login is valid.
        if(x){
            SharedPreferences prefs = this.getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.putString("currentUser", user);
            prefEditor.commit();

            navigateToStartScreen();
        }
        else{
            Toast.makeText(getApplicationContext(), "Fel användarnamn eller lösenord.", Toast.LENGTH_LONG).show();
        }

    }

    //Navigating to the start screen
    public void navigateToStartScreen(){
        Intent intent = new Intent(this, StartScreenFragmentHolder.class);
        startActivity(intent);

        //Removing login page from backstack to prevent navigating back.
        finish();
    }

    //Retrieving login state from SharedPreferences
    public void retrieveLoginState(){
        SharedPreferences prefs = this.getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
        boolean autoLogin = prefs.getBoolean("autoLogin", false);

        if(autoLogin){
            navigateToStartScreen();
        }
    }

    //Change the switch button for autologin
    public void changeSwitch(View view) {
        SharedPreferences prefs = this.getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefs.edit();

        Switch keepMeIn = findViewById(R.id.login_switch);

        if(keepMeIn.isChecked()){
            System.out.println("Checked!");
            prefEditor.putBoolean("autoLogin", true);
        }
        else{
            System.out.println("Not checked!");
            prefEditor.putBoolean("autoLogin", false);
        }

        prefEditor.commit();
    }
}


