# Audit du projet AutoLoc

## Périmètre et méthode

Audit statique réalisé sur le dépôt au 22 novembre 2026 à partir des sources
présentes, du `pom.xml`, de la configuration Spring et de la consigne métier
fournie. La commande Maven de référence doit être exécutée avec
`.\mvnw.cmd` sous Windows. Aucun résultat de compilation ou de test n'est
considéré comme acquis tant qu'il n'a pas été exécuté avec cette commande.

## Éléments conformes

- Projet Maven Spring Boot avec Java 17 déclaré.
- JPA et validation Jakarta sont présents.
- Les neuf entités et les cinq énumérations du modèle sont présentes.
- Les relations principales du modèle sont déjà représentées : agence/
  véhicule/employé, véhicule/équipement, client/réservation,
  réservation/contrat, contrat/paiement et véhicule/maintenance.
- Les associations utilisent majoritairement `LAZY`; les cascades explicites
  existantes correspondent aux règles demandées pour client/réservation,
  contrat/paiement et maintenance/véhicule.
- Les CRUD client, véhicule et réservation existent.
- `ReservationDTO`, `VehiculeDTO`, leurs mappers et `@Valid` existent.
- La validation des dates de réservation et le tarif positif sont déjà
  couverts.
- L'injection par constructeur est utilisée dans les services et contrôleurs.
- Une première séparation Strategy, Factory et interfaces spécialisées est
  documentée dans `docs/atelier-gof-solid.md`.

## Éléments partiellement réalisés

- Les contrôleurs client et véhicule exposent directement une entité JPA
  pour les clients; les autres ressources n'ont aucun DTO/API.
- La couche service n'existe que pour client, réservation et véhicule.
- Les validations de domaine sont incomplètes pour les entités restantes et
  les colonnes JPA ne rendent pas partout les champs obligatoires.
- Le handler global ne traite que `MethodArgumentNotValidException`.
- Les associations bidirectionnelles ne disposent pas de méthodes
  d'affectation/désaffectation cohérentes.
- Le repository véhicule ne propose ni disponibilité, ni recherche, ni
  pagination.
- La tarification existe mais ne couvre pas explicitement catégorie,
  période et équipements et n'est pas exposée par une API.
- La journalisation est un service appelé manuellement, sans aspect AOP.
- Il n'y a pas de scheduler, de documentation OpenAPI, de sécurité
  fonctionnelle, de collection Postman ni de couverture de tests métier
  significative.

## Éléments manquants

- CRUD REST pour agences, employés, équipements, contrats, paiements et
  maintenances.
- Repositories et services correspondants.
- Requêtes de disponibilité, recherche et agrégation.
- Scheduler configurable pour les traitements demandés.
- DTO/mappers pour les ressources nouvellement exposées.
- Format d'erreur JSON homogène pour 404, conflits, références et erreurs
  inattendues.
- Guides et rapport final demandés.

## Risques techniques

1. Retourner des entités JPA peut déclencher des chargements lazy ou des
   cycles JSON lors de l'évolution des contrôleurs.
2. Les erreurs `NoSuchElementException` sont actuellement capturées dans
   certains contrôleurs seulement, ce qui rend le contrat HTTP incohérent.
3. Les références client/véhicule sont vérifiées dans le service réservation,
   mais les autres relations n'ont pas encore de validation métier centralisée.
4. `ddl-auto=update` est adapté au développement local indiqué, mais ne
   constitue pas une stratégie de migration de production.
5. La connexion MySQL dépend d'un serveur local et ne peut pas être validée
   sans vérifier XAMPP.

## Plan de correction priorisé

1. Mesurer compilation et tests de référence.
2. Uniformiser les erreurs REST sans modifier les contrats fonctionnels
   existants.
3. Ajouter contraintes de validation et méthodes d'association sûres.
4. Ajouter les repositories, services, DTO et contrôleurs manquants.
5. Ajouter recherches, pagination, disponibilité et traitement planifié.
6. Ajouter OpenAPI, tests ciblés et collection Postman.
7. Finaliser la documentation et exécuter les vérifications disponibles.

## Limites de l'audit

Le PDF fourni est un document binaire dont le texte n'est pas exploitable par
les outils de lecture disponibles dans cet environnement. Les règles
explicitement reproduites dans la consigne et le modèle EA joint ont donc été
utilisés comme exigences de référence; toute règle non visible reste à
confirmer avant d'en faire une contrainte métier.
