package com.backend.projectapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@IdClass(LogoviPrijaveKey.class)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class LogoviPrijave {
    //treba skuziti kako postaviti kompleksni kljuc
    @Id
    private Long korisnikId;

    @Id
    private Long prijavaId;

    @JsonIgnore
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "korisnikId")
    private Korisnik korisnik;

    @JsonIgnore
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prijavaId")
    private Prijava prijava;

    @NonNull
    private LocalDate vrijemePrijave;

}
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
class LogoviPrijaveKey implements Serializable{
    private Long korisnikId;
    private Long prijavaId;
}
