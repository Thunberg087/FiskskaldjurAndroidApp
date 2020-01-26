package com.example.fiskskaldjurandroidapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fiskskaldjurandroidapp.LocationSender;
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

        setViewProperties();
        //Switch
        autoLoginSwitchChanged();
        //Buttons
        logoutButtonTapped();
        changePassButtonTapped();

        return view;
    }

    //Will display username in textview
    public  void setViewProperties(){
        TextView user = view.findViewById(R.id.settings_logged_as);
        Switch autoLog = view.findViewById(R.id.settings_auto_login);

        SharedPreferences sp = getContext().getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
        String username = sp.getString("currentUser", getResources().getString(R.string.settings_no_user));
        boolean autoLogin = sp.getBoolean("autoLogin", false);

        if(autoLogin){
            autoLog.setChecked(true);
        }
        else{
            autoLog.setChecked(false);
        }

        user.setText(username);
    }

    //Switch
    public void autoLoginSwitchChanged(){
        final Switch autoLog = view.findViewById(R.id.settings_auto_login);
        autoLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences
                SharedPreferences prefs = getContext().getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefs.edit();

                //Check if Switch button is either true or false.
                if(autoLog.isChecked()){
                    System.out.println("Should be logged in automatically");
                    prefEditor.putBoolean("autoLogin", true);
                }
                else{
                    System.out.println("Should NOT be logged in automatically");
                    prefEditor.putBoolean("autoLogin", false);
                }

                prefEditor.apply();
            }
        });
    }

    //Buttons
    public void logoutButtonTapped(){
        Button logout = view.findViewById(R.id.settings_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle the logout here.

                SharedPreferences prefs = getContext().getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefs.edit();
                prefEditor.putBoolean("autoLogin", false);
                prefEditor.putBoolean("isSendingLocation", false);
                prefEditor.apply();



                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
            }
        });
    }

    //Button for changing the password
    public void changePassButtonTapped(){
        Button changePass = view.findViewById(R.id.settings_change_pass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ChangePassFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.master_frame_holder, fragment);
                ft.commit();
                System.out.println("Handle the password change here");
            }
        });
    }

}
