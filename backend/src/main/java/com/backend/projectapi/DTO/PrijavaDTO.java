package com.backend.projectapi.DTO;

import com.backend.projectapi.model.Lokacija;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PrijavaDTO {
    private Long tipOstecenja;
    private String opis;
    private Lokacija lokacija;
    private List<MultipartFile> slike;

    public PrijavaDTO(Long tipOstecenja, String opis, Lokacija lokacija, List<MultipartFile> slike) {
        this.tipOstecenja = tipOstecenja;
        this.opis = opis;
        this.lokacija = lokacija;
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

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public List<MultipartFile> getSlike() {
        return slike;
    }

    public void setSlike(List<MultipartFile> slike) {
        this.slike = slike;
    }
}
