package com.example.green_gardening_app;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ThingSpeakAPI {
    //Fetching Soil Moisture Sensor data
    @GET("channels/2508787/feeds.json")
    Call<ThingSpeakResponse> getLatestData(@Query("api_key") String apiKey, @Query("results") int results);

    //Fetching temperature data
    @GET("channels/2508787/feeds.json")
    Call<ThingSpeakResponse> getLatestTemperature(@Query("api_key") String apiKey, @Query("results") int results);

}