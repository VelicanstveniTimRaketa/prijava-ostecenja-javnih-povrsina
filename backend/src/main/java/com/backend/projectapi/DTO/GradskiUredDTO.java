package com.backend.projectapi.DTO;

public class GradskiUredDTO {

    private Long tipOstecenjeID;

    private String nazivUreda;

    private Long osnivac;

    public GradskiUredDTO(Long tipOstecenjeID, String nazivUreda,Long osnivac) {
        this.tipOstecenjeID = tipOstecenjeID;
        this.nazivUreda = nazivUreda;
        this.osnivac=osnivac;
    }

    public Long getOsnivac() {
        return osnivac;
    }

    public void setOsnivac(Long osnivac) {
        this.osnivac = osnivac;
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
