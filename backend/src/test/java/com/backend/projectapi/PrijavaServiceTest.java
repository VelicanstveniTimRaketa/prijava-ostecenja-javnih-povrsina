package com.backend.projectapi;
import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.model.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.backend.projectapi.repository.*;
import com.backend.projectapi.service.impl.PrijavaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
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
    @DisplayName("Dohvacanje svih prijava koje se filtriraju ovisno o poslanim parametrima")
    public void testGetAllPrijave(){
        List<Prijava> prijave = Arrays.asList(createTestPrijava(1L, "Prijava1", 1L, ZonedDateTime.now(), null, 45.0, 15.0),
                                              createTestPrijava(2L, "Prijava2", 2L, ZonedDateTime.now().minusDays(5), ZonedDateTime.now(), 47.0, 17.0),
                                              createTestPrijava(3L, "Prijava3", 3L, ZonedDateTime.now().minusDays(6), null, 50.0, 20.0));

        when(prijaveRepo.findAll()).thenReturn(prijave);
        when(prijaveRepo.findAllByKreatorId(1L)).thenReturn(Arrays.asList(prijave.get(0)));
        when(prijaveRepo.getAllByVrijemeOtklonaIsNull()).thenReturn(Arrays.asList(prijave.get(0), prijave.get(2)));
        when(prijaveRepo.findAllByPrvoVrijemePrijaveBetween(any(ZonedDateTime.class), any(ZonedDateTime.class))).thenReturn(prijave);

        List<Prijava> res = prijavaService.getAllPrijave(1L, "true", null, ZonedDateTime.now().minusDays(10), ZonedDateTime.now().plusDays(1), null, null, null, null);

        assertNotNull(res);
        assertEquals(1, res.size());
        assertEquals("Prijava1", res.get(0).getNaziv());
    }

    private Prijava createTestPrijava(Long id, String naziv, Long kreatorId, ZonedDateTime prvoVrijemePrijave, ZonedDateTime vrijemeOtklona, Double lat, Double longitude){
        Prijava prijava = new Prijava();
        prijava.setId(id);
        prijava.setNaziv(naziv);
        prijava.setPrvoVrijemePrijave(prvoVrijemePrijave);
        prijava.setVrijemeOtklona(vrijemeOtklona);
        Korisnik kreator = new Korisnik("username", "ime", "prezime", "pass", "email");
        kreator.setId(kreatorId);
        prijava.setKreator(kreator);
        Lokacija lokacija = new Lokacija(lat, longitude);
        prijava.setLokacija(lokacija);

        return prijava;
    }

    @Test
    @DisplayName("Stvaranje nove Prijave i pohrana u bazu")
    public void testAddPrijave() {
        PrijavaDTO prijavaDTO = new PrijavaDTO("Test Prijava", "Opis", 1L, 45.0, 15.0, new MultipartFile[]{});
        GradskiUred gradskiUred = new GradskiUred("Gradski ured", null, null, "true");
        Korisnik korisnik = new Korisnik("username", "name", "password", "surname", "email@gmail.com");
        Lokacija lokacija = new Lokacija(45.0, 15.0);
        Prijava prijava = new Prijava(lokacija, "test prijava", gradskiUred, "Opis", korisnik, null, null, ZonedDateTime.now(), null);
        prijava.setId(1L);

        when(gradskiUrediRepo.findById(1L)).thenReturn(Optional.of(gradskiUred));
        when(lokacijaRepo.save(any(Lokacija.class))).thenReturn(lokacija);
        when(prijaveRepo.save(any(Prijava.class))).thenReturn(prijava);

        Object result = prijavaService.addPrijave(prijavaDTO, null, korisnik);

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
        verify(prijaveRepo, times(2)).save(any(Prijava.class));
    }
}