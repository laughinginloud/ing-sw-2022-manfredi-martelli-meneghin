# Eriantys Board Game

[![Build and test](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/actions/workflows/mavenBuildTest.yml/badge.svg?branch=main)](https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/actions/workflows/mavenBuildTest.yml)

## About

<img src="https://www.craniocreations.it/wp-content/uploads/2021/06/Eriantys_scatola3Dombra-600x600.png" width=300px height=300px align="right"/>

Eriantys Board Game is the final project of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2021/2022).

**Professor** Pierluigi San Pietro

**Lab professor** Daniele Cattaneo</br>
**Lab professor** Chiara Criscuolo

**Tutor** Federica Suriano</br>
**Tutor** Lorenzo Italiano

### The Team

* [Giovanni Manfredi](https://github.com/Silemo)
* [Mattia Martelli](https://github.com/laughinginloud)
* [Sebastiano Meneghin](https://github.com/TheLastSoldier)

## Table of Contents

* [Specifications](#Specifications)
* [Before Playing](#Before-playing)
* [Javadocs](#Javadocs)
* [More](#More)


## Specifications

The project consists of a Java version of the board game *Eriantys*, made by Cranio Creations.

The full game can be found [here](https://www.craniocreations.it/prodotto/eriantys/).

The final version includes:
* initial UML diagram: [a first][firstUML-link] and an [updated version][firstUML_updated-link];
* [final UML diagram][finalUML-link], generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* [network protocol documentation][protocolDoc-link] of the application
* source code of the [implementation][main-link];
* source code of [unity tests][tests-link].
* received peer-reviews of our [UML][UMLpeer-link] and [network protocol][protocolDocPeer-link]
* sent peer-reviews of [UML][UMLSentPeer-link] and [network protocol][protocolDocSentPeer-link] of other students

### Implemented Functionalities
<table>
<tr><td>

| Functionality                |              Status               |
|:-----------------------------|:---------------------------------:|
| [Basic rules][rules-link]    | [:white_check_mark:][rules-link]  |
| [Complete rules][rules-link] | [:white_check_mark:][rules-link]  |
| [Socket][socket-link]        | [:white_check_mark:][socket-link] |
| [CLI][cli-link]              |  [:white_check_mark:][cli-link]   |
| [GUI][gui-link]              |  [:white_check_mark:][gui-link]   |

</td><td>

| Advanced Functionality               |                  Status                  |
|:-------------------------------------|:----------------------------------------:|
| [Character card][characterCard-link] | [:white_check_mark:][characterCard-link] |
| [4 players game][server-link]        |    [:white_check_mark:][server-link]     |
| [Persistence][save-link]             |     [:white_check_mark:][save-link]      |
| Multiple games                       |                :no_entry:                |
| Resilience                           |                :no_entry:                |

</td></tr>
</table>

##### Legend
:no_entry: Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;:warning: Implementing&nbsp;&nbsp;&nbsp;&nbsp;:white_check_mark: Implemented

### Test cases
The model package has a classes' coverage at 95% (22/23).

**Coverage criteria: code lines.**

| Package    | Tested Class           |   Coverage    |
|:-----------|:-----------------------|:-------------:|
| Model      | Global Package         | 250/288 (86%) |


## Before playing

| **[Installation][install-link]**   | **[Compiling][compile-link]**      | **[Running][run-link]**    | **[Javadocs][javadocs-link]**        |
|------------------------------------|------------------------------------|----------------------------|--------------------------------------|
| [![i1][install-img]][install-link] | [![i2][compile-img]][compile-link] | [![i4][run-img]][run-link] | [![i3][javadocs-img]][javadocs-link] |

## Javadocs

This project has a large number of methods and classes and can sometimes be difficult 
to understand the purpose of each one of them. For this reason, each method and class has a doc associated to it.

The complete collection of the Javadocs can't be published right now because the
project has yet to be evaluated. Nonetheless, it is possible to execute Maven locally
on the repo by using the command `mvn javadoc:javadoc` to generate a few html files containing 
all the Javadocs of the project.

## More

### IntelliJ Options

During the development of the project we have found necessary to automate the execution
of the application. To do so we created a few `.xml` files that IntelliJ reads as running options (these files
are all contained in the `/.run` folder).
They are still in the project to show our methods and how we worked during the project.

*WARNING* :warning: : these running options were used only internally for debugging and are not 
intended for playing the game, so if you use these options we cannot ensure that the game will behave as designed.
These running tools were tested only on Windows based systems.

### Software used

**Intellij IDEA Ultimate** - main IDE

**GitKraken & GitHub Desktop** - github

**Scenebuilder** - GUI

**Notion** - organisation and brainstorming

**OneDrive** - file sharing

**Astah UML** - UML and sequence diagrams

**TeX Studio** - documentation

<!--Links of the document-->

[firstUML-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/deliverables/UML/initial_uml.png
[firstUML_updated-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/deliverables/UML/initialUML_updated.pdf
[finalUML-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/deliverables/UML%20-%20final
[protocolDoc-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/deliverables/protocolDocumentation/protocolDocumentation.pdf
[main-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main
[tests-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/test/java/it/polimi/ingsw/common/model
[UMLpeer-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/deliverables/UML%20-%20peerReview/received_peer_review_uml.pdf
[UMLSentPeer-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/deliverables/UML%20-%20peerReview/peer_review_uml.pdf
[protocolDocPeer-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/deliverables/protocolDocumentation%20-%20peerReview/received_peerReview2_protocolDocumentation.pdf
[protocolDocSentPeer-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/deliverables/protocolDocumentation%20-%20peerReview/peerReview2_ProtocolDocumentation.pdf

[rules-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/common/model
[socket-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/virtualView
[cli-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/client/view/ViewCLI.java
[gui-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/client/view/gui
[characterCard-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/controller/characterCard
[server-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server
[save-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/controller/save

[install-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/wiki/Installation
[compile-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/wiki/Compiling
[run-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/wiki/Running
[javadocs-link]: #Javadocs

[install-img]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/github/install.png
[compile-img]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/github/compile.png
[run-img]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/github/run.png
[javadocs-img]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/github/javadocs.png