package com.backend.projectapi.DTO;

import com.backend.projectapi.model.Lokacija;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PrijavaDTO {
    private Long tipOstecenja;
    private String opis;
    private Double latitude;
    private Double longitude;
    private MultipartFile[] slike;

    public PrijavaDTO(Long tipOstecenja, String opis, Double latitude, Double longitude, MultipartFile[] slike) {
        this.tipOstecenja = tipOstecenja;
        this.opis = opis;
        this.latitude = latitude;
        this.longitude = longitude;
        this.slike = slike;
    }

    public Long getTipOstecenja() {
        return tipOstecenja;
    }

    public void setTipOstecenja(Long tipOstecenja) {
        this.tipOstecenja = tipOstecenja;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public MultipartFile[] getSlike() {
        return slike;
    }

    public void setSlike(MultipartFile[] slike) {
        this.slike = slike;
    }
}
