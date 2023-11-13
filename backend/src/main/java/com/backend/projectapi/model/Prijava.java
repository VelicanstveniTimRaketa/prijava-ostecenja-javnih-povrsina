package com.backend.projectapi.model;

import com.backend.projectapi.service.TipOstecenjaService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prijave")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Prijava {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @Column(name = "naziv")
    private String naziv;


    @ManyToOne(optional = false)
    @JoinColumn(name = "lokacijaId")
    private Lokacija lokacija;


    @ManyToOne(optional = false)
    @JoinColumn(name = "ostecenjeId")
    private TipOstecenja tipOstecenja;

    @Column(name = "opis")
    private String opis;

    @NonNull
    @ManyToOne(optional = false) //jedna prijava ima samo jednog korisnika a jedan korisnik moze imati vise prijava
    @JoinColumn(name = "kreatorId")
    private Korisnik kreator;

    @OneToMany(mappedBy = "prijava", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Slika> slike;

    @ManyToOne
    @JoinColumn(name = "parent_prijava_id")
    private Prijava parentPrijava;

    @NonNull
    private ZonedDateTime prvoVrijemePrijave;

    private ZonedDateTime vrijemeOtklona;

    public Prijava(Lokacija lokacija, String naziv, TipOstecenja tipOstecenja, String opis, @NonNull Korisnik kreator, List<Slika> slike, Prijava parentPrijava, @NonNull ZonedDateTime prvoVrijemePrijave, ZonedDateTime vrijemeOtklona) {
        this.lokacija = lokacija;
        this.naziv = naziv;
        this.tipOstecenja = tipOstecenja;
        this.opis = opis;
        this.kreator = kreator;
        this.slike = slike;
        this.parentPrijava = parentPrijava;
        this.prvoVrijemePrijave = prvoVrijemePrijave;
        this.vrijemeOtklona = vrijemeOtklona;
    }
}
