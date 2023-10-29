package com.backend.projectapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
@Table
public class Slika {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private UUID Id;
    private byte[] podatak; //slika
    @ManyToOne
    @JoinColumn(name = "prijavaId")
    private Prijava prijava;

    public Slika(byte[] podatak, Prijava prijava) {
        this.podatak = podatak;
        this.prijava = prijava;
    }
}
