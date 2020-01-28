package com.example.fiskskaldjurandroidapp.Fragments.Orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fiskskaldjurandroidapp.R;

import java.util.ArrayList;

public class OrderListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<OrderData> orderArray;
    private static LayoutInflater inflater = null;

    public OrderListViewAdapter(Context context, ArrayList<OrderData> orderArray){
        this.context = context;
        this.orderArray = orderArray;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return orderArray.size();
    }

    @Override
    public Object getItem(int position) {
        return orderArray.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.list_view_item, null);

            //Retrieve textviews
            TextView txt = view.findViewById(R.id.orders_text1);
            TextView txt1 = view.findViewById(R.id.orders_text2);
            TextView txt2 = view.findViewById(R.id.orders_text3);

            txt.setText(orderArray.get(position).getFishName());
            txt1.setText(orderArray.get(position).getWeight());
            txt2.setText("" + orderArray.get(position).getFridgeId());
        }

        return view;
    }
}
