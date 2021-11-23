package com.example.weatherappui;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
    Call<Pojo>getWeather(@Query("lat") double lat,@Query("lon") double lon,@Query("appid") String appid);
    @GET("weather")
    Call<Pojo>getCityWeather(@Query("q")String city,@Query("units")String cel,@Query("appid")String appid);
    @GET("onecall")
    Call<PostPojo>getReWeather(@Query("lat")double lat,@Query("lon")double lon,@Query("exclude")String part,@Query("appid")String appid, @Query("units")String cel);
}
