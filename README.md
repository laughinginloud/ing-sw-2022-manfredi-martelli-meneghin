# Eriantys Board Game

## About

<img src="https://www.craniocreations.it/wp-content/uploads/2021/06/Eriantys_scatola3Dombra-600x600.png" width=192px height=192px align="right"/>

Eriantys Board Game is the final project of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2021/2022).

**Teacher** Pierluigi Sanpietro

### The Team

* [Giovanni Manfredi](https://github.com/Silemo)
* [Mattia Martelli](https://github.com/laughinginloud)
* [Sebastiano Meneghin](https://github.com/TheLastSoldier)

## Table of Contents

* [Specifications](#Specifications)
* [Instructions](#Instructions)
* [JavaDocs](javadocs-link)
* [Find out more](#Find out more)


## Specifications

The project consists of a Java version of the board game *Eriantys*, made by Cranio Creations.

You can find the full game [here](https://www.craniocreations.it/prodotto/eriantys/).

The final version includes:
* initial UML diagram: [a first](firstUML-link) and an [updated version](firstUML_updated-link);
* [final UML diagram](finalUML-link), generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the [implementation](main-link);
* source code of [unity tests](tests-link).

### Implemented Functionalities
<table>
<tr><td>

| Functionality                |              Status               |
|:-----------------------------|:---------------------------------:|
| [Basic rules](rules-link)    | [:white_check_mark:](rules-link)  |
| [Complete rules](rules-link) | [:white_check_mark:](rules-link)  |
| [Socket](socket-link)        | [:white_check_mark:](socket-link) |
| [CLI](cli-link)              |  [:white_check_mark:](cli-link)   |
| [GUI](gui-link)              |  [:white_check_mark:](gui-link)   |

</td><td>

| Advanced Functionality               |                  Status                  |
|:-------------------------------------|:----------------------------------------:|
| [Character card](characterCard-link) | [:white_check_mark:](characterCard-link) |
| [4 players game](server-link)        |    [:white_check_mark:](server-link)     |
| [Persistence](save-link)             |     [:white_check_mark:](save-link)      |
| Multiple games                       |                :no_entry:                |
| Resilience                           |                :no_entry:                |
</td></tr>

</table>

##### Legend
:no_entry: Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;:warning: Implementing&nbsp;&nbsp;&nbsp;&nbsp;:white_check_mark: Implemented

<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

### Test cases
The model package has a classes' coverage at 95% (22/23).

**Coverage criteria: code lines.**

| Package    | Tested Class           |   Coverage    |
|:-----------|:-----------------------|:-------------:|
| Model      | Global Package         | 250/288 (86%) |

## Instructions

### Installation

#### System requirements

* Windows OS, macOS or Linux with an active terminal
* JAVA SE JDK 18 ([OracleJDK](oracleJDK18-link) or [OpenJDK](openJDK18-link))
* [Maven](maven-link) framework version 3.0 (or newer)

*WARNING* :warning: : A screen resolution of 1920x1080 (100% DPI) or more is required to render, see and play the GUI
properly. If these requirements are not met, we cannot ensure that the game will behave as designed.

#### Windows

##### Installing Terminal

If the version in use is Windows 11, use the default terminal "Windows Terminal", 
otherwise install it by using [this link](windowsTerminal-link).

##### Installing JAVA

Download the `.jar` file from [Oracle](oracleJDK18-link) or [OpenJDK](openJDK18-link)


#### macOS

#### Linux

### Compiling


## Find out more

### Software used

**Intellij IDEA Ultimate** - main IDE

**GitKraken & GitHub Desktop** - github

**Scenebuilder** - GUI

**Notion** - organisation and brainstorming

**OneDrive** - file sharing

**Astah UML** - UML and sequence diagrams

**TeX Studio** - documentation

<!--Links of the document-->

[javadocs-link]: https:///laughinginloud.github.io/eriantys-javadocs/

[firstUML-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/deliverables/UML/initial_uml.png
[firstUML_updated-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/deliverables/UML/initialUML_updated.pdf
[finalUML-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/deliverables
[main-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main
[tests-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/test/java/it/polimi/ingsw/common/model

[rules-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/common/model
[socket-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/virtualView
[cli-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/client/view/ViewCLI.java
[gui-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/client/view/gui
[characterCard-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/controller/characterCard
[server-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server
[save-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/tree/main/src/main/java/it/polimi/ingsw/server/controller/save

[oracleJDK18-link]: https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html
[openJDK18-link]: https://jdk.java.net/18/
[maven-link]: https://maven.apache.org/
[windowsTerminal-link]: https://www.microsoft.com/store/productId/9N0DX20HK701

[installation-link]: ##Installation
[compiling-link]: ##Compiling
[running-link]: ##Running
[troubleshooting-link]: ##Troubleshooting
[javadocs]: https://s0nn1.github.io/santorini-javadocs/

[installation-image]: github/Artboard%201.png
[compiling-image]: github/Artboard%203.png
[running-image]: github/Artboard%204.png
[javadocs-image]: github/Artboard%202.png
[troubleshooting-image]: github/Artboard%205.png