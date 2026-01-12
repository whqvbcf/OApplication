package com.example.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * 自定义下拉选择器适配器
 */
public class CustomSpinnerAdapter extends ArrayAdapter<CharSequence> {
    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, int resource, CharSequence[] objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
        }

        CheckedTextView checkedTextView = view.findViewById(android.R.id.text1);
        checkedTextView.setText(getItem(position));

        return view;
    }
}

