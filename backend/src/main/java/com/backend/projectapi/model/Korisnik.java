package com.backend.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "korisnici")
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private Long Id;

    @NonNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NonNull
    private String ime;

    @NonNull
    private String prezime;

    @NonNull
    private String passwordHash;

    @NonNull
    private String token;

    @NonNull
    private String role;

    @Column(columnDefinition = "VARCHAR(6) DEFAULT 'true'")
    private String active;

    @ManyToOne
    @JoinColumn(name = "uredId")
    private GradskiUred ured;

    @NonNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "kreator",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prijava> prijave;

}
