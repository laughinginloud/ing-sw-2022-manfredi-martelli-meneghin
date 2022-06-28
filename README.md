# Eriantys Board Game
[![Build and test](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/actions/workflows/mavenBuildTest.yml/badge.svg?branch=main)](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/actions/workflows/mavenBuildTest.yml)

<img src="https://www.craniocreations.it/wp-content/uploads/2021/06/Eriantys_scatola3Dombra-600x600.png" />

Eriantys Board Game is the final project of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2021/2022).

**Teacher** Pierluigi Sanpietro

## Project specification
The project consists of a Java version of the board game *Eriantys*, made by Cranio Creations.

You can find the full game [here](https://www.craniocreations.it/prodotto/eriantys/).

The final version includes:
* initial UML diagram: a first and an updated version;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.

## Implemented Functionalities
| Functionality     |                                                                     Status                                                                     |
|:------------------|:----------------------------------------------------------------------------------------------------------------------------------------------:|
| Basic rules       |       [✅](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/common/model)       |
| Complete rules    |       [✅](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/common/model)       |
| Socket            |    [✅](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/virtualView)    |
| CLI               | [✅](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/client/view/ViewCLI.java) |
| GUI               |     [✅](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/client/view/gui)      |

| Advanced Functionality |                                                                              Status                                                                              |
|:-----------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| Character card         | [✅](https://github.com/github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/controller/characterCard) |
| 4 players game         |                       [✅](github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server)                       |
| Persistence            |               [✅](github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/controller/save)               |
| Multiple games         |                                                                              [⛔]()                                                                               |
| Resilience             |                                                                              [⛔]()                                                                               |

#### Legend
[⛔]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[⚠️]() Implementing&nbsp;&nbsp;&nbsp;&nbsp;[✅]() Implemented


<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

## Test cases
The model package has a classes' coverage at 95% (22/23).

**Coverage criteria: code lines.**

| Package    | Tested Class           |   Coverage    |
|:-----------|:-----------------------|:-------------:|
| Model      | Global Package         | 250/288 (86%) |

## The Team
* [Giovanni Manfredi](https://github.com/Silemo)
* [Mattia Martelli](https://github.com/laughinginloud)
* [Sebastiano Meneghin](https://github.com/TheLastSoldier)

## Software used
**Intellij IDEA Ultimate** - main IDE

**GitKraken & GitHub Desktop** - github

**Scenebuilder** - GUI

**Notion** - organisation and brainstorming

**OneDrive** - file sharing

**Astah UML** - UML and sequence diagrams

**TeX Studio** - documentation