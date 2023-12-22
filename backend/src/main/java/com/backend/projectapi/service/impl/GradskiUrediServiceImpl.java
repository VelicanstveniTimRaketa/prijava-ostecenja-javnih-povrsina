package com.backend.projectapi.service.impl;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.repository.GradskiUrediRepository;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.TipoviOstecenjaRepository;
import com.backend.projectapi.service.GradskiUrediService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GradskiUrediServiceImpl implements GradskiUrediService {
    private final GradskiUrediRepository gradskiUredRepo;
    private final TipoviOstecenjaRepository ostecenjaRepo;
    private final KorisniciRepository korisniciRepo;


    public GradskiUrediServiceImpl(GradskiUrediRepository vijeceRepo, TipoviOstecenjaRepository ostecenjaRepo, KorisniciRepository korisniciRepo){
        this.gradskiUredRepo = vijeceRepo;
        this.ostecenjaRepo = ostecenjaRepo;
        this.korisniciRepo = korisniciRepo;
    }

    @Override
    public List<GradskiUred> getAll(String active) {
        if (active.equals("false")){
            return gradskiUredRepo.findNeaktiveUrede();
        }else if(active.equals("true")){
            return gradskiUredRepo.findAktivneUrede();
        }
        return null;
    }

    @Override
    public Object addGradskiUred( GradskiUredDTO gradskiUredDTO){

        System.out.println(gradskiUredDTO.getNazivUreda());
        System.out.println(gradskiUredDTO.getTipOstecenjeID());
        System.out.println(gradskiUredDTO.getOsnivac());

        ArrayList<Korisnik> listaKorisnika= new ArrayList<>();
        listaKorisnika.add(korisniciRepo.findById(gradskiUredDTO.getOsnivac()).get());

        GradskiUred gradskiUred = new GradskiUred(
                gradskiUredDTO.getNazivUreda(),
                ostecenjaRepo.findById(gradskiUredDTO.getTipOstecenjeID()).get(),
                listaKorisnika,
                "false"
        );

        GradskiUred gradskiUredSaved = gradskiUredRepo.save(gradskiUred);

        return gradskiUredSaved;
    }


    //samo ADMIN moze ovo
    @Override
    public Object makeActive(Long id) {

        GradskiUred gradskiUred=null;
        Optional<GradskiUred> gradskiUredOpt= gradskiUredRepo.findById(id);
        if (gradskiUredOpt.isPresent()){
            gradskiUred=gradskiUredOpt.get();
        }
        gradskiUred.setActive("true");

        return gradskiUredRepo.save(gradskiUred);
    }


    /**
     * u modelu Korisnik se postavlja Ured na zeljeni ured i ured_status se postavlja na pending sto znaci da se zahtjev
     * jos nije odobrio, tj da korisnik NIJE jos clan ureda
     * @param korisnikId
     * @param uredId
     * @return true
     */
    @Override
    public Object zahtjevZaUlazak(Long korisnikId, Long uredId) {

        Optional<Korisnik> korisnikOptional=korisniciRepo.findById(korisnikId);
        Korisnik korisnik = null;

        Optional<GradskiUred> gradskiUredOptional=gradskiUredRepo.findById(uredId);
        GradskiUred gradskiUred = null;

        if (korisnikOptional.isPresent()){
            korisnik=korisnikOptional.get();
        }else {
            throw new RecordNotFoundException("ne postoji korisnik za dani id: "+korisnikId);
        }

        if(gradskiUredOptional.isPresent()){
            gradskiUred=gradskiUredOptional.get();
        }else{
            throw new RecordNotFoundException("ne postoji gradski ured za dani id: "+uredId);
        }

        if (gradskiUred.getActive().equals("false")){
            throw new RecordNotFoundException("ured u koji korisnik zeli uci nije aktivan");
        }

        if (korisnik.getUred()!=null){
            if (Objects.equals(korisnik.getUred().getId(), uredId) && korisnik.getUred_status().equals("pending")){

                throw new RecordNotFoundException("korisnik je vec predao prijavu za ulazak u ovaj ured");

            }else if (Objects.equals(korisnik.getUred().getId(), uredId) && korisnik.getUred_status().equals("active")){

                throw new RecordNotFoundException("korisnik je vec clan ovog ureda");

            }else if (!Objects.equals(korisnik.getUred().getId(), uredId)){
                throw new RecordNotFoundException("korisnik je vec predao prijavu ili vec je clan drugog ureda");
            }
        }

        korisnik.setUred(gradskiUred);
        korisnik.setUred_status("pending");

        return korisniciRepo.save(korisnik);
    }

    @Override
    public List<Korisnik> sviZahtjevi() {
        return korisniciRepo.findByPendingZahthev();
    }

    @Override
    public Object potvrdaZahtjeva(Long korisnikId) {

        Optional<Korisnik> korisnikOptional = korisniciRepo.findById(korisnikId);
        Korisnik korisnik;

        if (korisnikOptional.isPresent()){
            korisnik=korisnikOptional.get();
            if (korisnik.getUred()==null){
                throw new RecordNotFoundException("korisnik nema predan zahtjev za ulazak u ured");
            }else if (korisnik.getUred_status().equals("active")){
                throw new RecordNotFoundException("korisnik je vec clan tog ureda");
            }
        }else{
            throw new RecordNotFoundException("ne postoji korisnik za dani id: "+korisnikId);
        }

        GradskiUred gradskiUred = gradskiUredRepo.findById(korisnik.getUred().getId()).get();

        korisnik.setUred_status("active");

        List<Korisnik> listaClanova = gradskiUred.getKorisnikList();
        listaClanova.add(korisnik);
        gradskiUred.setKorisnikList(listaClanova);

        korisniciRepo.save(korisnik);

        return gradskiUredRepo.save(gradskiUred);
    }

    @Override
    public Object odbijanjeZahjteva(Long korisnikId) {

        Optional<Korisnik> korisnikOptional = korisniciRepo.findById(korisnikId);
        Korisnik korisnik;

        if (korisnikOptional.isPresent()){
            korisnik=korisnikOptional.get();
            if (korisnik.getUred()==null){
                throw new RecordNotFoundException("korisnik nema predan zahtjev za ulazak u ured");
            }else if (korisnik.getUred_status().equals("active")){
                throw new RecordNotFoundException("korisnik je vec clan tog ureda");
            }
        }else{
            throw new RecordNotFoundException("ne postoji korisnik za dani id: "+korisnikId);
        }

        korisnik.setUred_status("NULL");
        korisnik.setUred(null);

        return korisniciRepo.save(korisnik);
    }

    @Override
    public Object odbijiUred(Long id) {

        Optional<GradskiUred> gradskiUredOptional = gradskiUredRepo.findById(id);
        GradskiUred gradskiUred;

        if(gradskiUredOptional.isPresent()){
            gradskiUred=gradskiUredOptional.get();
            if (gradskiUred.getActive().equals("true")){
                throw new RecordNotFoundException("ured za dani id: "+id+ " je vec aktivan");
            }
        }else{
            throw new RecordNotFoundException("ne postoji ured za dani id: "+id);
        }

        gradskiUredRepo.delete(gradskiUred);

        return true;
    }
}

