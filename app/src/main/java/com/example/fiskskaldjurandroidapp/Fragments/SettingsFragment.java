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

import com.example.fiskskaldjurandroidapp.MainActivity;
import com.example.fiskskaldjurandroidapp.R;
import com.example.fiskskaldjurandroidapp.StartScreenFragmentHolder;

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

                SharedPreferences prefs = getContext().getSharedPreferences("loginState", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefs.edit();
                prefEditor.putBoolean("autoLogin", false);
                prefEditor.commit();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

                //TODO: Kollar över detta senare. Får se hur man sköter en korrekt logout i MySQL.
            }
        });

    }

}
