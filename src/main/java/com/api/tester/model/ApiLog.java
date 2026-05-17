package com.api.tester.model;

public class ApiLog {
    private String api;

    private String method;
    private long duration;
    private int statusCode;
    private long timestamp;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public long getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(long time) {
        this.timestamp = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
