package com.backend.projectapi;


import com.backend.projectapi.DTO.GradskiUredDTOR;
import com.backend.projectapi.controller.ApplicationController;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.TipOstecenja;
import com.backend.projectapi.repository.GradskiUrediRepository;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.TipoviOstecenjaRepository;
import com.backend.projectapi.service.impl.GradskiUrediServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GradskiUredServiceTest {

    @Mock
    private GradskiUrediRepository gradskiUredRepo;

    @Mock
    private TipoviOstecenjaRepository ostecenjaRepo;

    @Mock
    private KorisniciRepository korisniciRepo;

    @Mock
    private TipoviOstecenjaRepository tipoviOstecenjaRepository;

    @Mock
    private ApplicationController applicationController;

    @InjectMocks
    private GradskiUrediServiceImpl gradskiUrediServiceImpl;

    TipOstecenja tipOstecenja;
    GradskiUred gradskiUred;
    Korisnik korisnik;
    GradskiUred gradskiUredSaved;

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
        gradskiUred.setKorisnikList(Collections.emptyList());
        gradskiUred.setPrijave(null);
        gradskiUred.setTipOstecenja(tipOstecenja);

        gradskiUredSaved = new GradskiUred();
        gradskiUredSaved.setId(1L);
        gradskiUredSaved.setNaziv("Ured za popravak cesta");
        gradskiUredSaved.setActive("true");
        gradskiUredSaved.setKorisnikList(Collections.emptyList());
        gradskiUredSaved.setPrijave(null);
        gradskiUredSaved.setTipOstecenja(tipOstecenja);

        korisnik=new Korisnik();
        korisnik.setUred_status("true");
    }

    @Test
    @DisplayName("metoda getUred koja treba vratiti samo jedan gradski ured")
    public void getGradskiUred(){
        Mockito.when(gradskiUredRepo.findById(anyLong())).thenReturn(Optional.of(gradskiUred));

        GradskiUredDTOR gradskiUredDTOR = (GradskiUredDTOR) gradskiUrediServiceImpl.getUred(1L);

        assertEquals("Ured za popravak cesta",gradskiUredDTOR.getNazivUreda());
        assertEquals("false",gradskiUredDTOR.getActive());
        assertEquals(1L,gradskiUredDTOR.getTipOstecenjeID());

    }


    @Test
    @DisplayName("metoda koja potvrdđuje stvaranje gradskog ureda")
    public void potvrdiGradskiUred(){

        when(gradskiUredRepo.findById(anyLong())).thenReturn(Optional.of(gradskiUred));
        when(gradskiUredRepo.save(any(GradskiUred.class))).thenReturn(gradskiUredSaved);
        when(korisniciRepo.findByPendingZahtjevOdredeniUred(anyLong())).thenReturn(List.of(korisnik));

        // Act
        GradskiUredDTOR result = (GradskiUredDTOR) gradskiUrediServiceImpl.potvrdiUred(1L);

        assertEquals("true",result.getActive());
        assertEquals("active", korisnik.getUred_status());


        verify(gradskiUredRepo, times(1)).findById(1L);
        verify(gradskiUredRepo, times(1)).save(gradskiUred);
        verify(korisniciRepo, times(1)).findByPendingZahtjevOdredeniUred(1L);
        verify(korisniciRepo, times(1)).save(korisnik);
    }
}
