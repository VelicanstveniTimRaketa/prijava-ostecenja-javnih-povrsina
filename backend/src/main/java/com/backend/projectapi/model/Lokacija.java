package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.NotFound;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Lokacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @NonNull
    @NotBlank
    @Column(name = "latitude",nullable = false)
    private Double latitude;

    @NonNull
    @NotBlank
    @Column(name = "longitude",nullable = false)
    private Double longitute;

    @JsonIgnore
    @OneToMany(mappedBy = "lokacijaId", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Prijava> prijave;
}
