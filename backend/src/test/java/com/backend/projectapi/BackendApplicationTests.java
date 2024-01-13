package com.backend.projectapi;

import com.backend.projectapi.config.*;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Role;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.RefreshTokenRepository;
import com.backend.projectapi.service.RefreshTokenService;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BackendApplicationTests {

	@Mock
	private KorisniciRepository korisnikRepo;
	@Mock
	private  JwtService jwtService;
	@Mock
	private ApplicationConfig applicationConfig;
	@Mock
	private  AuthenticationManager authenticationManager;
	@Mock
	private PasswordEncoder encoder;
	@Mock
	private  RefreshTokenService refreshTokenService;
	@Mock
	private  RefreshTokenRepository refreshTokenRepository;
	@InjectMocks
	private AuthenticationService authenticationService;

	@Mock
	private Korisnik korisnik;



	@Test
	@DisplayName("Test for registering new valid users")
	void RegisterTest() {
//		RegisterRequest request = new RegisterRequest("username",
//				"name",
//				"lastname",
//				"password",
//				"email@email.com");
//		when(korisnikRepo.findByEmail(anyString())).thenReturn(Optional.empty());
//		when(korisnikRepo.findByUsername(anyString())).thenReturn(Optional.empty());
//		when(encoder.encode(anyString())).thenReturn("encodedPassword");
//		when(korisnikRepo.save(any(Korisnik.class))).thenReturn(new Korisnik(1L,"username","name","lastname","encodedPassword", Role.USER,"active",null,null,"email@email.com",null));
//		//when(korisnikRepo.save(any(Korisnik.class))).thenAnswer(i -> i.getArgument(0));
//
//
//		AuthenticationResponse response = authenticationService.register(request);
////
////		assertNotNull(response);
////		assertEquals("username",response.getKorisnik().getUsername());
////		assertEquals("name",response.getKorisnik().getIme());
////		assertEquals("lastname",response.getKorisnik().getPrezime());
////		assertEquals("email@email.com",response.getKorisnik().getEmail());

	}



}
