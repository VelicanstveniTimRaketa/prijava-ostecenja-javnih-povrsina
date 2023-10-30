package com.backend.projectapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
@Table
public class TipOstecenja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private UUID Id;
    private String naziv;
    private long IdVijeca; //neznan koliko je ovo bitno ali tako je na skici
    private String nazivVijeca;
    @OneToMany(mappedBy = "tipOstecenja",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Prijava> prijave;

    public TipOstecenja(String naziv, long idVijeca, String nazivVijeca) {
        this.naziv = naziv;
        IdVijeca = idVijeca;
        this.nazivVijeca = nazivVijeca;
    }
}
