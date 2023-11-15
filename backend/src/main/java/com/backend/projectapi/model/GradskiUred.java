package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue
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
    @OneToMany(mappedBy = "ured",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Korisnik> korisnikList;
}
