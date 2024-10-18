package com.example.green_gardening_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HealthGraphFragment extends Fragment {

    private WebView webView1, webView2;
    private TextView errorText;  // Member variable for error text

    public HealthGraphFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_graph, container, false);

        // Initialize the WebViews and error text
        webView1 = view.findViewById(R.id.webview1);
        webView2 = view.findViewById(R.id.webview2);
        errorText = view.findViewById(R.id.error_text);

        // Setup each WebView with the appropriate URL for each field's graph
        setupWebView(webView1, "https://thingspeak.com/channels/2508787/charts/1?api_key=A2ZE4F2SHJL9CRW6&type=line");
        setupWebView(webView2, "https://thingspeak.com/channels/2508787/charts/2?api_key=A2ZE4F2SHJL9CRW6&type=line");

        return view;
    }

    // Method to setup a WebView with a given URL
    private void setupWebView(WebView webView, String url) {
        if (webView != null) {
            // Enable JavaScript
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                // Override to handle URL loading
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return false;
                }

                // Override to handle page loading errors
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    if (errorText != null) {
                        errorText.setVisibility(View.VISIBLE);
                        errorText.setText("Failed to load graph, please check your connection.");
                    }
                    Log.e("WebView", "Error loading page: " + error.getDescription().toString());
                }
            });
            // Load the URL
            webView.loadUrl(url);
        } else {
            Log.e("HealthGraphFragment", "WebView is not found.");
        }
    }
}
