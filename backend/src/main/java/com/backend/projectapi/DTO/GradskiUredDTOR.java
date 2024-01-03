package com.backend.projectapi.DTO;

import com.backend.projectapi.model.Korisnik;

import java.util.LinkedList;
import java.util.List;

public class GradskiUredDTOR {

    private Long ured_id;
    private Long tipOstecenjeID;

    private String nazivUreda;

    private List<KorisnikDTO> clanovi;


    public GradskiUredDTOR(Long ured_id,Long tipOstecenjeID, String nazivUreda,List<Korisnik> clanovi) {
        this.ured_id=ured_id;
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
                            korisnik.getEmail()
                    )
            );
        }

        return response;
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

    public void setNazivUreda(String nazivUreda) {
        this.nazivUreda = nazivUreda;
    }
}
