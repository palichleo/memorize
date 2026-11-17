# Memorize

Preuve de concept d'un gestionnaire de mots de passe de bureau en Java (JavaFX), mon premier
projet dans ce langage (2023). L'idée à démontrer : chiffrer un mot de passe en AES-256 sans stocker la
clé dans un fichier dédié, en la cachant dans un fichier ordinaire de n'importe quel type, choisi
par l'utilisateur. Cette partie fonctionne.

## Ce qui fonctionne

- Inscription en trois étapes : choix du mot de passe maître, choix d'un fichier porteur, choix du
  dossier où ranger ce fichier.
- Une clé AES de 256 bits est générée, puis ajoutée à la fin d'une copie du fichier porteur. Le mot
  de passe maître chiffré est enregistré dans `~/Memorize/pass.enc`.
- À la connexion, les 32 derniers octets du fichier porteur redonnent la clé, qui déchiffre le mot
  de passe maître. Si le fichier porteur a été déplacé, l'application demande où il se trouve.
- Les tableaux d'octets qui ont contenu la clé sont écrasés après usage.
- Un délai après chaque mot de passe erroné freine les essais en série.
- Interface JavaFX : fenêtres déplaçables, grille des entrées avec défilement animé,
  aperçu en direct de l'entrée en cours de saisie.

## État du dépôt

- La liste des mots de passe, où l'utilisateur ajoute ses entrées comme dans les gestionnaires
  courants, était prévue et n'a pas été terminée : `AddPassword.addPassword()` est vide et la
  grille affiche des valeurs d'exemple. La lecture chiffrée, elle, est écrite
  (`PathManager.getInformation`).

## Pistes

- `Cipher.getInstance("AES")` utilise le mode ECB, sans authentification ; AES-GCM avec une clé
  dérivée du mot de passe maître serait plus solide.
- La clé est ajoutée en clair à la fin du fichier : c'est une dissimulation plutôt qu'une
  stéganographie au sens strict.
- Pas de tests automatisés.

## Lancer

```sh
./mvnw clean javafx:run
```
