package com.backend.projectapi;

import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.model.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.backend.projectapi.repository.*;
import com.backend.projectapi.service.impl.PrijavaServiceImpl;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PrijavaServiceTest {
    @Mock
    private PrijaveRepository prijaveRepo;

    @Mock
    private LokacijeRepository lokacijaRepo;

    @Mock
    private GradskiUrediRepository gradskiUrediRepo;

    @Mock
    private KorisniciRepository korisnikRepo;

    @Mock
    private SlikeRepository slikaRepo;

    @InjectMocks
    private PrijavaServiceImpl prijavaService;


    @Test
    public void testGetAllPrijave(){
        TipOstecenja ostecenje = new TipOstecenja();
        ostecenje.setId(1L);
        ostecenje.setNaziv("ostecenje1");

        GradskiUred ured = new GradskiUred();
        ured.setId(1L);
        ured.setTipOstecenja(ostecenje);

        Prijava prijava = new Prijava();
        prijava.setId(1L);
        prijava.setNaziv("prijava1");
        prijava.setGradskiUred(ured);

        Prijava prijava2 = new Prijava();
        prijava2.setId(1L);
        prijava2.setNaziv("prijava2");

        when(prijaveRepo.findAll()).thenReturn(Arrays.asList(prijava, prijava2));
        when(prijaveRepo.findAllByTipOstecenja(ostecenje.getId())).thenReturn(Arrays.asList(prijava));

        List<Prijava> res = prijavaService.getAllPrijave(null, null, null, null, null, null, null, null, ostecenje.getId());

        assertEquals("prijava1", res.get(0).getNaziv());
    }

    @Test
    public void testAddPrijave(){
        PrijavaDTO prijavaDTO = new PrijavaDTO("Test Prijava", "Opis", 1L, 45.0, 15.0, new MultipartFile[]{});
        GradskiUred gradskiUred = new GradskiUred("Gradski ured", null, null, "true");
        Korisnik korisnik = new Korisnik("username", "name","password" ,"surname", "email@gmail.com");
        Lokacija lokacija = new Lokacija(45.0, 15.0);
        Prijava prijava = new Prijava(lokacija, "test prijava", gradskiUred, "Opis", korisnik, null, null, ZonedDateTime.now(), null);
        prijava.setId(1L);

        when(gradskiUrediRepo.findById(1L)).thenReturn(Optional.of(gradskiUred));
        when(korisnikRepo.findById(1L)).thenReturn(Optional.of(korisnik));
        when(lokacijaRepo.save(any(Lokacija.class))).thenReturn(lokacija);
        when(prijaveRepo.save(any(Prijava.class))).thenReturn(prijava);

        Object result = prijavaService.addPrijave(prijavaDTO, null, 1L);

        assertNotNull(result);
        assertTrue(result instanceof Map);
        Map<String, Object> resultMap = (Map<String, Object>) result;
        assertTrue(resultMap.containsKey("newReport"));
        assertTrue(resultMap.containsKey("nearbyReports"));

        Prijava savedPrijava = (Prijava) resultMap.get("newReport");
        assertNotNull(savedPrijava);
        assertEquals("test prijava", savedPrijava.getNaziv());
        assertEquals("Opis", savedPrijava.getOpis());

        verify(lokacijaRepo).save(any(Lokacija.class));
        verify(gradskiUrediRepo).findById(1L);
        verify(korisnikRepo).findById(1L);
        // 2 poziva zbog prisutnih slika
        verify(prijaveRepo, times(2)).save(any(Prijava.class));
    }
}
