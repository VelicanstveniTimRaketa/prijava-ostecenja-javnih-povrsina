package com.backend.projectapi.service;

import com.backend.projectapi.model.Prijava;

public interface SlikaService {

    public void saveImage(String base64Image, Prijava prijava);
}
