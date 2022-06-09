package com.example.mrdrink.ui.filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.mrdrink.R;

import java.util.List;


public class FilterList extends BaseAdapter {
    Context context;
    public List<String> filtersList;

    LayoutInflater inflter;

    public FilterList(Context applicationContext, List<String> newFiltersList) {
        this.context = context;
        filtersList = newFiltersList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return filtersList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String filterName = filtersList.get(i);
        Context c = viewGroup.getContext();

        view = inflter.inflate(R.layout.filter_item, null);

        CheckBox filterCB = (CheckBox) view.findViewById(R.id.filter_cb);
        filterCB.setText(filterName);

        return view;
    }
}
