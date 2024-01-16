package com.backend.projectapi.DTO;

import com.backend.projectapi.model.Korisnik;

import java.util.LinkedList;
import java.util.List;

public class GradskiUredDTOR {

    private Long ured_id;
    private Long tipOstecenjeID;

    private String active;

    private String nazivUreda;

    private List<KorisnikDTO> clanovi;


    public GradskiUredDTOR(Long ured_id,String active,Long tipOstecenjeID, String nazivUreda,List<Korisnik> clanovi) {
        this.ured_id=ured_id;
        this.active=active;
        this.tipOstecenjeID=tipOstecenjeID;
        this.nazivUreda=nazivUreda;
        this.clanovi = setClanovi(clanovi);
    }

    public List<KorisnikDTO> setClanovi(List<Korisnik> clanovi){
        List<KorisnikDTO> response = new LinkedList<>();

        for(Korisnik korisnik: clanovi){
            response.add(
                    new KorisnikDTO(
                            korisnik.getId(),
                            korisnik.getUsername(),
                            korisnik.getIme(),
                            korisnik.getPrezime(),
                            korisnik.getRole(),
                            korisnik.getActive(),
                            korisnik.getUred_status(),
                            korisnik.getUred(), korisnik.getEmail()
                    )
            );
        }

        return response;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getTipOstecenjeID() {
        return tipOstecenjeID;
    }

    public void setTipOstecenjeID(Long tipOstecenjeID) {
        this.tipOstecenjeID = tipOstecenjeID;
    }

    public String getNazivUreda() {
        return nazivUreda;
    }

    public Long getUred_id() {
        return ured_id;
    }

    public void setUred_id(Long ured_id) {
        this.ured_id = ured_id;
    }

    public List<KorisnikDTO> getClanovi() {
        return clanovi;
    }


    public void setNazivUreda(String nazivUreda) {
        this.nazivUreda = nazivUreda;
    }
}
