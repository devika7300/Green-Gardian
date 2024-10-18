package com.example.green_gardening_app;


import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static final int MAX_MOISTURE_LEVEL = 500 ;
    private TextView textViewTemperature, textViewMoisture;
    private Handler handler = new Handler();
    private static final long UPDATE_INTERVAL = 15000; // 15seconds



    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize TextViews
        textViewTemperature = view.findViewById(R.id.textViewTemperature);
        textViewMoisture = view.findViewById(R.id.textViewMoisture);

        // Setup Retrofit and start fetching data
        setupRetrofitAndFetch();

        return view;
    }

    // Method to setup Retrofit and fetch data periodically
    private void setupRetrofitAndFetch() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thingspeak.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ThingSpeakAPI thingSpeakAPI = retrofit.create(ThingSpeakAPI.class);

        // Define a Runnable to periodically fetch data
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                // Enqueue the API call to fetch latest data
                thingSpeakAPI.getLatestData("A2ZE4F2SHJL9CRW6", 2).enqueue(new Callback<ThingSpeakResponse>() {
                    @Override
                    public void onResponse(Call<ThingSpeakResponse> call, Response<ThingSpeakResponse> response) {
                        // Handle successful response
                        if (response.isSuccessful() && response.body() != null) {
                            ThingSpeakResponse responseBody = response.body();

                            // Update temperature
                            String temperatureStr = responseBody.getFeeds().get(0).getField1();
                            updateTemperatureDisplay(temperatureStr);

                            // Update moisture
                            String moistureStr = responseBody.getFeeds().get(0).getField2();
                            updateMoistureDisplay(moistureStr);

                        }
                    }

                    @Override
                    public void onFailure(Call<ThingSpeakResponse> call, Throwable t) {
                        // Log and display error if data fetch fails
                        Log.e("HomeFragment", "Failed to fetch data from ThingSpeak", t);
                        textViewTemperature.setText("Failed to get temperature");
                        textViewMoisture.setText("Failed to get moisture");
                    }
                });
                // Schedule next execution after UPDATE_INTERVAL
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
        // Start the periodic data fetching task
        handler.post(runnableCode);
    }
    // Method to update temperature display
    private void updateTemperatureDisplay(String temperatureValue) {
        float temperature = Float.parseFloat(temperatureValue);
        String temperatureText = String.format(Locale.getDefault(), "Temp %.2f°C", temperature);
        textViewTemperature.setText(temperatureText);
    }

    // Method to update moisture display
    private void updateMoistureDisplay(String moistureValue) {
        int moisture = Integer.parseInt(moistureValue);
        String moistureText = String.format(Locale.getDefault(), "Moisture %d%%", (moisture * 100) / MAX_MOISTURE_LEVEL); // Assuming MAX_MOISTURE_LEVEL is the max value for moisture
        textViewMoisture.setText(moistureText);
    }

    // Remove all callbacks and messages when the fragment is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}


