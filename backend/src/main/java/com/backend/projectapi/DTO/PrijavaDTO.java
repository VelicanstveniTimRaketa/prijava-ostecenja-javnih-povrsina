package com.backend.projectapi.DTO;

import com.backend.projectapi.model.Lokacija;
import org.springframework.web.multipart.MultipartFile;

import javax.print.event.PrintJobAttributeEvent;
import java.util.List;

public class PrijavaDTO {
    private Long ured;
    private String naziv;
    private String opis;
    private Double latitude;
    private Double longitude;
    private MultipartFile[] slike;

    public PrijavaDTO(String naziv, String opis, Long uredId, Double latitude, Double longitude, MultipartFile[] slike) {
        this.naziv = naziv;
        this.opis = opis;
        this.ured = uredId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.slike = slike;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getUred() {
        return ured;
    }

    public void setUred(Long ured) {
        this.ured = ured;
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
