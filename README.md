# Microservice

1. Il faut d'abord créer un projet java maven simple
2. Créer les modules spring boot (les microservice)
   - Clique droit sur le nom du projet, nouveau et cliquer sur creer un module
   - Les dépendances à installer
     - spring-boot-starter-data-rest pour créer rapidement un web service rest
     - spring-cloud-starter-netflix-eureka-client pour effectuer l'enregistrement du nom du microservice au démarrage
     - spring-cloud-starter-config permet de contacter le service de configuration au démarrage du microservice
     - spring-boot-starter-actuator permet de faire du monitoring
3. Creer le module gateway service pour pouvoir router les requêtes http vers les microservice
   - les dépendances à installer
     - spring-cloud-starter-config
     - spring-boot-starter-actuator
     - spring-cloud-starter-gateway-mvc /!\ nouvelle version, il faut d'abord la tester et apprendre à utiliser
     - spring-cloud-starter-gateway ancienne version celle qui est utilisé dans cette application
     - spring-cloud-starter-netflix-eureka-client
   - Configuration static : utiliser le fichier application.yml
   - configuration dynamique : passer par le module discovery service
4. Creer le module discovery service pour l'enregistrement des microservices
   - la dépendance à installer
     - spring-cloud-starter-netflix-eureka-server
   - l'annotation à ajouter dans le point d'entrée de l'application
     - @EnableEurekaServer
   - pour afficher l'interface de eureka discovery
     - http://localhost:8761
   - Lorsque tu veux utiliser le discovery service avec eureka, il faut aller activer dans chaque microservice l'enregistrement automatique
     - dans le fichier application.properties des microservices ainsi que la gateway
       - spring.cloud.discovery.enabled=true
     - indiquer aux microservices où se trouve eureka discovery. Donc apportée cette modification dans chaque microservices
       - eureka.client.service-url.defaultZone=http://localhost:8761/eureka
     - pour que les microservices s'enregistre avec leurs adress ip au lieu de s'enregistrer avec leur nom ce qui est fait par défaut il faut
       - eureka.instance.prefer-ip-address=true
5. Ordre de démarrage
    - Démarrer le discovery service en premier
    - ensuite les microservices
    - enfin la gateway
    - S'il y avait un service de configuration, il serait le premier à être démarré
6. Préparation pour la configuration du système de routage de manière dynamique
   - Pour passer en configuration dynamique, il faut abandonner le fichier application.yml de gateway-service qui sert à la configuration statique. Il faut le renommer en app.yml
7. Configuration des routes de manière dynamique
   - Il faut creer un @Bean dans une class de configuration ou dans le point d'entrée de l'application du module gateway. Il s'agit de ce Bean DiscoveryClientRouteDefinitionLocator
8. Utilisation de la route dynamique
   - Une fois la configuration faite, pour accéder aux api il faut saisir l'adresse de la Gateway suivi du nom du microservice et de l'api a appeler. Exemple : http://localhost:8888/CUSTOMER-SERVICE/api/customers
   - tu remarqueras que le nom du microservice ici est écrit en majuscule pour permettre de l'écrire en minuscule il faut aller vers la gateway et ajouter la propriete suivante
     - spring.cloud.gateway.discovery.locator.lower-case-service-id=true

