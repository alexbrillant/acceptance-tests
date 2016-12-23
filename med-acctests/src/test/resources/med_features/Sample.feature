# language: fr
Fonctionnalité: Ajouter une ordonnance

Afin de permettre aux patients d'obtenir des ordonnances de médicaments
En tant que patient
Je veux pouvoir verser l'ordonnance à mon dossier à la pharmacie

Règles d'affaires
- L'ordonnance est conservée au dossier si elle est valide

@large
Scénario: Celui où l'ordonnance est valide
  Étant donné une ordonnance valide pour Alice
  Quand Alice ajoute l'ordonnance à son dossier
  Alors l'ordonnance est ajoutée à son dossier
  Et une confirmation lui est signalée

@medium
Scénario: Celui où l'ordonnance est invalide
  Étant donné une ordonnance invalide pour Alice
  Quand Alice ajoute l'ordonnance à son dossier
  Alors l'ordonnance n'est pas ajoutée à son dossier
  Et une erreur lui est signalée