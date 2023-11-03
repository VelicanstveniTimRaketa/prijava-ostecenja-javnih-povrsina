package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

import java.sql.Timestamp;
import java.time.LocalDate;
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


    @ManyToOne(optional = false)
    @JoinColumn(name = "lokacijaId")
    private Lokacija lokacija;


    @ManyToOne(optional = false)
    @JoinColumn(name = "ostecenjeId")
    private TipOstecenja tipOstecenja;

    private String opis;

    @NonNull
    @ManyToOne(optional = false) //jedna prijava ima samo jednog korisnika a jedan korisnik moze imati vise prijava
    @JoinColumn(name = "kreatorId")
    private Korisnik kreator;

    @OneToMany(mappedBy = "prijava", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Slika> slike;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_prijava_id")
    private Prijava parentPrijava;

    @NonNull
    private Timestamp prvoVrijemePrijave;

    private Timestamp vrijemeOtklona;

}
