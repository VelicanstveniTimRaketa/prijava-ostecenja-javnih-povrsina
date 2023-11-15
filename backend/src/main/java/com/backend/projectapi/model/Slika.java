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
@Table(name = "slike")
public class Slika {
    @Id
    @GeneratedValue
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @NonNull
    private String podatak; // putanja do same slike koja se nalazi u backend folderu

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "prijavaId")
    private Prijava prijava;
}
