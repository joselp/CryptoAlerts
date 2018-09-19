package com.jp.cryptoalerts.service;

import com.jp.cryptoalerts.pojos.Price;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PriceService {

    String API_ROUTE = "/data/price";

    @GET(API_ROUTE)
    Call<Price> getPrice(@Query("fsym") String coin, @Query("tsyms") String currency);

}
