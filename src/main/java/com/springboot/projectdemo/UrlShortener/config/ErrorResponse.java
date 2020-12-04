package com.springboot.projectdemo.UrlShortener.config;

public class ErrorResponse {
    private String response;

    ErrorResponse(RuntimeException e){
        response = e.getMessage();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
