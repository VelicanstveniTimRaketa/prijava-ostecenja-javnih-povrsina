package com.backend.projectapi.service.impl;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.repository.GradskiUrediRepository;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.TipoviOstecenjaRepository;
import com.backend.projectapi.service.GradskiUrediService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}

