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
* [Installation](#Installation)
* [Compiling](#Compiling)
* [Running](#Running)
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

## Installation

### System requirements

* Windows OS, macOS or Linux with an ANSI-capable terminal emulator
* Java SE JDK 18 ([OracleJDK][oracleJDK18-link] or [OpenJDK][openJDK18-link])
* [Maven][maven-link] framework version 3.0 (or newer)

*WARNING* :warning: : A screen resolution of 1920x1080 or more is required to render, see and play the GUI
properly. If these requirements are not met, we cannot ensure that the game will behave as designed.

### Windows

#### Installing Terminal

If using Windows 10 or newer, the recommended terminal emulator is "Windows Terminal",
obtainable by using [this link][windowsTerminal-link].

#### Installing Java

1. Download the compressed archive from [Oracle][oracleJDK18JARwin64-link] or [OpenJDK][openJDK18JARwin64-link];
2. Extract content from the zip folder to your preferred location 
   (we recommend [7Zip][7Zip-link] to uncompress your files, but any un-compression software will do);
3. Then go to `START -> Edit the system environment variables`;
4. In the `USER` section select the `Path` variable and click `Edit`;
5. Select `New` and type `C:\Users\<your-user>\<path-to-extracted-folder-jdk>\bin` then save and exit;
6. Open Windows Terminal (also named just "Terminal") and verify your java version by typing the following command:
```shell
java -version
```
7. If the version is the 18.0.1 or later, the installation has been successful

If something went wrong with the installation try again this or another method at [this link][javaguide-link].

#### Installing Maven

1. Download a compressed archive from the [Apache Maven Project][maven-link] website and 
   select a version to download (remember for this project Maven 3.0 or later is needed)
2. Extract content from the zip folder to your preferred location 
   (we recommend [7Zip][7Zip-link] to uncompress your files, but any un-compression software will do);
3. Then go to `START -> Edit the system environment variables`;
4. In the `USER` section select the `Path` variable and click `Edit`;
5. Select `New` and type `C:\Users\<your-user>\<path-to-extracted-folder-maven>\bin` then save and exit;
6. Open Windows Terminal (also named just "Terminal") and verify your Maven version by typing the following command:
```shell
mvn --version
```
7. If the version is the 3 or later, the installation has been successful

If something went wrong with the installation try again this or another method at [this link][mvnguide-link].

### macOS

#### Installing Terminal

The default terminal emulator will run the application, but the recommendation is [iTerm2](https://iterm2.com/), obtainable
directly from its website or by using the [Homebrew](https://brew.sh/) command:
```shell
brew install --cask iterm2
```

#### Installing Java

1. Download the Java installer (`.dmg`) from [Oracle][oracleJDK18-link] 
   the correct version for the system in use:
   * [**macOS x64 systems**][oracleJDK18JARmac64-link]
   * [**macOS aarch64 (64-bit ARM)**][oracleJDK18JARmacM1-link]
2. From either the browser **downloads** window or from the file browser, double-click the `.dmg` file to start it.
   A **Finder** window appears that contains an icon of an open box and the name of the `.pkg` file.
3. Click `Continue`, the installation window will appear.
4. Click `Install`, a window appears that displays the message: 
   `Installer is trying to install new software. Enter your password to allow this.`
5. Enter the Administrator username and password and click `Install Software`.
   The software is installed and a confirmation window is displayed.
6. Open your terminal of choice and verify your Java version by typing the following command:
```shell
java -version
```
7. If the version is the 18.0.1 or later, the installation has been successful

After the software is installed, you can delete the `.dmg` file if you want to save disk space.

#### Installing Maven

1. Install [Homebrew](https://brew.sh/) (if not already installed)
2. Install Maven by using this Homebrew formula:
```shell
brew install maven
```
3. Open your terminal of choice and verify your Maven version by typing the following command:
```shell
mvn -version
```
4. If the version is the 3 or later, the installation has been successful!

### Linux

If you want to download Maven and JavaJDK with your package manager be sure to fulfill 
the system requirements otherwise follow the next steps.

#### Installing Terminal

The recommended choice is GNOME Terminal, obtainable from the distribution's package manager

#### Installing Java

1. Download JavaJDK based on your distro [here][oracleJDK18-link];
2. Navigate to your preferred install location and extract the .tar.gz archive file using:
```shell
tar zxvf jdk-18.<version-number>-x64_bin.tar.gz
```
3. Now let's set the `PATH` variable by typing the following code:
```shell
   cd $HOME
   nano .bashrc
```
4. Add the following line to the end of `.bashrc`:
```shell
export PATH=/<path-to-extracted-folder/bin:$PATH
```
5. Verify your Java version by typing the following command:
```shell
java -version
```
6. If the version is the 18.0.1 or later, the installation has been successful

If something went wrong with the installation try again this or another method at [this link][javalinux-link].

#### Installing Maven

1. Download Maven based on your distro [here][maven-link]
2. Extract it with:
```shell
tar zxvf maven.<version-number>-x64_bin.tar.gz
```
3. Set `PATH` variable by appending this command to `.bashrc` as previous steps:
```shell
cd $HOME 
nano .bashrc
export PATH=/<path-to-extracted-folder/bin:$PATH
```
4. Open your terminal of choice and verify your Maven version by typing the following command:
```shell
mvn -v
```
or
```shell
mvn --version
```
5. If the version is the 3 or later, the installation has been successful

## Compiling

1. After downloading and installing the required software, clone this repo by either downloading the `.zip` and extracting it,
   or using `git clone`.
2. Open your terminal of choice, navigate to the folder and compile sources of the package by typing:
```shell
mvn clean package
```
3. A new folder called `/target` will be created in the project home directory, 
   inside it, you will find the `.jar` file, which already includes the project dependencies.
4. Verify this by navigating to the folder and displaying its contents by typing:
```shell
cd /target
cd ls
```
5. If you see a file named `PSP27-Eriantys-1.0.jar` the compilation has been successful

## Running

1. Once you met all the requirements, open a terminal and go to the project target directory 
   (which has to be previously built with maven, see the [compiling](#Compiling) section if you skipped 
   this step, alternatively the already compiled `PSP27-Eriantys-1.0.jar` in the [`deliverables/jar` folder][jar-link] can be used).
2. Once there, execute this command:
```shell
java -jar PSP27-Eriantys-1.0.jar
```
*WARNING* :warning: : the file `PSP27-Eriantys-1.0.jar` must match the one present in your directory,
if it doesn't, execute the command by substituting the command above with the correct file name

3. A CLI will appear, and you'll be able to choose an option among the present by using:
   * **Windows** <kbd>&uarr;</kbd> <kbd>&darr;</kbd> or <kbd>Tab</kbd>
   * **Unix** <kbd>Tab</kbd>
4. The options are:
   * Server
   * Client CLI
   * Client GUI
   * Exit
5. In order to play you'll need at least:
   * One server
   * Two clients (either CLI or GUI)

*INFO* :information_source: : the CLI menu to choose an option can be skipped by specifying the
desired instance of the program as a parameter of the command above. These are the available options:
```shell
java -jar PSP27-Eriantys-1.0.jar server
java -jar PSP27-Eriantys-1.0.jar server ${port number}
java -jar PSP27-Eriantys-1.0.jar client cli
java -jar PSP27-Eriantys-1.0.jar client gui
```
Where `${port number}` indicates a custom port number instead of the default one, which is 6556.
A couple of help commands are also present:
```shell
java -jar PSP27-Eriantys-1.0.jar help
java -jar PSP27-Eriantys-1.0.jar client help
```

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
[jar-link]: https://github.com/laughinginloud/ing-sw-2022-manfredi-martelli-meneghin/blob/main/deliverables/jar/PSP27-Eriantys-1.0.jar

[oracleJDK18-link]: https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html
[oracleJDK18JARwin64-link]: https://download.oracle.com/java/18/archive/jdk-18.0.1_windows-x64_bin.zip
[oracleJDK18JARmac64-link]: https://download.oracle.com/java/18/archive/jdk-18.0.1_macos-x64_bin.dmg
[oracleJDK18JARmacM1-link]: https://download.oracle.com/java/18/archive/jdk-18.0.1_macos-aarch64_bin.dmg
[openJDK18-link]: https://jdk.java.net/18/
[openJDK18JARWin64-link]: https://download.java.net/java/GA/jdk18.0.1.1/65ae32619e2f40f3a9af3af1851d6e19/2/GPL/openjdk-18.0.1.1_windows-x64_bin.zip
[maven-link]: https://maven.apache.org/
[windowsTerminal-link]: https://www.microsoft.com/store/productId/9N0DX20HK701
[7Zip-link]: https://www.7-zip.org/
[javaguide-link]: https://docs.oracle.com/en/java/javase/18/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA
[maven-link]: https://maven.apache.org/download.cgi
[mvnguide-link]: https://maven.apache.org/install.html
[javalinux-link]: https://computingforgeeks.com/install-oracle-java-18-on-ubuntu-debian/
