package com.backend.projectapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
@Table
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private UUID Id;
    private String username;
    private String email;
    @OneToMany(mappedBy = "kreatorId",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prijava> prijave;
    @OneToOne(mappedBy = "korisnik")
    private LogoviPrijave log;

    public Korisnik(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
