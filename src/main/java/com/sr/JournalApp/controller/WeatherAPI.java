package com.sr.JournalApp.controller;

import com.sr.JournalApp.apiResponse.WeatherResponseEntity;
import com.sr.JournalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class WeatherAPI {

    @Value("${weatherApp.api.key}")
    private String key;

    private final AppCache appCache;

    public WeatherAPI(AppCache appCache){
        this.appCache = appCache;
    }

    @GetMapping("/{location}")
    public ResponseEntity<?> getWeather(@PathVariable String location){

        RestClient restClient = RestClient.create();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Map<String,String> cache = appCache.map;

        String url = cache.get("weather_api").replace("<LOCATION_INPUT>", location).replace("<API_KEY>",key);

        WeatherResponseEntity response = restClient.get().uri(url).retrieve().body(WeatherResponseEntity.class);

        return ResponseEntity.ok("for user " + username + " weather is : " + response.getCurrentConditions().getFeelslike());
    }
}
