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
9. Centralisation des fichiers de configuration
    - Il faut creer un module config-service
    - Il faut creer les fichiers de configuration pour chaque microservice
      - application.properties pour conserver la configuration commune pour chaque fichier de configuration
      - customer-serive.properties pour conserver la configuration spécifique du microservice customer-service
      - customer-serive-dev.properties pour conserver la configuration spécifique du microservice customer-service en mode developpement
      - customer-serive-prod.properties pour conserver la configuration spécifique du microservice customer-service en mode production
    - Dans la configuration commune (application.properties) il faut activer les endpoints health et refresh
      - management.endpoints.web.exposure.include=health refresh
    - Ajouter la dépendance spring-cloud-starter-config dans les micro services et dans la gateway
    - creer un micro service config-service
      - ajouter la dépendance spring-cloud-starter-config
      - ajouter l'annotation @EnableConfigServer dans le point d'entrée de l'application
      - ajouter la dépendance spring-cloud-starter-actuator pour pouvoir faire du monitoring
      - ajouter la dépendance spring-boot-starter-web pour pouvoir exposer le service de configuration
      - ajouter la dépendance spring-cloud-starter-netflix-eureka-client pour pouvoir s'enregistrer dans le discovery service
      - il faut aussi déterminer ou trouver les fichiers de configuration dans le fichier application.properties
        - Si la configuration est en local
          - spring.cloud.config.server.git.uri=E:/exercices/spring/microservice-ecom-app/config-repo
        - Si la configuration est sur github
          - spring.cloud.config.server.git.uri=https://github.com/JMOROKO/bank-account-config-repo
      - Une fois le microservice démarré, 
        - il faut aller vers l'url http://localhost:8888/application/default pour voir la configuration de l'application par defaut
        - il faut aller vers l'url http://localhost:8888/customer-service/default pour voir la configuration du microservice customer-service
        - il faut aller vers l'url http://localhost:8888/customer-service/dev pour voir la configuration du microservice customer-service en mode developpement
        - il faut aller vers l'url http://localhost:8888/customer-service/prod pour voir la configuration du microservice customer-service en mode production
    - Dans les fichiers application.properties de chaque microservice et de la gateway, il faut ajouter la configuration suivante
      - spring.cloud.config.enabled=true # pour activer la configuration mais se n'est pas obligatoire car sa valeur par defaut est true
      - spring.config.import=optional:configserver:http://localhost:9999 pour aller récupérer la configuration ou http://localhost:9999 represente l'adresse de configuraiton
      - pour pouvoir récupérer les configurations modifié à chaud il faut ajouter la dépendance spring-cloud-starter-actuator
      - Ajouter @RefreshScope pour permettre de rafraichir la configuration à chaud
      - Faire du repertoire config-repo un repository git
      - Utiliser les commandes git pour faire le commit et le push si necessaire
      - Pour faire le refresh à chaud de la configuration, il faut aller vers l'url 
        - Pour le micro-service customer-service
            - POST http://localhost:8082/actuator/refresh
              Accept: application/json
        - Pour le micro-service account-service
            - POST http://localhost:8081/actuator/refresh
              Accept: application/json
        - ...
   10. Maintenant démarrons les services pour le test
        - Commencer par démarrer le discovery service
        - Ensuite démarrer le config service
        - Ensuite démarrer le customer service
        - 
