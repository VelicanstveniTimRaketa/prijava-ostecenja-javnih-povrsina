package com.backend.projectapi.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ovo ce kasnije postati dio SERVICE koda
 */
@RestController
public class RoadsAPI {

    /**
     * Nalazi koordinate najblize ceste iz ili adrese ili koordinata
     *
     * @param address adresa za koju trazimo pripadni segment ceste
     * @param lat     latitude
     * @param lng     longitude
     * @return response - JSON koji se sastoji od koordinata ceste i ostalih stvari
     */
    @GetMapping()
    public String nearestRoad(@RequestParam(required = false) String address, @RequestParam(required = false) String lat, @RequestParam(required = false) String lng) {
        String mapsAPIkey = "";
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder points = new StringBuilder();

        //provjera da su poslani ili adresa ili koordinate
        if (!StringUtils.hasText(address) && (!StringUtils.hasText(lat) || !StringUtils.hasText(lng))) {
            throw new IllegalArgumentException("Ne moguce pronaci koordinate ceste jer ni adresa ni koordinate nisu poslane ");
        }

        if (StringUtils.hasText(address)) {
            //dobijamo koordinate iz adrese pomocu geocodingAPI-a
            String geocodingAPIurl = "https://maps.googleapis.com/maps/api/geocode/json?address={address}&key={key}";
            String geocodingAPIresponse = restTemplate.getForObject(geocodingAPIurl, String.class, address, mapsAPIkey);
            assert geocodingAPIresponse != null;

            //parsiranje geocodingAPIresponsea kako bi iz njega izvukli koordinate
            String location = geocodingAPIresponse.split("location")[1];
            String address_lat = location.split(",")[0].split("\"lat\" : ")[1].trim();
            String address_lng = location.split(",")[1].split("}")[0].split(": ")[1].trim();

            //String points koji ide u roadsAPI kako bi vratio koordinate najblize ceste
            points.append(address_lat).append(",").append(address_lng);
        } else {
            //u slučaju da nije poslana adresa nego samo koordinate, njih stavljamo u points variablu
            points.setLength(0); //u slučaju da su poslani i adresa i koordinate bolje je koristiti samo koordinate
            points.append(lat).append(",").append(lng);
        }
        String roadsAPIurl = "https://roads.googleapis.com/v1/nearestRoads?points={points}&key={key}";
        String response = restTemplate.getForObject(roadsAPIurl, String.class, points, mapsAPIkey);

        //response bi se kasnije prosljedivao u Service dio na daljnu obradu
        return response; // iz nepoznatog razloga vraca detalje za istu lokaciju dva puta, no to nije problem

    }

}
