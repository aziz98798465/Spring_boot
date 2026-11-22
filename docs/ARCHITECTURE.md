# Architecture AutoLoc

L'application suit une architecture Spring en couches :

- `domain` : entités JPA et énumérations ;
- `repository` : accès Spring Data ;
- `service` : orchestration métier, validation et planification ;
- `web.controller` : contrats HTTP ;
- `web.dto` : contrats et conversions pour véhicules/réservations.

Les services utilisent l'injection par constructeur. Les références
bidirectionnelles non nécessaires aux réponses JSON sont ignorées pour éviter
les cycles. La sécurité par rôles n'est pas activée : aucune authentification
fonctionnelle n'est actuellement configurée.

La tâche planifiée de libération des véhicules est transactionnelle et
configurable avec `autoloc.scheduler.release-cron`.
