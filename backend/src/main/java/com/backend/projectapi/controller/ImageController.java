package com.backend.projectapi.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class ImageController extends ApplicationController{

    @GetMapping("/getImage/{id}/{name}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable("id") String id,@PathVariable("name") String name) {
        // Load image dynamically based on imageName
        // ...
        System.out.println("davor je picka mala");

        // Replace "path/to/dynamic/images" with the actual directory where you store dynamic images
        Path imagePath = Paths.get("src/main/resources/static/").resolve(id).resolve(name);

        Resource resource;

        try {
            resource = new UrlResource(imagePath.toUri());
            if (resource.exists() && resource.isReadable()) {

                String[] type = resource.getFilename().split("\\\\.");
                 String typee=type[type.length-1];

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "filename=" + resource.getFilename())
                        .header(HttpHeaders.CONTENT_TYPE, "image/"+typee)
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

}
