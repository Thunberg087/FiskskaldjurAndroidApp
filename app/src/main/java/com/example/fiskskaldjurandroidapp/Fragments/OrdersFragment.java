package com.example.fiskskaldjurandroidapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fiskskaldjurandroidapp.LocationSender;
import com.example.fiskskaldjurandroidapp.R;

/**
 * {@link OrdersFragment}.
 */
public class OrdersFragment extends Fragment {


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        LocationSender.init(getContext());

        return view;
    }

}
