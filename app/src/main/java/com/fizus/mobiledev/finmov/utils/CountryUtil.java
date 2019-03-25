package com.fizus.mobiledev.finmov.utils;

import com.fizus.mobiledev.finmov.R;
import com.fizus.mobiledev.finmov.data.local.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryUtil {
    public static List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        Country global = new Country("", "Global", R.drawable.flag_earth);
        countries.add(global);
        Country indonesia = new Country("ID", "Indonesia", R.drawable.flag_id);
        countries.add(indonesia);
        return countries;
    }
}
