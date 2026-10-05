# Travel Agency API

API REST para agência de viagens, com **PostgreSQL** via **Spring Data JPA** e autenticação **Spring Security + JWT**, com controle de acesso por perfil (`ADMIN` / `CLIENTE`).

## Stack

Java 17 · Spring Boot 3.3.4 · Spring Data JPA (Hibernate) · Spring Security + JJWT · PostgreSQL · Lombok

## Regras de acesso

| Rota | Método | Acesso |
| --- | --- | --- |
| `/auth/registrar`, `/auth/login` | POST | Público |
| `/api/**` | GET | Qualquer usuário autenticado |
| `/api/**` | POST/PUT/DELETE | Apenas `ROLE_ADMIN` |

Autenticação via `Authorization: Bearer <token>`, obtido em `/auth/login`.

## Entidades

- **Usuario** — autenticação e perfil (`ADMIN` / `CLIENTE`)
- **Destino** — destinos turísticos
- **Hotel** — hotéis vinculados a um destino, com vagas disponíveis
- **PacoteViagem** — pacotes de viagem vinculados a um destino
- **Atividade** — atividades turísticas vinculadas a um pacote

## Endpoints principais

- `/api/destinos` — CRUD de destinos
- `/api/hoteis`, `/api/hoteis/destino/{id}` — CRUD de hotéis
- `/api/pacotes`, `/api/pacotes/destino/{id}` — CRUD de pacotes
- `/api/atividades`, `/api/atividades/pacote/{id}` — CRUD de atividades
