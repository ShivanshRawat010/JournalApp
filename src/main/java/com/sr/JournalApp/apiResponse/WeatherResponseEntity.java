package com.sr.JournalApp.apiResponse;

import lombok.Data;

@Data
public class WeatherResponseEntity{
    public CurrentConditions currentConditions;
    @Data
    public class CurrentConditions{
        public String datetime;
        public int datetimeEpoch;
        public double temp;
        public double feelslike;
        public double humidity;
    }

}

