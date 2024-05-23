package com.tech.m2.dto;

public class WeatherDto {
    private String weatherMain;
    private String weatherDescription;
    private String tempCelsius;
    private String windSpeed;
    private String windDeg;
    private String windDeg_origin;

    public String getWindDeg_origin() {
        return windDeg_origin;
    }

    public void setWindDeg_origin(String windDeg_origin) {
        this.windDeg_origin = windDeg_origin;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getTempCelsius() {
        return tempCelsius;
    }

    public void setTempCelsius(String tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }
}
