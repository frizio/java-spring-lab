---
name: check-java-project
description: Controlla lo stato Git e la versione Java dichiarata nel POM di un progetto Maven.
---

Lavora in sola lettura.

1. Esegui `git --no-optional-locks status --short`.
2. Cerca nel `pom.xml` la versione Java dichiarata.
3. Sotto il titolo "Scheda Java del progetto", riporta lo stato Git,
   la versione con file e numero di riga e i comandi eseguiti.
4. Se la versione non è dichiarata nel POM, segnalalo.
