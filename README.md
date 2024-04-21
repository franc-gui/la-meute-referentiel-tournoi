# About

Service Web pour l'association La Meute proposant des apis permettant de suivre les joueurs, leur score dans les parties jouées et d'optimiser la 
répartition de ceux-ci dans chaque ronde pour que chaque joueur rencontre autant de fois que possible chaque autre 
joueur

# Getting Started

###### Postgresql

- Installer PostgreSql : https://www.enterprisedb.com/downloads/postgres-postgresql-downloads (se souvenir du mdp postgres)
- Ajouter le chemin <ton_chemin_dinstallation>\PostgreSQL\16\bin au Path de Windows dans les variables d'environnement système
- En ligne de commande, se connecter à l'invit de commande postgres `psql -U postgres`
- Dans l'invit de commande, créer un nouvel utilisateur tournoi : `CREATE USER tournoi WITH PASSWORD '<ton_mdp>';`
- Dans l'invit de commande, créer une nouvelle bdd tournoi : `CREATE DATABASE referentiel_tournoi OWNER tournoi;`

###### Java

- Installer Java 21 : https://www.oracle.com/java/technologies/downloads/#jdk21-windows
- Le lier dans son IDE (si Intellij Ctrl+Alt+Shift+S -> JDK)

