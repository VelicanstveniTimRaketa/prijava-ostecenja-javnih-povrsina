package com.backend.projectapi.DTO;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.Role;
import java.util.List;

public class KorisnikDTO {

    private Long Id;
    private String username;
    private String ime;
    private String prezime;

    public KorisnikDTO(Long Id, String username, String ime, String prezime, Role role, String active, String ured_status, String email) {
        this.Id = Id;
        this.username = username;
        this.ime = ime;
        this.prezime = prezime;
        this.role = role;
        this.active = active;
        this.ured_status = ured_status;
        this.email = email;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUred_status() {
        return ured_status;
    }

    public void setUred_status(String ured_status) {
        this.ured_status = ured_status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Prijava> getPrijave() {
        return prijave;
    }

    public void setPrijave(List<Prijava> prijave) {
        this.prijave = prijave;
    }

    private Role role;
    private String active;
    private String ured_status;
    private String email;
    private List<Prijava> prijave;

}
