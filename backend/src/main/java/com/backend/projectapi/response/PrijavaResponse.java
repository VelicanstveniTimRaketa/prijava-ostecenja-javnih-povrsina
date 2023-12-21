package com.backend.projectapi.response;

import com.backend.projectapi.model.*;
import com.backend.projectapi.repository.GradskiUrediRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;

public class PrijavaResponse {
    private Long Id;
    private String naziv;
    private Lokacija lokacija;
    private GradskiUred gradskiUred;
    private String opis;
    private Korisnik kreator;
    private List<Slika> slike;
    private Prijava parentPrijava;
    private ZonedDateTime prvoVrijemePrijave;
    private ZonedDateTime vrijemeOtklona;

    public PrijavaResponse(Long Id,
                           String naziv,
                           Lokacija lokacija,
                           GradskiUred gradskiUred,
                           String opis,
                           Korisnik kreator,
                           List<Slika> slike,
                           Prijava parentPrijava,
                           ZonedDateTime prvoVrijemePrijave,
                           ZonedDateTime vrijemeOtklona){
        this.Id = Id;
        this.naziv = naziv;
        this.lokacija = lokacija;
        this.gradskiUred = gradskiUred;
        this.opis = opis;
        this.kreator = kreator;
        this.slike = slike;
        this.parentPrijava = parentPrijava;
        this.prvoVrijemePrijave = prvoVrijemePrijave;
        this.vrijemeOtklona = vrijemeOtklona;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public GradskiUred getGradskiUred() {
        return gradskiUred;
    }

    public void setGradskiUred(GradskiUred gradskiUred) {
        this.gradskiUred = gradskiUred;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Korisnik getKreator() {
        return kreator;
    }

    public void setKreator(Korisnik kreator) {
        this.kreator = kreator;
    }

    public List<Slika> getSlike() {
        return slike;
    }

    public void setSlike(List<Slika> slike) {
        this.slike = slike;
    }

    public Prijava getParentPrijava() {
        return parentPrijava;
    }

    public void setParentPrijava(Prijava parentPrijava) {
        this.parentPrijava = parentPrijava;
    }

    public ZonedDateTime getPrvoVrijemePrijave() {
        return prvoVrijemePrijave;
    }

    public void setPrvoVrijemePrijave(ZonedDateTime prvoVrijemePrijave) {
        this.prvoVrijemePrijave = prvoVrijemePrijave;
    }

    public ZonedDateTime getVrijemeOtklona() {
        return vrijemeOtklona;
    }

    public void setVrijemeOtklona(ZonedDateTime vrijemeOtklona) {
        this.vrijemeOtklona = vrijemeOtklona;
    }
}
