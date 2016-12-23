# language: fr
Fonctionnalité: Exécuter une ordonnance

  Afin de pouvoir obtenir mon médicament à la pharmacie
  En tant que patient
  Je veux pouvoir exécuter une ordonnance

  Règles d'affaires
  - Une ordonnance a une date limite
  - Une ordonnance a un nombre maximal de répétitions

  # Large car montrer que le happy path fonctionne de bout en bout est important
  # je fait un appel rest à /patients/{patientId}/prescriptions/{prescriptionId};
  # je vérifie le code de retour de l'appel rest;
  # je vais voir dans la bd si une nouvelle date d'exécution a été ajouté à ma prescription;
  @large
  Scénario: Celui où c'est la première exécution non expirée
    Étant donné une patiente Alice
    Et une nouvelle ordonnance d'acétaminophène
    Quand Alice demande a exécuter l'ordonnance d'acétaminophène
    Alors l'exécution est autorisée
    Et la date d'exécution est conservée

  # Medium car c'est long d'exécuter plusieurs requêtes à répétitions
  # j'utilise PatientRepository pour aller chercher un patient qui existe et qui a une prescription;
  # j'exécute une prescription valide pour le patient(domaine) le nombre de fois demandé;
  # je vérifie que le renewals de la prescription a baissé ou que l'exécution a tiré une exception
  @medium
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


  # Medium car c'est ardu de simuler une exécution à une date donnée
  # j'utilise PatientRepository pour aller chercher un patient qui existe;
  # j'exécute une prescription valide pour le patient(domaine) le nombre de fois demandé;
  # je vérifie que le renewals de la prescription a baissé ou que l'exécution a tiré une exception
  @medium
  Plan du scénario: Celui où l'ordonnance est expirée
    Étant donné une patiente Alice
    Et une ordonnance d'acétaminophène au dossier de Alice expirant le '2016-12-12'
    Quand Alice demande à exécuter l'ordonnance d'acétaminophène le <date>
    Alors l'exécution est <resultat?>

    Exemples:
      | date       | resultat? |
      | 2016-12-11 | autorisée |
      | 2016-12-12 | autorisée |
      | 2016-12-13 | refusée   |