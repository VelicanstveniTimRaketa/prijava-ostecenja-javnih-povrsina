package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "gradski_uredi")
public class GradskiUred {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @NonNull
    @NotBlank
    @Column(name = "naziv",nullable = false)
    private String naziv;


    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "ostecenjeId")
    private TipOstecenja tipOstecenja;

    @JsonIgnore
    @OneToMany(mappedBy = "gradskiUred", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Prijava> prijave;

    @JsonIgnore
    @OneToMany(mappedBy = "ured",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Korisnik> korisnikList;

    @Column(columnDefinition = "VARCHAR(6) DEFAULT 'false'")
    private String active;

    public GradskiUred(@NonNull String naziv, TipOstecenja tipOstecenja, List<Korisnik> korisnikList,String active) {
        this.naziv = naziv;
        this.tipOstecenja = tipOstecenja;
        this.korisnikList = korisnikList;
        this.active=active;
    }
}
