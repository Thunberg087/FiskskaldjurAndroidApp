package com.example.fiskskaldjurandroidapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fiskskaldjurandroidapp.LocationSender1;
import com.example.fiskskaldjurandroidapp.MainActivity;
import com.example.fiskskaldjurandroidapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View view;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setttings, container, false);

        logoutButtonTapped();

        return view;
    }

    public void logoutButtonTapped(){
        Button logout = view.findViewById(R.id.tempButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle the logout here.

                SharedPreferences prefs = getContext().getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefs.edit();
                prefEditor.putBoolean("autoLogin", false);
                prefEditor.putBoolean("isSendingLocation", false);
                prefEditor.apply();



                LocationSender1.setIsSendingLocation(false);

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);

            }
        });

    }

}
