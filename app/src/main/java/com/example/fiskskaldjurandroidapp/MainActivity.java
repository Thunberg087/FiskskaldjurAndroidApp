package com.example.fiskskaldjurandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
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
    }

    public void loginUser(View view) {
        //Retrieving data by ID.
        EditText username = findViewById(R.id.login_username);
        EditText password = findViewById(R.id.login_password);

        //Converting EditText to Strings.
        String user = username.getText().toString();
        String pass = password.getText().toString();

        //Sending a login request to MySQL.
        boolean x = MySQLFunctions.login(user, pass);

        if(x == true){
            Toast.makeText(getApplicationContext(), "Inloggning lyckades.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, StartScreenFragmentHolder.class);
            startActivity(intent);

            //Removing login page from backstack to prevent navigating back.
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Fel användarnamn eller lösenord.", Toast.LENGTH_LONG).show();
        }

    }

}


