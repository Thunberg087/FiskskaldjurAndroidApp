package com.example.fiskskaldjurandroidapp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fiskskaldjurandroidapp.MySQLFunctions;
import com.example.fiskskaldjurandroidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassFragment extends Fragment {

    private View view;
    private String username;

    public ChangePassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_pass, container, false);

        getUsername();
        changePasswordButtonTapped();

        return view;
    }

    public void getUsername(){
        SharedPreferences sp = getContext().getSharedPreferences("userPerferences", Context.MODE_PRIVATE);
        username = sp.getString("currentUser", getResources().getString(R.string.settings_no_user));
    }

    public void  changePasswordButtonTapped(){
        //Retrieve Button by ID
        Button btn = view.findViewById(R.id.password_change);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve EditText by ID
                EditText getCurrentPass = view.findViewById(R.id.password_current);
                EditText getNewPass = view.findViewById(R.id.password_new);
                EditText getRepeatPass = view.findViewById(R.id.password_repeat);

                //Convert into string
                String currentPass = getCurrentPass.getText().toString();
                String newPass = getNewPass.getText().toString();
                String repeatPass = getRepeatPass.getText().toString();

                //Check if newPass is equal to repeatPass
                if(newPass.equals(repeatPass)){
                    boolean passDidChange = MySQLFunctions.changePassword(username, currentPass, newPass);

                    if(passDidChange){
                        Toast.makeText(getContext(), "Ditt lösenord har ändrats!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Lösenordet kunde inte ändras!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Lösenorden matchar inte!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
