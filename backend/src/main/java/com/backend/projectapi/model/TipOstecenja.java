package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tipovi_ostecenja")
@RequiredArgsConstructor
public class TipOstecenja {
    @Id
    @GeneratedValue
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @NonNull
    private String naziv;

    @JsonIgnore
    @OneToMany(mappedBy = "tipOstecenja", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Prijava> prijave;

}
