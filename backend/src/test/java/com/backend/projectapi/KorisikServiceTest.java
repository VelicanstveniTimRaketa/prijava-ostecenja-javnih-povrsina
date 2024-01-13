package com.backend.projectapi;
import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.service.impl.KorisnikServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KorisikServiceTest {

    @Mock
    private KorisniciRepository korisniciRepo;

    @InjectMocks
    private KorisnikServiceImpl korisnikService;

    @Test
    @DisplayName("metoda getKorisnik koja treba vratiti iznimku o ne postojećem korisniku")
    public void GetKorisnikFalse(){
        Long userId = 1L;
        when(korisniciRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> korisnikService.getAllUsers(userId));

    }

    @Test
    @DisplayName("metoda getKorisnik koja vraća sve korisnike")
    public void getKorisnikAll() {
        List<Korisnik> userList = new ArrayList<>();
        Korisnik korisnik1 = new Korisnik();
        korisnik1.setId(1L);
        korisnik1.setActive("true");
        korisnik1.setUsername("username");
        korisnik1.setIme("name");
        korisnik1.setPrezime("lastname");
        korisnik1.setPassword("pass");
        korisnik1.setEmail("email@email.com");

        Korisnik korisnik2 = new Korisnik();
        korisnik2.setId(2L);
        korisnik2.setActive("true");
        korisnik2.setUsername("username2");
        korisnik2.setIme("name2");
        korisnik2.setPrezime("lastname2");
        korisnik2.setPassword("pass2");
        korisnik2.setEmail("email2@email.com");

        userList.add(korisnik1);
        userList.add(korisnik2);

        when(korisniciRepo.findAll()).thenReturn(userList);


        List<Korisnik> result = korisnikService.getAllUsers(null);


        assertEquals(userList, result);
    }

    @Test
    @DisplayName("metoda getKorisnik koja treba vratiti samo jednog korisnika")
    void getKorisnikSingle() {

        Long userId = 1L;
        Korisnik korisnik1 = new Korisnik();
        korisnik1.setId(1L);
        korisnik1.setActive("true");
        korisnik1.setUsername("username");
        korisnik1.setIme("name");
        korisnik1.setPrezime("lastname");
        korisnik1.setPassword("pass");
        korisnik1.setEmail("email@email.com");
        when(korisniciRepo.findById(userId)).thenReturn(Optional.of(korisnik1));

        List<Korisnik> result = korisnikService.getAllUsers(userId);

        assertEquals(1, result.size());
        assertEquals(korisnik1, result.get(0));
    }

}
