package com.backend.projectapi;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.DTO.GradskiUredDTOR;
import com.backend.projectapi.config.ApplicationConfig;
import com.backend.projectapi.config.AuthenticationService;
import com.backend.projectapi.controller.ApplicationController;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.TipOstecenja;
import com.backend.projectapi.repository.GradskiUrediRepository;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.TipoviOstecenjaRepository;
import com.backend.projectapi.service.impl.GradskiUrediServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GradskiUredServiceTest {

    @Mock
    private  GradskiUrediRepository gradskiUredRepo;

    @Mock
    private  TipoviOstecenjaRepository ostecenjaRepo;

    @Mock
    private  KorisniciRepository korisniciRepo;

    @Mock
    private TipoviOstecenjaRepository tipoviOstecenjaRepository;

    @Mock
    private ApplicationController applicationController;

    @InjectMocks
    private GradskiUrediServiceImpl gradskiUrediServiceImpl;

    TipOstecenja tipOstecenja;
    GradskiUred gradskiUred;
    Korisnik korisnik;

    @BeforeEach
    public void settingUp(){
        tipOstecenja = new TipOstecenja();
        tipOstecenja.setId(1L);
        tipOstecenja.setNaziv("puknuće ceste");
        tipOstecenja.setGradskiUredi(null);

        gradskiUred = new GradskiUred();
        gradskiUred.setId(1L);
        gradskiUred.setNaziv("Ured za popravak cesta");
        gradskiUred.setActive("false");
        gradskiUred.setKorisnikList(new ArrayList<>());
        gradskiUred.setPrijave(null);
        gradskiUred.setTipOstecenja(tipOstecenja);

        korisnik=new Korisnik();
        korisnik.setUred_status("false");
    }

    @Test
    public void getGradskiUred(){
        Mockito.when(gradskiUredRepo.findById(anyLong())).thenReturn(Optional.of(gradskiUred));

        GradskiUredDTOR gradskiUredDTOR = (GradskiUredDTOR) gradskiUrediServiceImpl.getUred(1L);

        assertEquals("Ured za popravak cesta",gradskiUredDTOR.getNazivUreda());
        assertEquals("false",gradskiUredDTOR.getActive());
        assertEquals(1L,gradskiUredDTOR.getTipOstecenjeID());

    }


    @Test
    public void potvrdiGradskiUred(){

        List<Korisnik> list = new ArrayList<>();
        list.add(korisnik);

        Mockito.when(gradskiUredRepo.findById(anyLong())).thenReturn(Optional.of(gradskiUred));
        Mockito.when(korisniciRepo.findByPendingZahtjevOdredeniUred(anyLong())).thenReturn(list);
        gradskiUred.setActive("active");
        Mockito.when(gradskiUredRepo.save(any(GradskiUred.class))).thenReturn(gradskiUred);

        GradskiUredDTOR gradskiUredDTOR = (GradskiUredDTOR) gradskiUrediServiceImpl.potvrdiUred(1L);

        assertEquals("Ured za popravak cesta",gradskiUredDTOR.getNazivUreda());
        assertEquals("true",gradskiUredDTOR.getActive());
        assertEquals(1L,gradskiUredDTOR.getTipOstecenjeID());
    }
}
