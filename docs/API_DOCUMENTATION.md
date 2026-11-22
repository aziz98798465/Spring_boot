# API disponible

Les endpoints actuellement implémentés sont :

| Ressource | Base | Opérations |
|---|---|---|
| Clients | `/api/clients` | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} |
| Véhicules | `/api/vehicules` | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} |
| Réservations | `/api/reservations` | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} |

Fonctions véhicule supplémentaires :

- `GET /api/vehicules/disponibles?page=0&size=20`
- `GET /api/vehicules/recherche?ville=Tunis&categorie=BERLINE`

Les entités restantes sont persistées mais ne disposent pas encore d'un
contrôleur REST dédié. Il serait incorrect de documenter des routes fictives.
