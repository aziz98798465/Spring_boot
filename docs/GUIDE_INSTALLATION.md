# Guide d'installation

Prérequis : Java 17+, Maven Wrapper et MySQL/XAMPP sur le port 3306.

1. Créer ou laisser l'application créer `autoloc_db`.
2. Vérifier le compte `root` sans mot de passe dans
   `src/main/resources/application.properties`.
3. Compiler : `.\mvnw.cmd clean compile`.
4. Tester : `.\mvnw.cmd test`.
5. Démarrer : `.\mvnw.cmd spring-boot:run`.

`spring.jpa.hibernate.ddl-auto=update` est conservé pour le développement
local. Aucun secret n'est stocké dans le dépôt.
