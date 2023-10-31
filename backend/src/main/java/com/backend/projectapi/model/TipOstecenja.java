package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
@RequiredArgsConstructor
public class TipOstecenja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @NonNull
    private String naziv;

    @OneToOne
    @JoinColumn(name = "vijeceId", referencedColumnName = "id")
    private Vijece vijeceId;

    @JsonIgnore
    @OneToMany(mappedBy = "tipOstecenja", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Prijava> prijave;

}
