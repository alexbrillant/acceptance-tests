# TP GLO4002 2016 - Stories

## Vacabulaire du domaine

-   ordonnance (en: prescription) = un "papier" donné par un professionnel de la santé visant à obtenir un ou des médicaments (dans ce domaine).
-   répétitions (en: renewal) = Nombre de fois qu'une personne peut demander ce médicament (ex.: 1 fois ou 2 fois). C'est le nombre *total* d'exécutions incluant la première fois.

## Stories

```gherkin
# language: fr
Fonctionnalité: Ajouter une ordonnance

Afin de permettre aux patients d'obtenir des ordonnances de médicaments
En tant que patient
Je veux pouvoir verser l'ordonnance à mon dossier à la pharmacie

Règles d'affaires
- L'ordonnance est conservée au dossier si elle est valide

Scénario: Celui où l'ordonnance est valide
  Étant donné une ordonnance valide pour Alice
  Quand Alice ajoute l'ordonnance à son dossier
  Alors l'ordonnance est ajoutée à son dossier
  Et une confirmation lui est signalée

Scénario: Celui où l'ordonnance est invalide
  Étant donné une ordonnance invalide pour Alice
  Quand Alice ajoute l'ordonnance à son dossier
  Alors l'ordonnance n'est pas ajoutée à son dossier
  Et une erreur lui est signalée
```

```gherkin
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
      | 1         | autorisée  |
      | 2         | autorisée  |
      | 3         | refusée    |


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
	
```

## API

Voir la documentation de l'API: http://docs.medglo400216.apiary.io/