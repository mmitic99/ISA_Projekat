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
- pokrenuti FishingBookingAppApplication.java kao java aplikaciju
- ukoliko se koristi Eclipse IDE, desni klik na projekat Maven -> UpdateProject
- nakon toga ubaciti Environment variable u Intelij, klikom na Run -> Edit Configurations, u polju Environment variables ubaciti: **POSTGRES_DB=FishingBooking;POSTGRES_HOST=localhost;POSTGRES_PASSWORD=root;POSTGRES_USERNAME=postgres;BACKEND_URL=http://localhost:8080**
- zatim pokrenuti FishingBookingAppApplication.java kao java aplikaciju
- aplikacije će se pokrenuti na portu 8080

## Aplikacija je dostupna i na Heroku platformi:
- frontend: https://isa-projekat-tim27.herokuapp.com
- backend: https://isa-projekat-tim27-backend.herokuapp.com

### Napomena:
- pre korišćenja aplikacije, da bi slanje mejla radilo, ulogovati se na mejl, ići na sledeći [link](https://accounts.google.com/DisplayUnlockCaptcha) i kliknuti na dugme Nastavi.
