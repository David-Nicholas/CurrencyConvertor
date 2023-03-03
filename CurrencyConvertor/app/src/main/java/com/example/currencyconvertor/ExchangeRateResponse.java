package com.example.currencyconvertor;

import com.google.gson.annotations.SerializedName;

public class ExchangeRateResponse {

    @SerializedName("result")
    private double result;

    public double getResult() {
        return result;
    }

}
