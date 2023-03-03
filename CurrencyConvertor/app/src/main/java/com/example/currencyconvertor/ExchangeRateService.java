package com.example.currencyconvertor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeRateService {
    @GET("convert")
    Call<ExchangeRateResponse> getExchangeRate(@Query("from") String fromCurrency, @Query("to") String toCurrency, @Query("amount") String amount);
}
