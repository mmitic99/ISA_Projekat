[![CircleCI](https://circleci.com/gh/mmitic99/ISA_Projekat.svg?style=svg)](https://circleci.com/gh/mmitic99/ISA_Projekat)
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=mmitic99_ISA_Projekat)](https://sonarcloud.io/summary/new_code?id=mmitic99_ISA_Projekat)

# ISA_Projekat

## Korišćene tehnologije
- SpringBoot
- Angular
- PostgreSql

## Pokretanje aplikacije lokalno:

### Frontend
 - ukljuciti terminal, pozicionirati se u folder 'ISA_Projekat\FishingBookingApp-frontend'
- kucati naredbu **npm install**, zatim **npm update**
- pokrenuti aplikaciju sa naredbom **ng s -o**
- aplikacije će se pokrenuti na [http://localhost:4200](http://localhost:4200/)

### Backend
- preporučuje se pokretanje aplikacije iz InteliJ IDEA
- importovati projekat
- nakon toga ubaciti Environment variable u Intelij, klikom na Run -> Edit Configurations, u polju Environment variables ubaciti: **POSTGRES_DB=FishingBooking;POSTGRES_HOST=localhost;POSTGRES_PASSWORD=root;POSTGRES_USERNAME=postgres;BACKEND_URL=http://localhost:8080**
- zatim pokrenuti FishingBookingAppApplication.java kao java aplikaciju
- aplikacije će se pokrenuti na portu 8080

- ukoliko se koristi Eclipse IDE, nakon importa maven projekta, desni klik na projekat Maven -> UpdateProject
- ubaciti Environment variable, desni klik na FishingBookingAppApplication.java -> Properties -> Run/Debug settings -> New -> Java Application -> OK -> u tabu Environment dodati sve Environment variable
- pokrenuti aplikaciju FishingBookingAppApplication.java -> Run as -> Java Application

## Aplikacija je dostupna i na Heroku platformi:
- frontend: https://isa-projekat-tim27.herokuapp.com
- backend: https://isa-projekat-tim27-backend.herokuapp.com

### Napomena:
- pre korišćenja aplikacije, da bi slanje mejla radilo, ulogovati se na mejl, ići na sledeći [link](https://accounts.google.com/DisplayUnlockCaptcha) i kliknuti na dugme Nastavi.

### Podaci za prijavljivanje u aplikaciju:

| Tip korisnika| Email | Lozinka |
|--|--|--|
| Običan korisnik | isaproject.tim27+2@gmail.com | 123 |
| Običan korisnik | isaproject.tim27+6@gmail.com | 123 |
| Vlasnik vikendice | isaproject.tim27+3@gmail.com | 123 |
| Vlasnik broda | isaproject.tim27+5@gmail.com | 123 |

