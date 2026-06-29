package com.edigest.jorunalApp.service;

import com.edigest.jorunalApp.api.response.WeatherResponse;
import com.edigest.jorunalApp.api.response.cache.AppCache;
import com.edigest.jorunalApp.constants.PlaceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private  String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppCache appCache;

     public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }
        String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString())
                .replace(PlaceHolder.CITY, city)
                .replace(PlaceHolder.API_KEY, apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        if (body != null) {
            redisService.set("weather_of_" + city, body, 300L);
        }
        return body;
    }
}
