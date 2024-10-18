package com.example.green_gardening_app;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TemperatureFragment extends Fragment {

    //TextViews
    private TextView temperaturePercentage;
    private TextView lightStatus;

    // Handler for scheduling temperature updates
    private Handler handler = new Handler();

    // Retrofit instance and ThingSpeak API interface
    private Retrofit retrofit;
    private ThingSpeakAPI thingSpeakAPI;


    // Update interval for fetching temperature data
    private static final long UPDATE_INTERVAL = 15000; // 15 seconds


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        temperaturePercentage = view.findViewById(R.id.temperaturePercentage);
        lightStatus = view.findViewById(R.id.lightStatus);

        // Setup Retrofit and schedule temperature updates
        setupRetrofit();
        scheduleTemperatureUpdate();

        return view;
    }

    // Method to setup Retrofit instance and ThingSpeak API interface
    private void setupRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thingspeak.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        thingSpeakAPI = retrofit.create(ThingSpeakAPI.class);
    }

    // Method to fetch temperature data from ThingSpeak API
    private void fetchTemperature() {
        thingSpeakAPI.getLatestTemperature("A2ZE4F2SHJL9CRW6", 1).enqueue(new Callback<ThingSpeakResponse>() {
            @Override
            public void onResponse(Call<ThingSpeakResponse> call, Response<ThingSpeakResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getFeeds().isEmpty()) {
                    String tempStr = response.body().getFeeds().get(0).getField1();
                    if (tempStr != null && !tempStr.isEmpty()) {
                        float temperature = Float.parseFloat(tempStr);
                        updateTemperatureDisplay(temperature);
                        updateLightStatus(temperature);
                    } else {
                        temperaturePercentage.setText("No temperature data");
                    }
                } else {
                    temperaturePercentage.setText("Error fetching data");
                }
            }

            @Override
            public void onFailure(Call<ThingSpeakResponse> call, Throwable t) {
                temperaturePercentage.setText("Failure: " + t.getMessage());
            }
        });
    }

    // Method to update temperature display
    private void updateTemperatureDisplay(float temperature) {
        int percentage = (int) ((temperature / 35.0) * 100); // Assuming 35°C is 100%
        temperaturePercentage.setText(percentage + "%");
    }

    // Method to update light status based on temperature
    private void updateLightStatus(float temperature) {
        if (temperature < 20) {
            lightStatus.setText("Light: ON");
        } else {
            lightStatus.setText("Light: OFF");
        }
    }

    // Method to schedule periodic temperature updates
    private void scheduleTemperatureUpdate() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                fetchTemperature();
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    // Remove callbacks when fragment is destroyed to avoid memory leaks
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
