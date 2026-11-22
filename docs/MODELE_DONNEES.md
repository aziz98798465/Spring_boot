# Modèle de données

Le modèle comprend `Agence`, `Vehicule`, `Equipement`, `Client`, `Employe`,
`Reservation`, `Contrat`, `Paiement` et `Maintenance`.

Relations principales :

- une agence possède plusieurs véhicules et employés ;
- un véhicule appartient à une agence et possède plusieurs équipements ;
- un client possède plusieurs réservations ;
- une réservation référence un client et un véhicule et peut avoir un contrat ;
- un contrat possède plusieurs paiements ;
- un véhicule possède plusieurs maintenances.

Les enums sont stockées sous forme de chaînes. Les collections sont lazy et
les cascades suivent les règles précisées dans l'étude de cas.
