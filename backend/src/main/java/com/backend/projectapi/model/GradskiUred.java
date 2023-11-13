package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @JsonIgnore
    @OneToOne(mappedBy = "ured")
    private TipOstecenja tipOstecenja;

    @JsonIgnore
    @OneToMany(mappedBy = "ured",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Korisnik> korisnikList;
}