package com.example.fiskskaldjurandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fiskskaldjurandroidapp.Fragments.MapFragment;
import com.example.fiskskaldjurandroidapp.Fragments.OrdersFragment;
import com.example.fiskskaldjurandroidapp.Fragments.SettingsFragment;

import static java.lang.Thread.sleep;

public class StartScreenFragmentHolder extends AppCompatActivity {

    //Fragment manager
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen_fragment_holder);

        startService(new Intent(this, LocationSender.class));


        //Get support fragment manager
        fragmentManager = getSupportFragmentManager();

        //Set view as orders fragment
        OrdersFragment employeeOrdersFragment = new OrdersFragment();
        fragmentManager.beginTransaction()
                .add(R.id.master_frame_holder, employeeOrdersFragment).commit();
    }


    // Navbar navigation
    public void gotoOrders(View view) {
        OrdersFragment ordersFragment = new OrdersFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.master_frame_holder, ordersFragment)
                .commit();

        removeAllNiceBlue();
        ImageButton im = findViewById(R.id.navbar_orders_button);
        im.setColorFilter(ContextCompat.getColor(this, R.color.colorNiceBlue));
    }

    public void gotoMap(View view) {
        MapFragment mapFragment = new MapFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.master_frame_holder, mapFragment)
                .commit();

        removeAllNiceBlue();
        ImageButton im = findViewById(R.id.navbar_map_button);
        im.setColorFilter(ContextCompat.getColor(this, R.color.colorNiceBlue));
    }


    public void gotoSettings(View view) {
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.master_frame_holder, settingsFragment)
                .commit();

        removeAllNiceBlue();
        ImageButton im = findViewById(R.id.navbar_settings_button);
        im.setColorFilter(ContextCompat.getColor(this, R.color.colorNiceBlue));
    }


    //Clearing the blue colors
    public void removeAllNiceBlue() {
        ImageButton imo = findViewById(R.id.navbar_orders_button);
        imo.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite));
        ImageButton ims = findViewById(R.id.navbar_settings_button);
        ims.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite));
        ImageButton imm = findViewById(R.id.navbar_map_button);
        imm.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite));
    }



    public void goBackToStart() {
        OrdersFragment ordersFragment = new OrdersFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.master_frame_holder, ordersFragment)
                .commit();

        removeAllNiceBlue();
        ImageButton im = findViewById(R.id.navbar_orders_button);
        im.setColorFilter(ContextCompat.getColor(this, R.color.colorNiceBlue));
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    FragmentManager fm = getSupportFragmentManager();
//                    MapFragment fragment = (MapFragment)fm.findFragmentById(R.id.map_fragment);
//                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.detach(fragment);
//                    ft.attach(fragment);
//                    ft.commit();
                    finish();
                    startActivity(getIntent());


                } else {

                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    goBackToStart();
                }
                return;
            }

        }
    }
}
