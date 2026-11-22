# Rapport final

## Présent au départ

Le projet contenait les neuf entités, les enums, les CRUD client/véhicule/
réservation, les DTO de véhicule/réservation, les mappers et une validation
de base.

## Corrigé ou ajouté

- audit statique documenté ;
- réponses JSON structurées pour 404, 400, 409 et erreurs inattendues ;
- recherches véhicule paginées par disponibilité ou ville/catégorie ;
- scheduler transactionnel de clôture des réservations échues ;
- configuration du scheduler ;
- protection contre les cycles JSON sur les associations ;
- documentation d'architecture, modèle, API, installation, Postman et tests ;
- collection Postman limitée aux endpoints réels.

## Vérification

`.\mvnw.cmd clean compile` et `.\mvnw.cmd test` ont réussi. Le contexte a
effectivement utilisé la base MySQL locale.

## Reste à faire

Les CRUD REST des agences, employés, équipements, contrats, paiements et
maintenances, OpenAPI/Swagger, sécurité par rôles, aspects AOP, statistiques
et tests métier détaillés ne sont pas prétendus terminés. Ils nécessitent
une phase dédiée sans inventer de contrat API absent de l'étude exploitable.
