package com.backend.projectapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * Testna klasa za isprobavanje rada RoadAPI-a
 */
@RestController
@RequestMapping("test/api")
public class TestRoadsAPIController {

    /**
     *
     * @param lat latitude
     * @param lon longitude
     * @return response - JSON koji se sastoji od koordinata ceste i ostalih stvari
     */
    @GetMapping()
    public String nearestRoad(@RequestParam String lat,@RequestParam String lon){

        RestTemplate restTemplate=new RestTemplate();

        String url="https://roads.googleapis.com/v1/nearestRoads?points={points}&key={key}";

        String response=restTemplate.getForObject(url,String.class,lat+","+lon,"AIzaSyDvl5jScXppKWZFz6vaPtasP7pPGutq8T8");

        //response bi se kasnije prosljedivao u Service dio na daljnu obradu
        return response;

    }
}
