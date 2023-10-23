# Prijava oštećenja javnih površina
Aplikacija za prijavu oštećenja javnih površina sa admin sučeljem.  
**Ova aplikacija je razvijena u sklopu kolegija "Programsko inženjerstvo" na
Fakultetu elektrotehnike i računarstva, grupa TG08.2, akademska godina 2023/2024.**

## Tehnologije
Frontend je pisan u React.js-u, a backend u Java Spring Boot-u.

## Članovi tima i zadaće
| **Član tima**                                                     | **Zadaća**                |
|-------------------------------------------------------------------|---------------------------|
| Ivan Šimunić ([@ivansimunic](https://www.github.com/ivansimunic)) | koordinator/dokumentacija |
| Nino Ćurko ([@ninotronics](https://www.github.com/ninotronics))   | dokumentacija             |
| Davor Najev ([@spinzed](https://www.github.com/spinzed))          | frontend                  |
| Nikola Botić ([@NBotic02](https://www.github.com/NBotic02))       | frontend                  |
| Dominik Zoričić ([@dz54240](https://www.github.com/dz54240))      | backend                   |
| Josip Šare ([@josipsare](https://www.github.com/josipsare))       | backend                   |
| Ivan Elez ([@eca041](https://www.github.com/eca041))              | backend                   |

## Kontribucije
Pogledaj [CONTRIBUTING.md](https://github.com/VelicanstveniTimRaketa/prijava-ostecenja-javnih-povrsina).

## Startanje backend servisa
Pozicioniranje u backend direktorij i pokretanje: 
--> **mvn clean spring-boot:run** <-- naredbe u terminalu.
Izvršavanjem ove naredbe pokrenut će se spring-boot server na localhost:8080.

## Backend package organizacija
U backend servisu postoje četiri glavna packagea/foldera. 
Package pod imenom controller sadrži rest api kontrolere koji primaju http 
zahtjeve i vraćaju odgovarajući response tj. potrebne podatke ili izvršavaju potrebne akcije. 
Package pod imenom domain sadrži modele iz baze podataka tj. objekti nad kojima radimo operacije. Unutar tih 
fileova definiraju se svojstva objekata unutar same baze kako bi se tablice konfigurirale na ispravan način.
Package pod imenom repository sadrži fileove koji rade direktne operacije nad bazom podataka. Operacije se provode
na pomoću već spomenutog object relational mapinga u javi.
Package pod imenom service sadrži svu bussines logiku koja je implementirana u kodu. Cilj je postići što jednostavnije 
i preglednije kontrolere i repository fileove čiste tako da se unutart njih obavlja isključivo opearcije nad bazom.
