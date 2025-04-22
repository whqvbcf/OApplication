package com.example.myapplication;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WifiSpinnerAdapter extends ArrayAdapter<String> {

    private int selectedPosition = -1;

    public WifiSpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = createViewFromResource(position, convertView, parent);
        TextView textView = view.findViewById(R.id.spinner_text);
        textView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL); // 设置文字靠右并垂直居中
        // 隐藏分割线，因为这是显示在 Spinner 上的视图
        View divider = view.findViewById(R.id.divider);
        divider.setVisibility(View.GONE);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = createViewFromResource(position, convertView, parent);
        ImageView checkMark = view.findViewById(R.id.check_mark);
        checkMark.setVisibility(position == selectedPosition ? View.VISIBLE : View.GONE);

        // 动态设置分割线
        View divider = view.findViewById(R.id.divider);
        if (position == getCount() - 1) {
            divider.setVisibility(View.GONE); // 最后一项不显示分割线
        } else {
            divider.setVisibility(View.VISIBLE);
        }

        // 设置顶部和底部的 padding
        int topPadding = (position == 0) ? 16 : 0;
        int bottomPadding = (position == getCount() - 1) ? 16 : 0;
        view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);

        return view;
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.spinner_text);
        textView.setText(getItem(position));
        return convertView;
    }

    public void setSelection(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
}