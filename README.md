# AutoLoc API

AutoLoc est une API REST Spring Boot destinée à la gestion d’un service de location de véhicules.

Ce projet contient l’application de l’atelier **GoF et SOLID** sur le domaine AutoLoc, tout en conservant les fonctionnalités CRUD existantes.

## Technologies utilisées

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Lombok

## Fonctionnalités principales

- Gestion des clients ;
- Gestion des véhicules ;
- Gestion des réservations ;
- Validation des données reçues par l’API ;
- Calcul du prix d’une location selon plusieurs stratégies ;
- Préparation de la création de factures PDF ou Excel.

## Organisation du travail de l’atelier

### 1. Principe SRP

Le service des véhicules a été séparé en plusieurs responsabilités :

- [`VehiculeService`](src/main/java/tn/esprit/autoloc/service/VehiculeService.java) : opérations métier et CRUD des véhicules ;
- [`VehiculeValidator`](src/main/java/tn/esprit/autoloc/service/VehiculeValidator.java) : validation du tarif et du nombre de jours ;
- [`JournalisationService`](src/main/java/tn/esprit/autoloc/service/JournalisationService.java) : journalisation des opérations.

Chaque classe possède ainsi une raison principale de changer.

### 2. Principe DIP et injection de dépendances

[`VehiculeService`](src/main/java/tn/esprit/autoloc/service/VehiculeService.java) reçoit ses dépendances par constructeur :

- [`VehiculeRepository`](src/main/java/tn/esprit/autoloc/repository/VehiculeRepository.java) pour la persistance ;
- `VehiculeValidator` pour les validations ;
- `JournalisationService` pour les logs.

Spring crée automatiquement ces composants et les injecte dans le service. Le service ne crée donc pas lui-même ses dépendances.

### 3. Patron Strategy et principe OCP

L’interface [`StrategieTarification`](src/main/java/tn/esprit/autoloc/service/tarification/StrategieTarification.java) définit le contrat commun de calcul du prix.

Les stratégies disponibles sont :

| Nom | Classe | Règle |
|---|---|---|
| `standard` | `TarificationStandard` | tarif journalier × nombre de jours |
| `weekend` | `TarificationWeekend` | réduction de 10 % |
| `longueDuree` | `TarificationLongueDuree` | réduction de 20 % |
| `entreprise` | `TarificationEntreprise` | réduction de 15 % |

[`TarificationService`](src/main/java/tn/esprit/autoloc/service/tarification/TarificationService.java) sélectionne automatiquement la stratégie grâce à l’injection Spring d’une `Map<String, StrategieTarification>`.

Pour ajouter une nouvelle tarification, il suffit de créer une nouvelle classe qui implémente `StrategieTarification`. Le service de calcul n’a pas besoin d’être modifié : cela applique le principe OCP.

Exemple :

```java
tarificationService.calculer("entreprise", 100.0, 5);
```

Résultat : `425.0`, car la réduction entreprise est de 15 %.

### 4. Patron Factory

Les classes du package [`facture`](src/main/java/tn/esprit/autoloc/service/facture) représentent une Factory simple :

- [`Facture`](src/main/java/tn/esprit/autoloc/service/facture/Facture.java) : abstraction commune ;
- `FacturePdf` : facture au format PDF ;
- `FactureExcel` : facture au format Excel ;
- [`FactureFactory`](src/main/java/tn/esprit/autoloc/service/facture/FactureFactory.java) : centralise la création selon le format demandé.

Exemple :

```java
Facture facture = new FactureFactory().creer("pdf");
```

### 5. Principe ISP

L’interface générale de gestion des véhicules a été divisée en interfaces spécialisées dans [`service/vehicule`](src/main/java/tn/esprit/autoloc/service/vehicule) :

- `GestionVehiculeLecture` ;
- `GestionVehiculeEcriture` ;
- `GestionVehiculeRapport` ;
- `GestionVehiculeNotification`.

Un composant qui consulte uniquement les véhicules ne dépend donc pas des méthodes d’export ou de notification.

### 6. Gestion globale des erreurs REST

[`GlobalExceptionHandler`](src/main/java/tn/esprit/autoloc/web/GlobalExceptionHandler.java) centralise les erreurs de validation de tous les contrôleurs REST.

Lorsqu’un contrôleur utilise `@Valid` et qu’une donnée est incorrecte, Spring déclenche `MethodArgumentNotValidException`. Le handler transforme cette exception en réponse HTTP `400 Bad Request` avec les erreurs par champ.

Exemple de réponse :

```json
{
  "nom": "Le nom est obligatoire",
  "email": "L'email doit être valide",
  "telephone": "Le téléphone doit contenir exactement 8 chiffres"
}
```

## Configuration de la base de données

La configuration se trouve dans [`application.properties`](src/main/resources/application.properties) :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/autoloc_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

Avant de lancer l’application, démarrer MySQL avec XAMPP ou un autre serveur MySQL.

## Lancer l’application dans IntelliJ IDEA

1. Ouvrir le projet Maven dans IntelliJ IDEA.
2. Vérifier que le SDK Java 17 est sélectionné.
3. Démarrer MySQL.
4. Ouvrir [`AutolocApiApplication.java`](src/main/java/tn/esprit/autoloc/AutolocApiApplication.java).
5. Cliquer sur le bouton vert **Run** situé à côté de `main`.

L’API démarre normalement sur :

```text
http://localhost:8080
```

Si le port est déjà utilisé, arrêter l’ancienne instance ou ajouter :

```properties
server.port=8081
```

## Tester l’API

### Lire les clients

```http
GET http://localhost:8080/api/clients
```

### Ajouter un client valide

```http
POST http://localhost:8080/api/clients
Content-Type: application/json

{
  "nom": "Ben Ali",
  "prenom": "Ahmed",
  "email": "ahmed@example.com",
  "telephone": "22123456",
  "numPermis": "PERMIS123",
  "dateInscription": "2026-11-16"
}
```

### Tester une erreur de validation

```http
POST http://localhost:8080/api/clients
Content-Type: application/json

{
  "nom": "",
  "prenom": "",
  "email": "email-invalide",
  "telephone": "123",
  "numPermis": "",
  "dateInscription": null
}
```

Résultat attendu : HTTP `400 Bad Request` avec les messages produits par [`GlobalExceptionHandler`](src/main/java/tn/esprit/autoloc/web/GlobalExceptionHandler.java).

## Tests dans IntelliJ IDEA

Le test de contexte se trouve dans [`AutolocApiApplicationTests`](src/test/java/tn/esprit/autoloc/AutolocApiApplicationTests.java).

Pour l’exécuter :

1. Ouvrir la classe de test.
2. Cliquer sur le bouton vert à côté de `contextLoads()`.
3. Vérifier que le résultat affiche une coche verte.

Les requêtes HTTP peuvent être exécutées directement depuis IntelliJ avec un fichier `.http`, Postman ou Insomnia.

## Synthèse SOLID et GoF

- **SRP** : séparation du service, de la validation et de la journalisation ;
- **DIP** : dépendances injectées par Spring ;
- **OCP** : ajout de nouvelles tarifications sans modifier le service ;
- **ISP** : interfaces spécialisées pour les véhicules ;
- **LSP** : les implémentations doivent respecter le contrat de leur abstraction ;
- **Strategy** : encapsulation des formules de tarification ;
- **Factory** : centralisation de la création des factures.

Une synthèse plus détaillée est disponible dans [`docs/atelier-gof-solid.md`](docs/atelier-gof-solid.md).
