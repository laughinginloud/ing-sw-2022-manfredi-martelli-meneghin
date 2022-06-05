package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.termutils.Ansi;
import it.polimi.ingsw.common.termutils.Key;
import it.polimi.ingsw.common.termutils.TermConstants;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.viewRecord.GameRules;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Display;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import static it.polimi.ingsw.common.termutils.Ansi.*;

//TODO: gestire jline.UserInterruptException come chiamata a close

public final class ViewCLI implements View {

    // region Terminal related fields

    private final Attributes  savedAttributes;
    private final Terminal    terminal;
    private final Display     display;
    private final PrintWriter writer;
    private final LineReader  reader;
    private final InputStream keyStream;

    private Address address;

    private VirtualController virtualController;
    private GameModel model;

    // endregion

    // region Constructors

    public ViewCLI() throws IOException {
        terminal = TerminalBuilder.builder()
            .name("Eriantys")
            .encoding("UTF-8")
            .nativeSignals(true)
            .type("screen")
            .jna(true)
            .build();

        display = new Display(terminal, true);
        display.resize(terminal.getHeight(), terminal.getWidth());

        reader = LineReaderBuilder.builder().terminal(terminal).build();
        writer = terminal.writer();

        savedAttributes = terminal.enterRawMode();
        terminal.puts(InfoCmp.Capability.enter_ca_mode);

        keyStream = terminal.input();
    }

    public ViewCLI(Terminal terminal, Display display, InputStream keyStream, Attributes savedAttributes) {
        this.terminal        = terminal;
        this.display         = display;
        this.keyStream       = keyStream;
        this.savedAttributes = savedAttributes;

        reader = LineReaderBuilder.builder().terminal(terminal).build();
        writer = terminal.writer();
    }

    // endregion

    @Override
    public void launchUI() {
        try {
            askAddress();
            new VirtualController(address, this);
        }

        // Should never happen
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playExitMenu() {
        reader.readLine();
    }

    @Override
    public void signalConnectionError() {
        display.clear();

    }

    @Override
    public void updateModel(GameModel model, Set<GameValues> updatedValues) {

    }

    public void close() {
        terminal.setAttributes(savedAttributes);
        terminal.puts(InfoCmp.Capability.exit_ca_mode);

        try {
            terminal.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askAddress() {
        try {
            // Both tuples contain a string representing a candidate for the value
            // and a bool representing whether that value is acceptable
            Tuple<String, Boolean> ip   = new Tuple<>(null, false);
            Tuple<String, Boolean> port = new Tuple<>(null, false);

            do {
                //Update the console with the logo
                display.clear();
                display.updateAnsi(TermConstants.logoList, (terminal.getWidth() + 1) * TermConstants.logoList.size());

                // Print the request for the IP
                // NB: the last space is used to solve a bug, because otherwise backspace would delete the whole line
                writer.println("Welcome to the magical world of Eriantys!");
                writer.println();
                writer.print("Please enter the IP address of the server you're trying to connect to:");
                writer.print(" ");

                // Check if there is an illegal candidate for the value
                if (ip.right()) {
                    // Print the candidate on a red background with an error message
                    writer.print(colorString(ip.left() + " is not a valid IP address", Ansi.BACKGROUND_RED));

                    // Reset the tuple
                    ip = new Tuple<>(null, false);

                    // Wait for a generic key, hiding the cursor
                    hideCursor(writer);
                    keyStream.read();
                    showCursor(writer);

                    continue;
                }

                // Otherwise, check if there is not a candidate
                else if (ip.left() == null) {
                    // Read an IP, sanitizing it in the process as the unsatized version is currently useless
                    String readIP = Address.sanitizeIP(reader.readLine());

                    // If the IP is incorrect, add it to the tuple and print again the menu, to highlight it with an error
                    if (!Address.checkIP(readIP)) {
                        ip = new Tuple<>(readIP, true);
                        continue;
                    }

                    // Otherwise, the candidate is correct, so update the tuple accordingly
                    ip = new Tuple<>(readIP, false);
                    moveCursor(writer, Ansi.Direction.UP, 5);
                    continue;
                }

                // Otherwise, there already is a correct candidate, so print it
                else
                    writer.println(colorString(ip.left(), Ansi.GREEN));

                // Print the request for the port number
                // NB: the last space is used to solve a bug, because otherwise backspace would delete the whole line
                writer.println();
                writer.print("Please enter the server port:");
                writer.print(" ");

                // Check if there is an illegal candidate for the value
                if (port.right()) {
                    // Print the candidate on a red background with an error message
                    writer.print(colorString(port.left() + " is not a valid port number", Ansi.BACKGROUND_RED));

                    // Reset the tuple
                    port = new Tuple<>(null, false);

                    // Wait for a generic key, hiding the cursor
                    hideCursor(writer);
                    keyStream.read();
                    showCursor(writer);

                    continue;
                }

                // Otherwise, read the port and try to parse it
                String readPort = reader.readLine();
                try {
                    // Filter for well known ports, that will not be accepted
                    if (Address.checkPort(port.left())){
                        address = new Address(ip.left(), Address.parsePort(port.left()));
                        return;
                    }
                }

                // If the port cannot be parsed, simply ignore the exception
                catch (NumberFormatException ignored) {}

                // The port number is illegal because of parsing or for beign well known, so update the tuple accordingly
                port = new Tuple<>(readPort, true);
            } while (true);
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings({"UnnecessaryLabelOnContinueStatement", "UnnecessaryContinue"})
    public void askRules() {
        try {
            Integer numOfPlayers = null;
            Boolean expertMode   = null;

            int numSel = 0;
            int expSel = 0;

            int okSel = 0;

            hideCursor(writer);

            // Main menu
            MENU:
            while (true) {
                List<String> menu = new ArrayList<>(TermConstants.logoList);
                menu.add("");
                menu.add("Please select the rules of the game");
                menu.add("");
                menu.add("How many players will the game have?");

                List<String> numOptions = new ArrayList<>(3);
                numOptions.add("> 2");
                numOptions.add("> 3");
                numOptions.add("> 4");

                numOptions.set(numSel, Ansi.colorString(numOptions.get(numSel), CYAN));
                menu.addAll(numOptions);

                // If no players have been selected, print the selection
                if (numOfPlayers == null) {
                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (true) {
                        // If no keys have been pressed just wait for them
                        if (keyStream.available() == 0)
                            continue KEYPRESS;

                        // Interpret the availale key
                        switch (Key.parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                numSel = (numSel + 1) % 3;
                                continue MENU;
                            }

                            // Up arrow or left arrow: go to the previous menu item
                            case UP_ARROW, LEFT_ARROW -> {
                                numSel = (numSel + 2) % 3;
                                continue MENU;
                            }

                            // Number two: jump to two players
                            case TWO -> {
                                numSel = 0;
                                continue MENU;
                            }

                            // Number three: jump to three players
                            case THREE -> {
                                numSel = 1;
                                continue MENU;
                            }

                            // Number three: jump to four players
                            case FOUR -> {
                                numSel = 2;
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                numOfPlayers = numSel + 2;
                                continue MENU;
                            }

                            // Otherwise, just read the next keypress
                            default -> {
                                continue KEYPRESS;
                            }
                        }
                    }
                }

                menu.add("");
                menu.add("Will the game be played with the expert rules?");

                List<String> expOptions = new ArrayList<>(2);
                expOptions.add("> Yes");
                expOptions.add("> No");

                expOptions.set(expSel, Ansi.colorString(expOptions.get(expSel), CYAN));

                menu.addAll(expOptions);

                if (expertMode == null) {
                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (true) {
                        // If no keys have been pressed just wait for them
                        if (keyStream.available() == 0)
                            continue KEYPRESS;

                        // Interpret the availale key
                        switch (Key.parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                                expSel = (expSel + 1) % 2;
                                continue MENU;
                            }

                            // Number one or Y key: jump to yes
                            case ONE, Y -> {
                                expSel = 0;
                                continue MENU;
                            }

                            // Number two or N key: jump to no
                            case TWO, N -> {
                                expSel = 1;
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                expertMode = expSel == 1;
                                continue MENU;
                            }

                            // Otherwise, just read the next keypress
                            default -> {
                                continue KEYPRESS;
                            }
                        }
                    }
                }

                menu.clear();
                menu.addAll(TermConstants.logoList);
                menu.add("");
                menu.add("Selected options:");
                menu.add("Number of players: " + numOfPlayers);
                menu.add("Expert rules: " + (expertMode ? "Yes" : "No"));
                menu.add("");
                menu.add("Is this okay?");

                if (okSel == 0) {
                    menu.add(Ansi.colorString("> Yes", CYAN));
                    menu.add("> No");
                }

                else {
                    menu.add("> Yes");
                    menu.add(Ansi.colorString("> No", CYAN));
                }

                display.clear();
                display.updateAnsi(menu, 0);

                // Key pressing loop
                KEYPRESS:
                while (true) {
                    // If no keys have been pressed just wait for them
                    if (keyStream.available() == 0)
                        continue KEYPRESS;

                    // Interpret the availale key
                    switch (Key.parseKey(keyStream)) {
                        // Tab or an arrow: go to next menu item (which is also the previous, since there are only two)
                        case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                            okSel = (okSel + 1) % 2;
                            continue MENU;
                        }

                        // Number one or Y key: jump to yes
                        case ONE, Y -> {
                            okSel = 0;
                            continue MENU;
                        }

                        // Number two or N key: jump to no
                        case TWO, N -> {
                            okSel = 1;
                            continue MENU;
                        }

                        // Return: select the item
                        case ENTER -> {
                            switch (okSel) {
                                // Selected yes, so forward the rules
                                case 0 -> {
                                    forwardViewToVirtualController(new GameRules(numOfPlayers, expertMode));
                                    showCursor(writer);
                                    return;
                                }

                                // Selected no, so reset all options to the default
                                case 1 -> {
                                    numSel = 0;
                                    numOfPlayers = null;

                                    expSel = 0;
                                    expertMode = null;

                                    okSel = 0;

                                    continue MENU;
                                }
                            }
                        }

                        // Otherwise, just read the next keypress
                        default -> {
                            continue KEYPRESS;
                        }
                    }
                }
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void askReloadGame() {

    }

    public void askEndOfTurn() {

    }

    public void requestUsernameAndMagicAge(Set<String> forbiddenUsernames) {

    }

    public void requestWizard(Wizard[] availableWizards) {

    }

    public void requestPlayAssistantCard(AssistantCard[] assistantCards) {

    }

    public void requestPlayCharacterCard(CharacterCard[] playableCharacterCards) {

    }

    public void requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards) {

    }

    public void requestStudentEntranceMovement(int selectedStudentIndex, Boolean[] diningRoomFreeTables) {

    }

    public void requestMotherNatureMovement(Island[] possibleMovement) {

    }

    public void requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards) {

    }

    public void requestCloudTileSelection(CloudTile[] availableClouds) {

    }

    public void requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards) {

    }

    public void requestHowManyStudentsToMove(int maxNumOfStudentMovable) {

    }

    public void requestChooseColor(Color[] availableColors) {

    }

    public void chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent) {

    }

    public void chooseStudentFromEntrance(Color[] availableColors) {

    }

    public void requestChooseIsland(Island[] availableIslands) {

    }

    public void requestChooseDiningRoom(Color[] compatibleDiningRoomTable) {

    }

    public void notifyGameInProgress() {

    }

    public void notifyGameStart() {

    }

    public void notifyPlayerDisconnection() {

    }

    public void notifyStartGameTurn(String playingPlayerUsername) {

    }

    public void notifyEndOfTurn() {

    }

    @Override
    public void forwardViewToVirtualController(Object infoToSend) {
        virtualController.messageAfterUserInteraction(infoToSend);
    }

    @Override
    public void setUsernameVirtualController(String readUsername) {
        virtualController.setUsername(readUsername);
    }

    @Override
    public void setUsernameVirtualController(String readUsername) {

    }

    public void setVirtualController(VirtualController virtualController) {

    }

    public void signalWinner(Player winner) {

    }

    @Override
    public GameModel getModel() {
        return model;
    }

    @Override
    public Player getLocalPlayer() {
        return Arrays.stream(model.getPlayer()).reduce((p1, p2) -> p1.getUsername().equals(virtualController.getUsername()) ? p1 : p2).get();
    }

    @Override
    public void setModel(GameModel model) {
        this.model = model;
    }

    @Override
    public void setVirtualController(VirtualController virtualController) {
        this.virtualController = virtualController;
    }
}
