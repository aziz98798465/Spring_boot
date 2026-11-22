# Guide Postman

La collection `AutoLoc_API_Tests.postman_collection.json` utilise la variable
`baseUrl`, initialisée à `http://localhost:8080`.

Les requêtes sont limitées aux routes réellement disponibles : clients,
véhicules, réservations, recherche et disponibilité. Créer d'abord un client
et un véhicule, puis reporter les identifiants retournés dans le body d'une
réservation. Les routes des autres entités ne sont pas incluses tant qu'elles
ne sont pas implémentées.
