package com.example.green_gardening_app;

import java.util.List;

public class ThingSpeakResponse {
    private List<Feed> feeds;

    // Getter and setter
    public List<Feed> getFeeds() { return feeds; }
    public void setFeeds(List<Feed> feeds) { this.feeds = feeds; }

    public static class Feed {
        private String field1; // Temperature
        private String field2; // Soil Moisture

        // Getter and setter
        public String getField1() { return field1; }
        public void setField1(String field1) { this.field1 = field1; }
        public String getField2() { return field2; }
        public void setField2(String field2) { this.field2 = field2; }
    }
}

