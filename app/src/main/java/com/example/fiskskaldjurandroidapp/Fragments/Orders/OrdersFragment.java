package com.example.fiskskaldjurandroidapp.Fragments.Orders;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fiskskaldjurandroidapp.R;

import java.util.ArrayList;

/**
 * {@link OrdersFragment}.
 */
public class OrdersFragment extends Fragment {

    private View view;
    private ListView listView;
    private ArrayList<OrderData> orderArray = new ArrayList<>();


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        getDataFromMySql();

        return view;
    }

    public void getDataFromMySql(){
        //TODO: Fix MySQL for this

        //Using this temporarily.
        OrderData orderData = new OrderData("Kräftor", "5kg", 3);
        orderArray.add(orderData);

        OrderData orderData1 = new OrderData("Räkor", "3kg", 3);
        orderArray.add(orderData1);


        listView = view.findViewById(R.id.list_view);
        //listView = getActivity().findViewById(R.id.list_view);
        listView.setAdapter(new OrderListViewAdapter(getContext(), orderArray));
    }

}
