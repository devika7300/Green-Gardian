package com.example.green_gardening_app;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WaterFragment extends Fragment {

    private View waterLevelView;
    private TextView percentageIndicator;
    private Handler handler = new Handler(); // Handler for scheduling periodic updates
    private static final int MAX_MOISTURE_LEVEL = 500; // Full scale is 500
    private static final int GOOD_MOISTURE_LEVEL = 300; // Good moisture level

    private static final long UPDATE_INTERVAL = 10000; // 10 seconds




    public WaterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container, false);
        waterLevelView = view.findViewById(R.id.water_level);
        percentageIndicator = view.findViewById(R.id.percentage_indicator);

        // Setup Retrofit and start fetching data
        setupRetrofitAndFetch();

        return view;
    }

    // Method to setup Retrofit instance and fetch data periodically
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
                thingSpeakAPI.getLatestData("A2ZE4F2SHJL9CRW6", 1).enqueue(new Callback<ThingSpeakResponse>() {
                    @Override
                    public void onResponse(Call<ThingSpeakResponse> call, Response<ThingSpeakResponse> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().getFeeds().isEmpty()) {
                            int currentMoisture = Integer.parseInt(response.body().getFeeds().get(0).getField2());
                            updateWaterLevel(currentMoisture);
                        }
                    }

                    @Override
                    public void onFailure(Call<ThingSpeakResponse> call, Throwable t) {
                        // Handle failure
                    }
                });
                // Schedule next execution after UPDATE_INTERVAL
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
        // Start the periodic data fetching task
        handler.post(runnableCode);
    }


    private void updateWaterLevel(int currentMoisture) {
        int containerHeight = getResources().getDimensionPixelSize(R.dimen.water_container_height);  // 300dp defined in dimens.xml
        // The good water level marker is at 60% of the container's height
        int goodWaterLevelHeight = (int)(GOOD_MOISTURE_LEVEL / (float)MAX_MOISTURE_LEVEL * containerHeight);

        // Set the water level to match the soil moisture percentage of the full scale
        float percentage = (currentMoisture / (float) MAX_MOISTURE_LEVEL) * 100;
        int newHeight = (int) (percentage / 100 * containerHeight);

        // Update water pump status text
        TextView waterPumpStatusTextView = getView().findViewById(R.id.water_pump_status);
        if (currentMoisture < 300) {
            waterPumpStatusTextView.setText(getString(R.string.water_pump_status, "ON"));
        } else {
            waterPumpStatusTextView.setText(getString(R.string.water_pump_status, "OFF"));
        }

        // Animate the water level view
        ValueAnimator animator = ValueAnimator.ofInt(waterLevelView.getLayoutParams().height, newHeight);
        animator.setDuration(500); // Animation duration
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = waterLevelView.getLayoutParams();
            params.height = (int) animation.getAnimatedValue();
            waterLevelView.setLayoutParams(params);
        });
        animator.start();

        // Update the percentage indicator text
        percentageIndicator.setText(String.format(Locale.getDefault(), "%.0f%%", percentage));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null); // Remove callbacks when fragment is destroyed
    }
}

