package com.fizus.mobiledev.finmov.data.local;

public class Country {

    private String countryCode;
    private String countryName;
    private int countryFlag;

    public Country(String countryCode, String countryName, int countryFlag) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.countryFlag = countryFlag;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getCountryFlag() {
        return countryFlag;
    }
}
