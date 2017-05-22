package com.pangge.fortest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iuuu on 16/10/11.
 */
public class DetailAdapter extends ArrayAdapter<Detail> {
    private int resourceId;
    public DetailAdapter(Context context, int textViewResourceId, List<Detail> objects){


        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Detail detail = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView detailItem = (TextView)view.findViewById(R.id.list_name);
        TextView detailValue = (TextView)view.findViewById(R.id.list_value);

        detailItem.setText(detail.getItem());
        detailValue.setText(detail.getValue());

        return view;
    }
}
