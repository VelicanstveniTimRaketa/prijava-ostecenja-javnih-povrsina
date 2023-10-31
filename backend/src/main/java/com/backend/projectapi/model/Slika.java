package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table
public class Slika {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;


    @NonNull
    private Byte[] podatak; //slika

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "prijavaId")
    private Prijava prijava;
}
