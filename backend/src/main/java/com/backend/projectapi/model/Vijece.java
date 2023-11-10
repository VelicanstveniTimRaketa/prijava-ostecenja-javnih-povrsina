package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "vijeca")
public class Vijece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @NonNull
    @NotBlank
    @Column(name = "naziv",nullable = false)
    private String naziv;

    @JsonIgnore
    @OneToOne(mappedBy = "vijece")
    private TipOstecenja tipOstecenja;

    @JsonIgnore
    @OneToMany(mappedBy = "vijece",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Korisnik> korisnikList;
}
