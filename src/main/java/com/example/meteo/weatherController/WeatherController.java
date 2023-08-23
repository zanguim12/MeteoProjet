package com.example.meteo.weatherController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.security.auth.message.ServerAuth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {
    private final RestTemplate restTemplate =   new RestTemplate();

    private String apiBaseUrl = "https://api.openweathermap.org/data/2.5/";
    private  String apiKey = "5325e2a401f5653c06eed8a86c3658b5";

    @GetMapping("/weather/{nomVille}")
    public ResponseEntity<String> getWeather(@PathVariable String nomVille){
        String url = apiBaseUrl + "weather?q=" + nomVille + "&units=metric&APPID=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response);
        //return response;

        ObjectMapper objectMapper = new ObjectMapper();
        double lat= 0.0;
        double lon = 0.0;
        try{
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            JsonNode coordNode = jsonNode.get("coord");

            lon = coordNode.get("lon").asDouble();
            lat = coordNode.get("lat").asDouble();

            System.out.println("Longitude:"+ lon);
            System.out.println("Latitude"+ lat);
        }catch (Exception e){
            e.printStackTrace();
        }
        String url2 = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon + "&units=metric&exclude=hourly,daily&appid=5325e2a401f5653c06eed8a86c3658b5";
        ResponseEntity<String> response1 = restTemplate.getForEntity(url2, String.class);

        return response1;
    }

}
