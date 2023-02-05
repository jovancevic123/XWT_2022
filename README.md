<h1 align="center">
  XML i Web Servisi
  <br>
</h1>

<p align="center">
  • FTN, Novi Sad, 2023</a>
</p>

## Autori:
  • Nikola Sovilj, SW75/2019,
  • Jovan Jovančević, SW01/2019,
  • Mihal Sabadoš, SW20/2019

## Tehnologije
    • Angular 15
    • Spring Boot (Java 11)
    • eXist DB
    • Apache Jena Fuseki

## Pokretanje projekta
- Inicijalizacija Docker container-a:
  - docker run -p 9090:8080 -d --name exist-db eXist-db/existdb:latest
  - docker run -p 3030:3030 --name fuseki -d apache/jena-fuseki:latest
  
- Pokretanje back-end aplikacija kroz InteliJ IDE pritiskom na run dugme:
  - Auth aplikacija/auth-app
  - A1 aplikacija/xml-backend
  - P1 aplikacija/xml-backend-p1
  - ZH1 aplikacija/xml-backend-zh1
  
- Pokretanje front aplikacija
  - Auth aplikacija/auth-front (ng serve --port 4205)
  - A1 aplikacija/xml-frontend (ng serve --port 4201)
  - P1 aplikacija/xml-frontend-p1 (ng serve --port 4200)
  - ZH1 aplikacija/zh-1-front (ng serve --port 4202)
