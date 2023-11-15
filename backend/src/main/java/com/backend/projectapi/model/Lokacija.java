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
@Table(name = "lokacije")
public class Lokacija {
    @Id
    @GeneratedValue
    @Column(name="lokacija_id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @NonNull
    @Column(name = "latitude",nullable = false)
    private Double latitude;

    @NonNull
    @Column(name = "longitude",nullable = false)
    private Double longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "lokacija", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Prijava> prijave;


}
