# language: fr
Fonctionnalité: Ajouter une ordonnance

Afin de permettre aux patients d'obtenir des ordonnances de médicaments
En tant que patient
Je veux pouvoir verser l'ordonnance à mon dossier à la pharmacie

Règles d'affaires
- L'ordonnance est conservée au dossier si elle est valide

# Large car montrer que le happy path fonctionne de bout en bout est important
# j'exécute un appel rest vers la route existente avec une ordonnance valide;
# je vérifie le code de retour
@large
Scénario: Celui où l'ordonnance est valide
  Étant donné une ordonnance valide pour Alice
  Quand Alice ajoute l'ordonnance à son dossier
  Alors l'ordonnance est ajoutée à son dossier
  Et une confirmation lui est signalée

# Large car on veut vérifier qu'une erreur fonctionne bien
# j'exécute un appel rest vers la route existente avec une ordonnance invalide;
# je vérifie le code de retour
@large
Scénario: Celui où l'ordonnance est invalide
  Étant donné une ordonnance invalide pour Alice
  Quand Alice ajoute l'ordonnance à son dossier
  Alors l'ordonnance n'est pas ajoutée à son dossier
  Et une erreur lui est signalée