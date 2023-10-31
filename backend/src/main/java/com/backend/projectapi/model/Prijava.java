package com.backend.projectapi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prijave")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Prijava {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @NonNull
    @Column(name = "latitude", nullable = false)
    private Long latitude;

    @NonNull
    @Column(name = "longitude", nullable = false)
    private Long longitude;

    @ManyToOne
    @JoinColumn(name = "ostecenjeId")
    private TipOstecenja tipOstecenja;

    @ManyToOne //jedna prijava ima samo jednog korisnika a jedan korisnik moze imati vise prijava
    @JoinColumn(name = "kreatorId")
    private Korisnik kreatorId;

    @OneToMany(mappedBy = "prijava", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Slika> slike;

    @OneToOne(mappedBy = "prijava")
    private LogoviPrijave log;
    private LocalDate prvoVrijemePrijave;
    private LocalDate vrijemeOtklona;
    public Prijava(long latitude, long longitude, TipOstecenja tipOstecenja, Korisnik kreatorId, LocalDate prvoVrijemePrijave, LocalDate vrijemeOtklona) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.tipOstecenja = tipOstecenja;
        this.kreatorId = kreatorId;
        this.prvoVrijemePrijave = prvoVrijemePrijave;
        this.vrijemeOtklona = vrijemeOtklona;
    }
}
