# TP GLO4002 2016

Ce TP vise à vous initier à écrire du code à partir de spécifications par l'exemple (SbE) découlant de *User Stories*. 

Chaque *User Story* possède des tests d'acceptation vous permettant de déterminer comment l'application doit réagir dans certains contextes.  

Le TP vise aussi a vous familiariser à la syntaxe `Gherkin` et à son automatisation avec `Cucumber` 

## Consignes

-   Travail individuel
-   Vous devez automatiser **tous** les scénarios en utilisant Cucumber
-   Le *niveau* du test est laissé à votre discrétion, mais nous nous attendons à ce que vous ayez des scénarios automatisés à plusieurs niveaux.
    -   Vous devez avoir au **minimum** un test *par fonctionnalité* utilisant `Rest Assured` 
    -   Vous devez essayer de respecter l'idée derrière la pyramide de tests (même si le faible nombre de scénarios pourrait vous empêcher d'obtenir exactement une pyramide)
-   Il n'est pas nécessaire d'être en mesure de rouler tous les scénarios à un niveau `medium` 
    -   Vous pouvez décider de fixer un seul niveau par scénario. Par exemple: le scénario `X` est à un niveau `médium` puis le scénario `y` au niveau `large`.
    -   Si vous trouvez plus simple d'avoir tous les scénarios à un niveau `medium` et certain `large` alors vous pouvez vous inspirer de la démonstration basée sur le laboratoire (tel que démontré en classe).
-   Vous devez aussi ajouter les tests unitaires pour la partie de code à ajouter
-   Nous vous invitons à essayer de le faire en ATDD

## Contraintes

-   Ne pas ajouter de nouvelles classes de "production"
-   Ne pas modifier la persistance (ajout ou modification)
-   Ne pas modifier l'architecture générale
-   Vous pouvez évidemment ajouter du code pour implémenter les nouvelles Stories

## Travail à effectuer

1.  Voir les [Stories](stories.md)
2.  Pour la fonctionnalité `Fonctionnalité: Ajouter une ordonnance` 
    *   Le code est déjà présent
    *   Vous devez relier le scénario avec le code (écrire la *Glue Cucumber*) 
3.  Pour la fonctionnalité `Fonctionnalité: Exécuter une ordonnance` 
    *   Le code n'existe pas et vous devez l'implémenter idéalement en ATDD
    *   Vous devez faire passer tous les scénarios
4.  Pour chaque scénario, vous devez justifier le niveau de test utilisé dans un commentaire en haut de celui-ci.
    *   Inscrivez une courte justification pour avoir choisi ce niveau

    *   Inscrivez également un résumé de votre stratégie (comment vous avez fait).

    *   Exemple: 

        ``` gherkin
        # Large car c'est ...
        # ...
        @large
        Scénario: Celui où l'ordonnance est valide
          ...
          
        # Medium car c'est ...
        # ...
        @medium
        Scénario: Celui où l'ordonnance est invalide
          ...
        ```

        ​

## Exécution

### Démarrer le projet

`mvn install && mvn exec:java -pl med-interfaces`

### Exécuter tous les tests

`mvn integration-test`