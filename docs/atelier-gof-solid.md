# Application de l’atelier GoF et SOLID dans AutoLoc

## Partie A : responsabilités identifiées

La classe de départ mélangeait quatre responsabilités :

- valider le tarif et le nombre de jours ;
- journaliser les opérations ;
- gérer la persistance des véhicules ;
- calculer le prix d’une location.

Ces responsabilités ont des raisons de changement différentes. Une règle de validation peut évoluer sans modifier la persistance, et le mécanisme de journalisation peut évoluer sans modifier le métier des véhicules.

Le principe appliqué est le **SRP** : chaque classe garde une responsabilité principale.

## Partie B : SRP et DIP

`VehiculeService` orchestre maintenant les opérations métier et délègue :

- `VehiculeValidator` : validation des règles métier ;
- `JournalisationService` : journalisation avec le logger Spring/SLF4J ;
- `VehiculeRepository` : accès aux données.

Le service dépend de l’abstraction `JpaRepository` exposée par `VehiculeRepository`, au lieu de construire lui-même un accès à la base. Spring crée le repository et injecte ses dépendances dans le constructeur de `VehiculeService`. Cette injection concrétise le **DIP** et la **DI** : le service reçoit son contrat de persistance sans être couplé à sa construction.

## Partie C : Strategy et OCP

`StrategieTarification` représente l’algorithme de calcul. Les implémentations sont :

- `standard` : tarif journalier × nombre de jours ;
- `weekend` : réduction de 10 % ;
- `longueDuree` : réduction de 20 % ;
- `entreprise` : réduction de 15 %.

`TarificationService` reçoit la `Map<String, StrategieTarification>` créée par Spring. Il sélectionne la stratégie par son nom et lève une `IllegalArgumentException` si le type est inconnu.

Le patron **Strategy** isole les formules qui peuvent varier. Le **OCP** est respecté : l’ajout de `TarificationEntreprise` n’exige aucune modification de `TarificationService`.

## Partie D : Factory, ISP et LSP

`FactureFactory.creer(String format)` centralise la création de `FacturePdf` et `FactureExcel`. Le code appelant dépend de `Facture`, et la logique de choix des classes concrètes reste à un seul endroit.

L’interface `GestionVehicule` initiale était trop large. Elle est découpée en contrats spécialisés : `GestionVehiculeLecture`, `GestionVehiculeEcriture`, `GestionVehiculeRapport` et `GestionVehiculeNotification`. Cette séparation applique l’**ISP** : un composant de consultation ne dépend que des méthodes de lecture.

Une sous-classe de `Vehicule` qui lance systématiquement `UnsupportedOperationException` pour une méthode attendue par le type parent ne peut pas remplacer correctement ce parent. Elle viole le **LSP**. La solution est de revoir l’abstraction : déplacer l’opération dans une interface spécialisée ou fournir une implémentation valide pour chaque sous-type.
