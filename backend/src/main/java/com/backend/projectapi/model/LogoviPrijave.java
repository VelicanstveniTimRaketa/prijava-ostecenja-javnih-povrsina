package com.backend.projectapi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@IdClass(LogoviPrijaveKey.class)
public class LogoviPrijave {
    //treba skuziti kako postaviti kompleksni kljuc
    @Id
    private Long korisnikId;
    @Id
    private Long prijavaId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "korisnikId")
    private Korisnik korisnik;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prijavaId")
    private Prijava prijava;
    private LocalDate vrijemePrijave;

}
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
class LogoviPrijaveKey implements Serializable{
    private Long korisnikId;
    private Long prijavaId;
}
