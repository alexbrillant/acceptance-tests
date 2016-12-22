# language: fr
Fonctionnalité: Exécuter une ordonnance

  Afin de pouvoir obtenir mon médicament à la pharmacie
  En tant que patient
  Je veux pouvoir exécuter une ordonnance

  Règles d'affaires
  - Une ordonnance a une date limite
  - Une ordonnance a un nombre maximal de répétitions

  Scénario: Celui où c'est la première exécution non expirée
    Étant donné une patiente Alice
    Et une nouvelle ordonnance d'acétaminophène
    Quand Alice demande a exécuter l'ordonnance d'acétaminophène
    Alors l'exécution est autorisée
    Et la date d'exécution est conservée

  Plan du scénario: Celui où on exécute une répétition
    Étant donné une patiente Alice
    Et une ordonnance d'acétaminophène au dossier de Alice avec 2 répétitions
    Quand Alice demande a exécuter l'ordonnance d'acétaminophène pour la <execution>e fois
    Alors l'exécution est <resultat?>

    Exemples:
      | execution | resultat? |
      | 1         | autorisée |
      | 2         | autorisée |
      | 3         | refusée   |


  Plan du scénario: Celui où l'ordonnance est expirée
    Étant donné une patiente Alice
    Et une ordonnance d'acétaminophène au dossier de Alice expirant le '2016-12-12'
    Quand Alice demande à exécuter l'ordonnance d'acétaminophène le <date>
    Alors l'exécution est <resultat?

    Exemples:
      | date       | resultat? |
      | 2016-12-11 | autorisée |
      | 2016-12-12 | autorisée |
      | 2016-12-13 | refusée   |