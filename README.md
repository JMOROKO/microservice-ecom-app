# Micro service

1. Il faut d'abord créer un projet java maven simple
2. Créer les modules spring boot (les microservice)
   - Clique droit sur le nom du projet, nouveau et cliquer sur creer un module
   - Les dépendances à installer
     - spring-boot-starter-data-rest pour créer rapidement un web service rest
     - spring-cloud-starter-netflix-eureka-client pour effectuer l'enregistrement du nom du microservice au démarrage
     - spring-cloud-starter-config permet de contacter le service de configuration au démarrage du microservice
     - spring-boot-starter-actuator permet de faire du monitoring