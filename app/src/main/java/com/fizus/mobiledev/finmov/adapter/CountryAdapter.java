package com.fizus.mobiledev.finmov.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.data.local.Country;

import java.util.List;

public class CountryAdapter extends BaseAdapter {

    private List<Country> countries;

    public CountryAdapter(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_country, null);
        ImageView ivCountryFlag = view.findViewById(R.id.iv_country_flag);
        TextView tvCountryName = view.findViewById(R.id.tv_country_name);
        Country country = countries.get(position);
        ivCountryFlag.setImageResource(country.getCountryFlag());
        tvCountryName.setText(country.getCountryName());
        return view;
    }
}
