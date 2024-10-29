## Dokumentering af Cycling API

Project: SP2

Project team: sp-2-team-20

By: Lars Grønberg and Nicolai Pejtersen

Class: Datamatiker - 3.Sem – Hold B

Date: 21.10.2024 - 25.10.2024

Github Repo: https://github.com/LaCabraGrande/cycling_api.git

Deployed: https://bicycle.thegreenway.dk/api/routes

## Beskrivelse:
Cycling API muliggør administration af cykler og deres komponenter med fulde CRUD-operationer. Brugere kan oprette og
læse, mens administratorer kan opdatere og slette cykler og deres dele via dedikerede endpoints. API'en inkluderer solid
fejlhåndtering, som giver meningsfulde svar på klientforespørgsler, hvilket gør det ideelt til applikationer, der kræver
detaljeret cykelinformation og komponentadministration.


## Hvordan det kører

- Opret en database i din lokale Postgres-instans kaldet bicycleapi.
- Kør hovedmetoden i Main-klassen for at starte serveren på port 7070, oprette tabellerne i databasen og populere
  databasen med nogle testdata (sker i ApplicationConfig-klassen). Du kan også gøre det fra endpointet
  http://localhost:7070/api/bicycles/populate.
- Se ruterne i din browser på http://localhost:7070/api/routes.
- Anmod om endpointet http://localhost:7070/api/bicycles/ i din browser for at se listen over cykler og deres dele.
- Brug dev.http-filen til at teste ruterne. GET/POST/PUT/DELETE-anmodninger er tilgængelige, og du vil også finde
  sikkerhedsruterne i filen.

## Hvordan vi tester:

5.4 Forskelle mellem enhedstest og integrationstest

Enhedstest handler om at teste små dele af koden, ofte enkeltstående metoder i klasser, for at sikre, at de fungerer,
som de skal. Disse tests køres i isolation, hvilket betyder, at de ikke afhænger af databaser eller
netværksforbindelser. Derfor er enhedstest hurtigere, fordi de kan køres uden at applikationen behøver at være i drift.
I enhedstest bruger vi ofte mocks og stubs til at simulere de dele af koden, som vi ikke tester.

Integrationstest tester derimod, hvordan flere dele af systemet arbejder sammen. I denne opgave fokuserer vi på, hvordan
BicycleDAO interagerer med databasen, for at sikre at CRUD-operationer (oprette, læse, opdatere og slette) fungerer
korrekt i en virkelighedsnær situation. Disse tests kræver oprettelse og sletning af testdata i databasen, hvilket gør
dem langsommere end enhedstest, men de giver et mere præcist billede af, hvordan systemet vil opføre sig i praksis.


6.5 Forskelle mellem test af REST-endpoints og enhedstest

Når vi tester REST-endpoints, handler det om at tjekke, hvordan API'et reagerer på forskellige HTTP-anmodninger. Dette
inkluderer at teste metoder som GET, POST, PUT og DELETE for at sikre, at serveren giver de rigtige svar og dataformater.
Under disse tests arbejder vi med en aktiv server, som f.eks. en Javalin-server, og vi har ofte brug for en testdatabase.

Forskellen på enhedstest er, at REST-endpoint tests ser på hele systemet fra anmodningen til svaret fra serveren.
De tester netværkskommunikation, hvordan data sendes og modtages, samt interaktionen med databasen. Det gør disse tests
mere omfattende og derfor også langsommere og mere komplekse end enhedstest. Men de er vigtige for at sikre, at API'et
fungerer korrekt i en situation, der minder om det virkelige liv.

## ER-Diagram:
![Er-Diagram](ER-Diagram-For-Cycling_Api.png)

## Endpoints:
![img.png](RoutesTable.png)

## Security:
Roles:
-	Anyone
-	User
-	Admin

## Request Body and Response Formats:
(1)	Bicycle format
-	Eksempel på GetBicycleById:

https://bicycle.thegreenway.dk/api/bicycles/1
{
"id": 1,
"brand": "Trek",
"model": "Domane SL6",
"size": 56,
"price": 3500,
"weight": 8.7,
"description": "High-performance road bike",
"frame": {
"id": 5,
"brand": "Trek",
"material": "Carbon",
"type": "Disc",
"weight": 1300,
"size": 56
},
"gear": {
"id": 1,
"brand": "Shimano",
"model": "Ultegra",
"material": "Aluminium",
"type": "Electronic",
"weight": 1422
},
"wheel": {
"id": 1,
"brand": "Zipp",
"material": "Carbon",
"type": "Disc",
"model": "404 Firecrest",
"weight": 1400,
"size": 25
},
"saddle": {
"id": 1,
"brand": "Fizik",
"material": "Carbon",
"model": "Arione",
"weight": 200,
"width": 140
}
}

## Errors:
(e) All errors are reported using this format (with the HTTP-status code matching the number)

Bicycle with wrong id (Id doesn't exist) :
{"timestamp":"2024-10-25 07:24:34","status":404,"message":"Bicycle ID not found"}
Bicycle with wrong id (Id in wrong format typed text) :
{"timestamp":"2024-10-25 07:27:36","status":400,"message":"Invalid bicycle ID, wrong format"}

(e1) - 400 Invalid format for ID, or 404 item with specified ID not found (e.g., "Invalid bicycle ID").
(e2) - 404 Protected route; user lacks permission to access this endpoint.
(e3) - 400 Invalid or missing data for POST requests (e.g., "Invalid ID for associated entity").
(e4) - 401 Login failed due to invalid credentials.
(e5) - 400 Registration failed due to invalid input.
(e6) - 403 Insufficient privileges to assign roles.
(e7) - 400 Invalid format or missing data for PUT requests; 404 if ID not found.
(e8) - 404 ID not found for delete; 400 for invalid ID format.



