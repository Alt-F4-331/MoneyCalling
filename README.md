# MoneyCalling

### Tehnologii:
- *Back-End*: Java cu Spring Boot și baza de date PostgreSQL.
- *Front-End*: React, folosind JavaScript, TypeScript, JSX, HTML, CSS, și opțional GraphQL și JSON.

---

### Cum va funcționa aplicația?

#### *Ecranul de Start (Login)*
La deschiderea aplicației, utilizatorul va fi întâmpinat de un ecran de login. Aici, se va solicita introducerea *username-ului* și a *parolei. Dacă utilizatorul nu are un cont, poate apăsa pe butonul **"Creare cont"*, care îl va direcționa către ecranul de înregistrare. Dacă are deja un cont, se va autentifica folosind datele salvate în baza de date și va fi redirecționat către ecranul principal al aplicației.

> *Notă: Ecranul de login va conține doar **două câmpuri* (username și parolă) și *două butoane* (Login și Creare cont).

---

#### *Ecranul de Creare Cont*
După apăsarea butonului "Creare cont", utilizatorul va fi redirecționat către un ecran denumit "Creare Cont". Acest ecran va conține *un singur câmp* de introducere a datelor și *un singur buton* de submit. După introducerea fiecărui câmp, cerința de introducere se va schimba, iar utilizatorul va completa succesiv informațiile cerute:

- Prima dată va introduce *numele*.
- După apăsarea butonului Submit, câmpul de introducere se va reseta și cerința va deveni *prenume*.
- Acest proces se va repeta pentru email și alte date necesare până la completarea tuturor informațiilor din profilul utilizatorului.

Odată completat, contul va fi salvat în baza de date, iar utilizatorul va fi redirecționat automat către ecranul principal al aplicației.

> *Notă: Ecranul de creare cont va conține în permanență **un singur câmp* și *două butoane* (Submit și Go Back).

---

#### *Ecranul Principal*
Ecranul principal va afișa o diagramă de tip *"Pizza"* care ilustrează distribuția bugetului utilizatorului pe diverse categorii de cheltuieli zilnice, cum ar fi:
- *Cumpărături*
- *Facturi întreținere*
- *Facturi telefon/televizor*

Aceste categorii vor fi predefinite cu cheltuieli de bază, dar utilizatorul va putea adăuga noi categorii de cheltuieli prin completarea unor rapoarte. Vor exista funcționalități adiționale precum:
- *Buget chirie*: Adăugarea cheltuielilor legate de chirie.
- *Achiziții costisitoare*: Gestionarea achizițiilor mari (electrocasnice, vacanțe, vehicule etc.).
- *Editare profil financiar*: Modificarea profilului financiar, în cazul schimbărilor de venit sau alte detalii financiare personale.

Fiecare raport va avea un ecran dedicat, pentru o gestionare clară a cheltuielilor și modificărilor financiare.
