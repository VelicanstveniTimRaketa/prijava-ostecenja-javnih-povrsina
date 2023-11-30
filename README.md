# Prijava oštećenja javnih površina
Aplikacija za prijavu oštećenja javnih površina sa sučeljem za krajnje korisnike, gradske urede i administratore.  
**Ova aplikacija je razvijena u sklopu kolegija "Programsko inženjerstvo" na
Fakultetu elektrotehnike i računarstva, grupa TG08.2, akademska godina 2023/2024.**

Verzija aplikacije koja je pod aktivnim razvijanjem se nalazi na branchu `develop`.  
Verzija aplikacije koja je spremna za produkciju se nalazi na branchu `master` i njen demo
je postavljen na https://prijavi-ostecenja.onrender.com/.

## Tehnologije
Frontend je pisan u TypeScript React.js-u, a backend u Java Spring Boot-u.

### Pokretanje frontend sučelja
Potrebno je instalirati Node.js i npm. Potrebno se pozicionirati u `frontend` direktorij i
u terminalu pokrenuti sljedeću naredbu:
```sh
$ npm i -D
```
Tako će se instalirati sve ovisnosti (dependencies) potrebne za pokretanje aplikacije.  

#### Development build
Za pokretanje development poslužitelja potrebno je izvršiti:
```sh
$ npm run dev
```
#### Production build
Za postavljanje aplikacije za produkciju, potrebno je izvršiti:
```sh
$ npm run build && npm run serve
```

Moguće je konfigurirati postavke frontenda postavljajući sljedeće vrijednosti u `frontend/.env` datoteku:  
```ini
VITE_MAPS_API_KEY=google_api_kljuc # Ključ za Google Maps API

# SAMO ZA PRODUKCIJU
HOST=0.0.0.0                       # Host IP Express.js poslužitelja koji servira frontend
PORT=3000                          # Port Express.js poslužitelja koji servira frontend
API_BASE_URL=https://example.com   # Bazna adresa backenda. Express.js poslužitelj proxy-ja
                                   # sve `/api/*` zahtjeve na backend poslužitelj
```

### Pokretanje backend servisa
Potrebno je instalirati JDK 17 i Apache Maven. Potrebno se pozicionirati u `backend` direktorij i
u terminalu pokrenuti sljedeću naredbu:
```sh
$ mvn clean spring-boot:run
```
Moguće je konfigurirati postavke backenda postavljajući sljedeće vrijednosti u `backend/.env` datoteku:  
```ini
PORT=8080                                    # Port na kojem je izložen API
DB_URL=jdbc:postgresql://localhost:5432/pojp # URL baze podataka
DB_USERNAME=postgres                         # Korisnik baze podataka
DB_PASS=postgres                             # Lozinka navedenog korisnika baze podataka
DB_HIBERNATE=create-drop                     # Kontrola perzistencije podataka pri ponovnom
                                             # pokretanju backenda ("create-drop" za resetiranje, 
                                             # "update" za perzistentne podatke)
```
Navedene vrijednosti poviše su zadane vrijednosti. Sve backend rute su izložene na `/api/*`.


## Članovi tima i zadaće
| **Član tima**                                                   | **Zadaća**           |
|-----------------------------------------------------------------|----------------------|
| Davor Najev ([@spinzed](https://www.github.com/spinzed))        | koordinator/frontend |
| Nikola Botić ([@NBotic02](https://www.github.com/NBotic02))     | frontend             |
| Dominik Zoričić ([@dz54240](https://www.github.com/dz54240))    | backend              |
| Josip Šare ([@josipsare](https://www.github.com/josipsare))     | backend              |
| Ivan Elez ([@eca041](https://www.github.com/eca041))            | backend              |
| Nino Ćurko ([@ninotronics](https://www.github.com/ninotronics)) | dokumentacija        |

## Kontribucije
Pogledaj [CONTRIBUTING.md](https://github.com/VelicanstveniTimRaketa/prijava-ostecenja-javnih-povrsina/blob/master/CONTRIBUTING.md).

## Dokumentacija
Dokumentacija se nalazi u direktoriju `docs`. Prva verzija dokumentacije se nalazi u datoteci [PROGI_ProjektnaDokumentacija.pdf](https://github.com/VelicanstveniTimRaketa/prijava-ostecenja-javnih-povrsina/blob/master/docs/PROGI_ProjektnaDokumentacija.pdf) koja je napisana u označnom jeziku LaTeX i generirana pomoću alata MiKTeX.

## Licenca
Ovaj projekt je objavljen pod [MIT licencom](https://github.com/VelicanstveniTimRaketa/prijava-ostecenja-javnih-povrsina/blob/master/LICENSE).
