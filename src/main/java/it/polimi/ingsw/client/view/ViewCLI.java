package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.termutils.Ansi;
import it.polimi.ingsw.common.utils.Methods;
import it.polimi.ingsw.common.utils.ModuloNat;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.viewRecord.GameRules;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge.UsernameResult;
import org.jline.reader.*;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Display;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static it.polimi.ingsw.common.termutils.Ansi.*;
import static it.polimi.ingsw.common.termutils.Ansi.Direction.*;
import static it.polimi.ingsw.common.termutils.Key.parseKey;
import static it.polimi.ingsw.common.termutils.Graphics.*;
import static it.polimi.ingsw.common.utils.Methods.*;
import static it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge.*;
import static it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge.sanitizeUsername;
import static java.lang.Thread.sleep;
import static java.util.Comparator.*;

/**
 * The CLI user interface
 * @author Mattia Martelli
 */

// A few warnings are suppressed for the whole class:
//
//      - UnnecessaryLabelOnContinueStatement,
//        UnnecessaryContinue,
//        UnnecessaryLabelOnBreakStatement: some nested loops are quite complicated, so in those cases all control flow instructions are labeled for clarity, even when not needed
//
//      - ResultOfMethodCallIgnored: to emulate Windows cmd's "Press any key to continue...", there are a few calls to InputStream::read where the result is discarded
//
@SuppressWarnings({"UnnecessaryLabelOnContinueStatement", "UnnecessaryContinue", "UnnecessaryLabelOnBreakStatement", "ResultOfMethodCallIgnored"})

public final class ViewCLI implements View {

    // region Terminal related fields

    private final Attributes  savedAttributes;
    private final Display     display;
    private final InputStream keyStream;
    private final LineReader  reader;
    private final Terminal    terminal;
    private final PrintWriter writer;

    // endregion

    // region Game related fields

    private Address           address;
    private Thread            currentMenu;
    private GameModel         model;
    private VirtualController virtualController;

    private int playerID;

    // endregion

    // region Constructors

    /**
     * Build a CLI from scratch
     * @throws IOException If the terminal cannot be built
     */
    public ViewCLI() throws IOException {
        terminal = TerminalBuilder.builder()
            .name("Eriantys")
            .encoding("UTF-8")
            .system(true)
            .nativeSignals(true)
            .jna(true)
            .build();

        display = new Display(terminal, true);
        display.resize(terminal.getHeight(), terminal.getWidth());

        reader = LineReaderBuilder.builder().terminal(terminal).build();
        writer = terminal.writer();

        savedAttributes = terminal.enterRawMode();
        terminal.puts(InfoCmp.Capability.enter_ca_mode);

        keyStream = terminal.input();

        playerID = -1;
    }

    /**
     * Build a CLI from a previously created terminal
     * @param terminal        The terminal
     * @param display         The display
     * @param keyStream       The input stream
     * @param savedAttributes The past terminal state
     */
    public ViewCLI(Terminal terminal, Display display, InputStream keyStream, Attributes savedAttributes) {
        this.terminal        = terminal;
        this.display         = display;
        this.keyStream       = keyStream;
        this.savedAttributes = savedAttributes;

        reader = LineReaderBuilder.builder().terminal(terminal).build();
        writer = terminal.writer();

        playerID = -1;
    }

    // endregion

    // region Execution related methods

    @Override
    public void launchUI() {
        try {
            run();
        }

        catch (UserInterruptException ignored) {
            close();
        }
    }

    /**
     * Run the CLI
     */
    private void run() {
        playExitMenu();

        ifNotNull(address, () -> {
            try {
                new VirtualController(address, this);
                virtualController.join();
            }

            catch (IOException | InterruptedException ignored) {
                signalConnectionError();
            }

            address = null;
            launchUI();
        });
    }

    /**
     * Terminate the CLI
     */
    public void close() {
        ifNotNull(virtualController, VirtualController::close);
        ifNotNull(currentMenu,       Thread::interrupt);

        terminal.setAttributes(savedAttributes);
        terminal.puts(InfoCmp.Capability.exit_ca_mode);

        try {
            terminal.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // endregion

    // region Utility methods

    /**
     * Context switch to a different "window"
     * @param window The "window" to switch to
     */
    private synchronized void contextSwitch(Runnable window) {
        ifNotNull(currentMenu, m -> {
            m.interrupt();

            reader.setVariable(LineReader.KILL_LINE,   Boolean.TRUE);
            reader.setVariable(LineReader.KILL_BUFFER, Boolean.TRUE);
        });


        currentMenu = new Thread(() -> {
            try {
                window.run();
            }

            catch (UserInterruptException ignored) {}
        });

        currentMenu.start();
    }

    /**
     * Context switch to a different "window"
     * @param window The "window" to switch to
     * @param param  The parameter of the "window"
     * @param <T>    The type of the parameter
     */
    private <T> void contextSwitch(Consumer<T> window, T param) {
        contextSwitch(() -> window.accept(param));
    }

    @Override
    public GameModel getModel() {
        return model;
    }

    @Override
    public void setModel(GameModel model) {
        this.model = model;
    }

    /**
     * Shows the game board for 1.5 seconds
     */
    private void showModel() {
        updateModel(model, null);

        try {
            sleep(1500);
        }

        catch (InterruptedException ignored) {}
    }

    @Override
    public void setVirtualController(VirtualController virtualController) {
        this.virtualController = virtualController;
    }

    @Override
    public Player getLocalPlayer() {
        if (playerID == -1)
            playerID = Arrays.stream(model.getPlayers())
                .reduce((p1, p2) -> p1.getUsername().equals(virtualController.getUsername()) ? p1 : p2)
                .orElseThrow()
                .getPlayerID();

        return model.getPlayer(playerID);
    }

    @Override
    public void forwardViewToVirtualController(Object infoToSend) {
        if (!interrupted())
            async(virtualController::messageAfterUserInteraction, infoToSend);
    }

    @Override
    public void setUsernameVirtualController(String readUsername) {
        virtualController.setUsername(readUsername);
    }

    /**
     * Color the selected element of the list in cyan
     * @param list  The list to modify
     * @param index The index of the selected item
     */
    private static void colorElem(List<String> list, int index) {
        highlightElem(list, index, CYAN);
    }

    /**
     * Underline the selected element of the list
     * @param list  The list to modify
     * @param index The index of the selected item
     */
    private static void underlineElem(List<String> list, int index) {
        highlightElem(list, index, UNDERLINE);
    }

    /**
     * Highlight the selected element of the list
     * @param list  The list to modify
     * @param index The index of the selected item
     * @param color The color(s) to apply to the element
     */
    private static void highlightElem(List<String> list, int index, Ansi... color) {
        list.set(index, (colorString(list.get(index), color)));
    }

    /**
     * Return the name of a character
     * @param card The card to elaborate
     * @return A String representing the character
     */
    private String characterName(CharacterCard card) {
        return switch (card.getCharacter()) {
            case STANDARD_BEARER -> "Herald";
            default              -> capitalize(card.getCharacter().name());
        };
    }

    /**
     * Print a simple confirm menu
     * @param header      The header of the menu
     * @param cursorShown Wheteher the cursor is currently shown
     * @return A boolean representing the choice
     * @throws IOException If there is an error whilst reading and writing
     * @throws InterruptedException If the current "window" has been interrupted
     */
    private boolean confirmMenu(List<String> header, boolean cursorShown) throws IOException, InterruptedException {
        boolean okSel = false;

        if (cursorShown)
            hideCursor(writer);

        MENU:
        while (!interrupted()) {
            List<String> menu = new ArrayList<>(header);
            menu.add("");
            menu.add("Is this okay?");

            if (okSel) {
                menu.add(colorString("> Yes", CYAN));
                menu.add("> No");
            }

            else {
                menu.add("> Yes");
                menu.add(colorString("> No", CYAN));
            }

            display.clear();
            display.updateAnsi(menu, 0);

            // Key pressing loop
            KEYPRESS:
            while (!interrupted()) {
                // Interpret the availale key
                switch (parseKey(keyStream)) {
                    // Tab or an arrow: go to next menu item (which is also the previous, since there are only two)
                    case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                        okSel = !okSel;
                        continue MENU;
                    }

                    // Number one or Y key: jump to yes
                    case ONE, Y -> {
                        okSel = true;
                        continue MENU;
                    }

                    // Number two or N key: jump to no
                    case TWO, N -> {
                        okSel = false;
                        continue MENU;
                    }

                    // Return: select the item
                    case ENTER -> {
                        if (cursorShown)
                            showCursor(writer);

                        return okSel;
                    }

                    // Otherwise, just read the next keypress
                    default -> {
                        continue KEYPRESS;
                    }
                }
            }
        }

        throw new InterruptedException();
    }

    /**
     * Find the correct index of the selected cloud tile
     * @param req    The contents of the cloud tile
     * @param clouds The array of clouds from the model
     * @return The index of the cloud tile requested
     */
    private int findCloud(Color[] req, CloudTile[] clouds) {
        OUT:
        for (int i = 0; i < clouds.length; ++i) {
            Color[] cloudStuds = clouds[i].getStudents();

            for (int j = 0; j < cloudStuds.length; ++j)
                if (cloudStuds[j] != req[j])
                    continue OUT;

            return i;
        }

        // Should never happen
        throw new IllegalStateException();
    }

    // endregion

    // region Menus

    // In this region there are "noSwitch" version of methods, which, as the name suggest,
    // do not perform context switching
    // They are required to avoid problems with complex requests, e.g. requesting a character card
    // or the movement of a pawn, which can call these "noSwitch" versions to maintain the context

    @Override
    public void playExitMenu() {
        try {
            ModuloNat sel = new ModuloNat(2);

            hideCursor(writer);

            // Main menu
            MENU:
            while (!interrupted()) {
                List<String> menu = new ArrayList<>(logoList);
                menu.add("Welcome to Eriantys!");
                menu.add("");

                List<String> numOptions = new ArrayList<>(2);
                numOptions.add("> Play");
                numOptions.add("> Exit");

                colorElem(numOptions, sel.value());
                menu.addAll(numOptions);

                display.clear();
                display.updateAnsi(menu, 0);

                // Key pressing loop
                KEYPRESS:
                while (!interrupted()) {
                    // Interpret the availale key
                    switch (parseKey(keyStream)) {
                        // Tab or an arrow: go to the other menu item
                        case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                            sel.next();
                            continue MENU;
                        }

                        case ONE, Y -> {
                            sel.set(0);
                            continue MENU;
                        }

                        case TWO, N, ESCAPE -> {
                            sel.set(1);
                            continue MENU;
                        }

                        // Return: select the item
                        case ENTER -> {
                            switch (sel.value()) {
                                case 0 -> askAddress();
                                case 1 -> close();
                            }

                            break MENU;
                        }

                        // Otherwise, just read the next keypress
                        default -> {
                            continue KEYPRESS;
                        }
                    }
                }
            }
        }

        catch (UserInterruptException ignored) {
            close();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void signalConnectionError() {
        display.clear();
        display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

        writer.println();
        writer.println("There was an error whilst trying to connect to the server!");
        writer.println("Please check your Internet connection and try again");
        writer.println();

        try {
            hideCursor(writer);
            writer.print("Press any key to continue...");
            keyStream.read();
            showCursor(writer);
        }

        // Should never happen
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateModel(GameModel model, Set<GameValues> ignored) {
        if (model == null)
            return;

        display.clear();
        display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

        moveCursor(writer, DOWN, 1);

        for (int i = 0, isl = 0; i < 2; ++i) {
            for (int j = 0; j < 6; ++j, ++isl) {
                if (isl >= model.getIslandsCount())
                    break;

                drawIsland(writer, model.getIsland(isl), isl == model.getMotherNaturePosition());

                moveCursor(writer, UP, 9);
                moveCursor(writer, RIGHT, 20);
            }

            moveCursor(writer, LEFT, terminal.getWidth());
            moveCursor(writer, DOWN, 10);
        }

        moveCursor(writer, DOWN, 1);

        for (int i = 0; i < model.getCloudTiles().length; ++i) {
            drawCloud(writer, model.getCloudTile(i));

            moveCursor(writer, RIGHT, 20);
            moveCursor(writer, UP, 7);
        }

        moveCursor(writer, LEFT, terminal.getWidth());
        moveCursor(writer, DOWN, 8);

        drawTable(writer, getLocalPlayer().getSchoolBoard(), model, getLocalPlayer());

        moveCursor(writer, LEFT, terminal.getWidth());
        moveCursor(writer, DOWN, 2);

        if (model.getExpertMode()) {
            int coinPool = 0;

            if (getLocalPlayer() instanceof PlayerExpert p)
                coinPool = p.getCoinCount();

            else if (getLocalPlayer() instanceof PlayerTeamExpert p)
                coinPool = p.getCoinCount();

            String cards = Arrays.stream(model.getCharacterCards())
                .map(this::characterName)
                .collect(Collectors.joining(", "));

            writer.println("Global coin pool: " + model.getCoinPool());
            writer.println("Your coin pool: "   + coinPool);
            writer.println("Character cards: "  + cards);
        }
    }

    @Override
    public void askAddress() {
        showCursor(writer);

        LineReader localReader = LineReaderBuilder.builder().terminal(terminal).completer(new StringsCompleter("localhost")).option(LineReader.Option.INSERT_TAB, false).build();

        try {
            // Both tuples contain a string representing a candidate for the value
            // and a bool representing whether that value is acceptable
            Tuple<String, Boolean> ip   = new Tuple<>(null, false);
            Tuple<String, Boolean> port = new Tuple<>(null, false);

            while (!interrupted()) {
                //Update the console with the logo
                display.clear();
                display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

                // Print the request for the IP
                // NB: the last space is used to solve a bug, because otherwise backspace would delete the whole line
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
                    String readIP = Address.sanitizeIP(localReader.readLine());

                    if (readIP.equals(""))
                        continue;

                    // If the IP is incorrect, add it to the tuple and print again the menu, to highlight it with an error
                    if (!Address.checkIP(readIP)) {
                        ip = new Tuple<>(readIP, true);
                        continue;
                    }

                    // Otherwise, the candidate is correct, so update the tuple accordingly
                    ip = new Tuple<>(readIP, false);
                    //moveCursor(writer, Ansi.Direction.UP, 5);
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

                localReader = LineReaderBuilder.builder().terminal(terminal).completer(new StringsCompleter("6556")).option(LineReader.Option.INSERT_TAB, false).build();

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
                String readPort = localReader.readLine();

                if (readPort.equals(""))
                    continue;

                try {
                    // Filter for well known ports, that will not be accepted
                    if (Address.checkPort(readPort)) {
                        List<String> conf = new ArrayList<>(logoList);
                        conf.add("");
                        conf.add("Selected address:");
                        conf.add("IP address: " + ip.left());
                        conf.add("Port: " + readPort);

                        if (confirmMenu(conf, true)) {
                            address = new Address(ip.left(), Address.parsePort(readPort));
                            return;
                        }

                        else
                            ip = new Tuple<>(null, false);
                    }
                }

                // If the port cannot be parsed, simply ignore the exception
                catch (NumberFormatException ignored) {}

                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // The port number is illegal because of parsing or for beign well known, so update the tuple accordingly
                port = new Tuple<>(readPort, true);
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void askRules() {
        contextSwitch(() -> {
            try {
                Integer numOfPlayers = null;
                Boolean expertMode   = null;

                ModuloNat numSel = new ModuloNat(3);
                ModuloNat expSel = new ModuloNat(2, 1);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Please select the rules of the game");
                    menu.add("");
                    menu.add("How many players will the game have?");

                    List<String> numOptions = new ArrayList<>(3);
                    numOptions.add("> 2");
                    numOptions.add("> 3");
                    numOptions.add("> 4");

                    colorElem(numOptions, numSel.value());
                    menu.addAll(numOptions);

                    // If no players have been selected, print the selection
                    if (numOfPlayers == null) {
                        display.clear();
                        display.updateAnsi(menu, 0);

                        // Key pressing loop
                        KEYPRESS:
                        while (!interrupted()) {
                            // Interpret the availale key
                            switch (parseKey(keyStream)) {
                                // Tab, down arrow or right arrow: go to next menu item
                                case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                    numSel.next();
                                    continue MENU;
                                }

                                // Up arrow or left arrow: go to the previous menu item
                                case UP_ARROW, LEFT_ARROW -> {
                                    numSel.prev();
                                    continue MENU;
                                }

                                // Number two: jump to two players
                                case TWO -> {
                                    numSel.set(0);
                                    continue MENU;
                                }

                                // Number three: jump to three players
                                case THREE -> {
                                    numSel.set(1);
                                    continue MENU;
                                }

                                // Number three: jump to four players
                                case FOUR -> {
                                    numSel.set(2);
                                    continue MENU;
                                }

                                // Return: select the item
                                case ENTER -> {
                                    numOfPlayers = numSel.value() + 2;
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

                    colorElem(expOptions, expSel.value());
                    menu.addAll(expOptions);

                    if (expertMode == null) {
                        display.clear();
                        display.updateAnsi(menu, 0);

                        // Key pressing loop
                        KEYPRESS:
                        while (!interrupted()) {
                            // Interpret the availale key
                            switch (parseKey(keyStream)) {
                                // Tab, down arrow or right arrow: go to next menu item
                                case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                                    expSel.next();
                                    continue MENU;
                                }

                                // Number one or Y key: jump to yes
                                case ONE, Y -> {
                                    expSel.set(0);
                                    continue MENU;
                                }

                                // Number two or N key: jump to no
                                case TWO, N -> {
                                    expSel.set(1);
                                    continue MENU;
                                }

                                // Return: select the item
                                case ENTER -> {
                                    expertMode = expSel.value() == 0;
                                    continue MENU;
                                }

                                // Otherwise, just read the next keypress
                                default -> {
                                    continue KEYPRESS;
                                }
                            }
                        }
                    }

                    if (interrupted())
                        return;

                    // When I get to this point both should have a value, but if for some reason
                    // that's not the case, simply reset the state of the menu and try again
                    if (numOfPlayers == null || expertMode == null) {
                        numOfPlayers = null;
                        expertMode   = null;
                        continue MENU;
                    }

                    menu.clear();
                    menu.addAll(logoList);
                    menu.add("");
                    menu.add("Selected options:");
                    menu.add("Number of players: " + numOfPlayers);
                    menu.add("Expert rules: " + (expertMode ? "Yes" : "No"));

                    if (confirmMenu(menu, false)) {
                        forwardViewToVirtualController(new GameRules(numOfPlayers, expertMode));
                        showCursor(writer);
                    }

                    else
                        askRules();

                    return;
                }
            }

            catch (InterruptedException ignored) {}

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void askReloadGame() {
        contextSwitch(() -> {
            try {
                boolean sel = false;

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("A saved game has been found!");
                    menu.add("Would you like to resume it?");

                    if (sel) {
                        menu.add(colorString("> Yes", CYAN));
                        menu.add("> No");
                    }

                    else {
                        menu.add("> Yes");
                        menu.add(colorString("> No", CYAN));
                    }

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                                sel = !sel;
                                continue MENU;
                            }

                            case Y, ONE -> {
                                sel = true;
                                continue MENU;
                            }

                            case N, TWO -> {
                                sel = false;
                                continue MENU;
                            }

                            case ENTER -> {
                                if (sel) {
                                    display.clear();
                                    display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());
                                    writer.println();
                                    writer.println("Rules set!");
                                    writer.print("Please wait for the other players to join");
                                }

                                forwardViewToVirtualController(sel);
                                return;
                            }

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
        });
    }

    @Override
    public void askEndOfTurn() {
        contextSwitch(() -> {
            try {
                boolean sel = false;

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("You still have the chance to play a character card!");
                    menu.add("");
                    menu.add("Would you like to?");

                    List<String> yesNo = new ArrayList<>(3);

                    if (sel) {
                        yesNo.add(colorString("> Yes", CYAN));
                        yesNo.add("> No");
                    }

                    else {
                        yesNo.add("> Yes");
                        yesNo.add(colorString("> No", CYAN));
                    }

                    menu.addAll(yesNo);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (true) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab or an arrow: go to the other menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                                sel = !sel;
                                continue MENU;
                            }

                            // Number one or Y key: jump to yes
                            case ONE, Y -> {
                                sel = true;
                                continue MENU;
                            }

                            // Number two or N: jump to no
                            case TWO, N -> {
                                sel = false;
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                forwardViewToVirtualController(!sel);
                                return;
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
        });
    }

    @Override
    public void requestUsernameAndMagicAge(Set<String> forbiddenUsernames) {
        contextSwitch(() -> {
            showCursor(writer);

            try {
                Tuple<String, UsernameResult> username = new Tuple<>(null, UsernameResult.NULL);
                Tuple<String, Boolean>        magicAge = new Tuple<>(null, false);

                while (!interrupted()) {
                    //Update the console with the logo
                    display.clear();
                    display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

                    // NB: the last space is used to solve a bug, because otherwise backspace would delete the whole line
                    writer.println();
                    writer.print("Please enter the username you've chosen:");
                    writer.print(" ");

                    switch (username.right()) {
                        case OK -> writer.println(colorString(username.left(), Ansi.GREEN));

                        case LONG -> {
                            // Print the candidate on a red background with an error message
                            writer.print(colorString(username.left() + " is longer than the 10 character limit", Ansi.BACKGROUND_RED));

                            // Reset the tuple
                            username = new Tuple<>(null, UsernameResult.NULL);

                            // Wait for a generic key, hiding the cursor
                            hideCursor(writer);
                            keyStream.read();
                            showCursor(writer);

                            continue;
                        }

                        case CHOSEN -> {
                            // Print the candidate on a red background with an error message
                            writer.print(colorString(username.left() + " has already been chosen by another player", Ansi.BACKGROUND_RED));

                            // Reset the tuple
                            username = new Tuple<>(null, UsernameResult.NULL);

                            // Wait for a generic key, hiding the cursor
                            hideCursor(writer);
                            keyStream.read();
                            showCursor(writer);

                            continue;
                        }

                        case NULL -> {
                            String readName = sanitizeUsername(reader.readLine());

                            if (readName.equals(""))
                                continue;

                            // Otherwise, the candidate is correct, so update the tuple accordingly
                            username = new Tuple<>(readName, checkUsername(readName, forbiddenUsernames));
                            continue;
                        }
                    }

                    // Print the request for the port number
                    // NB: the last space is used to solve a bug, because otherwise backspace would delete the whole line
                    writer.println();
                    writer.print("Please enter for how many years you've known magic:");
                    writer.print(" ");

                    // Check if there is an illegal candidate for the value
                    if (magicAge.right()) {
                        // Print the candidate on a red background with an error message
                        writer.print(colorString(magicAge.left() + " is not a valid age", Ansi.BACKGROUND_RED));

                        // Reset the tuple
                        magicAge = new Tuple<>(null, false);

                        // Wait for a generic key, hiding the cursor
                        hideCursor(writer);
                        keyStream.read();
                        showCursor(writer);

                        continue;
                    }

                    // Otherwise, read the port and try to parse it
                    String readAge = reader.readLine();

                    if (readAge.equals(""))
                        continue;

                    try {
                        if (checkMagicAge(readAge)) {
                            display.clear();
                            List<String> menu = new ArrayList<>(logoList);
                            menu.add("");
                            menu.add("Entered data:");
                            menu.add("Username: " + username.left());
                            menu.add("Magic age: " + readAge);

                            if (confirmMenu(menu, true)) {
                                setUsernameVirtualController(username.left());
                                forwardViewToVirtualController(new UsernameAndMagicAge(username.left(), parseMagicAge(readAge)));
                                return;
                            }

                            else {
                                username = new Tuple<>(null, UsernameResult.NULL);
                                magicAge = new Tuple<>(null, false);
                                continue;
                            }
                        }
                    }

                    // If the port cannot be parsed, simply ignore the exception
                    catch (NumberFormatException ignored) {}

                    // The port number is illegal because of parsing or for beign well known, so update the tuple accordingly
                    magicAge = new Tuple<>(readAge, true);
                }
            }

            catch (InterruptedException | UserInterruptException ignored) {}

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void requestWizard(Wizard[] availableWizards) {
        contextSwitch(() -> {
            try {
                int       numOfWiz = availableWizards.length;
                ModuloNat wizSel   = new ModuloNat(numOfWiz);

                hideCursor(writer);

                Wizard[] sortedWiz = Arrays.stream(availableWizards)
                    .sorted(comparing(Enum::name))
                    .toArray(Wizard[]::new);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Please select the wizard you would like to play as:");

                    List<String> wizOptions = new ArrayList<>(numOfWiz);

                    Arrays.stream(sortedWiz)
                        .map(Enum::name)
                        .map(Methods::capitalize)
                        .forEach(w -> wizOptions.add("> " + w));

                    colorElem(wizOptions, wizSel.value());
                    menu.addAll(wizOptions);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                wizSel.next();
                                continue MENU;
                            }

                            // Up arrow or left arrow: go to the previous menu item
                            case UP_ARROW, LEFT_ARROW -> {
                                wizSel.prev();
                                continue MENU;
                            }

                            case ONE -> {
                                wizSel.set(0);
                                continue MENU;
                            }

                            // Number two: jump to two players
                            case TWO -> {
                                wizSel.set(1);
                                continue MENU;
                            }

                            // Number three: jump to three players
                            case THREE -> {
                                if (numOfWiz < 3)
                                    continue KEYPRESS;

                                wizSel.set(2);
                                continue MENU;
                            }

                            // Number three: jump to four players
                            case FOUR -> {
                                if (numOfWiz < 4)
                                    continue KEYPRESS;

                                wizSel.set(3);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                List<String> header = new ArrayList<>(logoList);
                                header.add("Selected wizard: " + capitalize(sortedWiz[wizSel.value()].name()));

                                if (!confirmMenu(header, false))
                                    continue MENU;

                                forwardViewToVirtualController(sortedWiz[wizSel.value()]);
                                menu = new ArrayList<>(logoList);
                                menu.add("");
                                menu.add("Rules set!");
                                menu.add("Please wait for the other players to join");
                                display.clear();
                                display.updateAnsi(menu, 0);
                                return;
                            }

                            // Otherwise, just read the next keypress
                            default -> {
                                continue KEYPRESS;
                            }
                        }
                    }
                }
            }

            catch (InterruptedException ignored) {}

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void requestPlayAssistantCard(AssistantCard[] assistantCards) {
        contextSwitch(() -> {
            try {
                int       numOfCards = assistantCards.length;
                ModuloNat cardSel    = new ModuloNat(numOfCards + 1);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Please select the assistant card you would like to play:");

                    List<String> cardOptions = new ArrayList<>(numOfCards);

                    Arrays.stream(assistantCards).forEach(card ->
                        cardOptions.add("> Card value: %2d, Movement points: %1d".formatted(card.cardValue(), card.movementPoints())));

                    cardOptions.add("> Show board");

                    colorElem(cardOptions, cardSel.value());
                    menu.addAll(cardOptions);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                cardSel.next();
                                continue MENU;
                            }

                            // Up arrow or left arrow: go to the previous menu item
                            case UP_ARROW, LEFT_ARROW -> {
                                cardSel.prev();
                                continue MENU;
                            }

                            case ONE -> {
                                cardSel.set(0);
                                continue MENU;
                            }

                            case TWO -> {
                                if (numOfCards < 2)
                                    continue KEYPRESS;

                                cardSel.set(1);
                                continue MENU;
                            }

                            case THREE -> {
                                if (numOfCards < 3)
                                    continue KEYPRESS;

                                cardSel.set(2);
                                continue MENU;
                            }

                            case FOUR -> {
                                if (numOfCards < 4)
                                    continue KEYPRESS;

                                cardSel.set(3);
                                continue MENU;
                            }

                            case FIVE -> {
                                if (numOfCards < 5)
                                    continue KEYPRESS;

                                cardSel.set(4);
                                continue MENU;
                            }

                            case SIX -> {
                                if (numOfCards < 6)
                                    continue KEYPRESS;

                                cardSel.set(5);
                                continue MENU;
                            }

                            case SEVEN -> {
                                if (numOfCards < 7)
                                    continue KEYPRESS;

                                cardSel.set(6);
                                continue MENU;
                            }

                            case EIGHT -> {
                                if (numOfCards < 8)
                                    continue KEYPRESS;

                                cardSel.set(7);
                                continue MENU;
                            }

                            case NINE -> {
                                if (numOfCards < 9)
                                    continue KEYPRESS;

                                cardSel.set(8);
                                continue MENU;
                            }

                            case ZERO -> {
                                if (numOfCards < 10)
                                    continue KEYPRESS;

                                cardSel.set(9);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                if (cardSel.value() == numOfCards) {
                                    showModel();

                                    continue MENU;
                                }

                                forwardViewToVirtualController(assistantCards[cardSel.value()]);
                                return;
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
        });
    }

    @Override
    public void requestPlayCharacterCard(CharacterCard[] playableCharacterCards) {
        contextSwitch(this::requestPlayCharacterCard_noSwitch, playableCharacterCards);
    }

    /**
     * A context switch-less version of the request
     * @param playableCharacterCards The available cards
     */
    private void requestPlayCharacterCard_noSwitch(CharacterCard[] playableCharacterCards) {
        try {
            int       numOfCards = playableCharacterCards.length;
            ModuloNat cardSel    = new ModuloNat(numOfCards);

            hideCursor(writer);

            // Main menu
            MENU:
            while (!interrupted()) {
                List<String> menu = new ArrayList<>(logoList);
                menu.add("");
                menu.add("Please select the assistant card you would like to play:");

                List<String> cardOptions = new ArrayList<>(numOfCards);

                Arrays.stream(playableCharacterCards).forEach(card ->
                    cardOptions.add("> " + characterName(card)));

                colorElem(cardOptions, cardSel.value());
                menu.addAll(cardOptions);

                display.clear();
                display.updateAnsi(menu, 0);

                // Key pressing loop
                KEYPRESS:
                while (!interrupted()) {
                    // Interpret the availale key
                    switch (parseKey(keyStream)) {
                        // Tab, down arrow or right arrow: go to next menu item
                        case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                            cardSel.next();
                            continue MENU;
                        }

                        // Up arrow or left arrow: go to the previous menu item
                        case UP_ARROW, LEFT_ARROW -> {
                            cardSel.prev();
                            continue MENU;
                        }

                        case ONE -> {
                            cardSel.set(0);
                            continue MENU;
                        }

                        case TWO -> {
                            if (numOfCards < 2)
                                continue KEYPRESS;

                            cardSel.set(1);
                            continue MENU;
                        }

                        case THREE -> {
                            if (numOfCards < 3)
                                continue KEYPRESS;

                            cardSel.set(2);
                            continue MENU;
                        }

                        // Return: select the item
                        case ENTER -> {
                            forwardViewToVirtualController(playableCharacterCards[cardSel.value()]);
                            return;
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

    @Override
    public void requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards) {
        contextSwitch(() -> {
            try {
                ModuloNat sel = new ModuloNat(2);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("You can move the students or play a character card");
                    menu.add("");
                    menu.add("What would you like to do?");

                    List<String> options = new ArrayList<>(2);
                    options.add("> Move students");
                    options.add("> Play a character card");

                    colorElem(options, sel.value());

                    menu.addAll(options);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab or an arrow: go to the other menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                                sel.next();
                                continue MENU;
                            }

                            // Number one or Y key: jump to yes
                            case ONE, Y -> {
                                sel.set(0);
                                continue MENU;
                            }

                            // Number two or N: jump to no
                            case TWO, N -> {
                                sel.set(1);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                switch (sel.value()) {
                                    case 0 -> chooseStudentFromEntrance_noSwitch(entranceStudents);
                                    case 1 -> requestPlayCharacterCard_noSwitch(playableCharacterCards);
                                }

                                return;
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
        });
    }

    @Override
    public void requestStudentEntranceMovement(int selectedStudentIndex, Boolean[] diningRoomFreeTables) {
        contextSwitch(() -> {
            try {
                ModuloNat sel = new ModuloNat(3);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    // Check whether the selected student can be moved to the corresponding table, breaking the loop if it can't
                    if (!diningRoomFreeTables[getLocalPlayer().getSchoolBoard().getEntrance().getStudents()[selectedStudentIndex].ordinal()])
                        break MENU;

                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Please select where you'd like to move the student:");

                    List<String> options = new ArrayList<>(3);

                    options.add("> An island");
                    options.add("> Your dining room");
                    options.add("> Show board");

                    colorElem(options, sel.value());
                    menu.addAll(options);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                sel.next();
                                continue MENU;
                            }

                            // Up arrow or left arrow: go to the previous menu item
                            case UP_ARROW, LEFT_ARROW -> {
                                sel.prev();
                                continue MENU;
                            }

                            case ONE -> {
                                sel.set(0);
                                continue MENU;
                            }

                            case TWO -> {
                                sel.set(1);
                                continue MENU;
                            }

                            case THREE -> {
                                sel.set(2);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                switch (sel.value()) {
                                    case 0 -> {
                                        break MENU;
                                    }

                                    case 1 -> {
                                        forwardViewToVirtualController(new MoveStudentInfo(true, null, selectedStudentIndex));
                                        SchoolBoard sch = getLocalPlayer().getSchoolBoard();
                                        Color std = sch.getEntrance().retrieveStudent(selectedStudentIndex);
                                        sch.getDiningRoom().setStudentCounters(std, sch.getDiningRoom().getStudentCounters(std) + 1);
                                        return;
                                    }

                                    case 2 -> showModel();
                                }
                            }

                            // Otherwise, just read the next keypress
                            default -> {
                                continue KEYPRESS;
                            }
                        }
                    }
                }

                int islandNum = model.getIslandsCount();

                Tuple<String, Boolean> islInd = new Tuple<>(null, false);

                showCursor(writer);

                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Here are the islands you can select");
                    menu.add("");

                    display.clear();
                    display.updateAnsi(menu, (terminal.getWidth() + 1) * menu.size());

                    for (int i = 0, isl = 0; i < 2; ++i) {
                        for (int j = 0; j < 6; ++j, ++isl) {
                            if (isl >= islandNum)
                                break;

                            drawIsland(writer, model.getIsland(isl), isl == model.getMotherNaturePosition());

                            moveCursor(writer, UP, 9);
                            moveCursor(writer, RIGHT, 20);
                        }

                        moveCursor(writer, LEFT, terminal.getWidth());
                        moveCursor(writer, DOWN, 10);
                    }

                    writer.println();
                    writer.print("Please enter the index, starting from 1, of the island you've chosen:");
                    writer.print(" ");

                    // Check if there is an illegal candidate for the value
                    if (islInd.right()) {
                        // Print the candidate on a red background with an error message
                        writer.print(colorString(islInd.left() + " is not a valid index", Ansi.BACKGROUND_RED));

                        // Reset the tuple
                        islInd = new Tuple<>(null, false);

                        // Wait for a generic key, hiding the cursor
                        hideCursor(writer);
                        keyStream.read();
                        showCursor(writer);

                        continue;
                    }

                    // Otherwise, check if there is not a candidate
                    else {
                        String read = reader.readLine();

                        try {
                            int readIslInd = Integer.parseInt(read);
                            if (readIslInd <= 0 || readIslInd > islandNum)
                                throw new NumberFormatException();

                            forwardViewToVirtualController(new MoveStudentInfo(false, readIslInd - 1, selectedStudentIndex));
                            Color std = getLocalPlayer().getSchoolBoard().getEntrance().retrieveStudent(selectedStudentIndex);
                            model.getIsland(readIslInd - 1).setStudentCounters(std, model.getIsland(readIslInd - 1).getStudentCounters(std) + 1);
                            return;
                        }

                        catch (NumberFormatException ignored) {
                            islInd = new Tuple<>(read, true);
                            continue;
                        }
                    }
                }
            }

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void requestMotherNatureMovement(Island[] possibleMovement) {
        contextSwitch(this::requestMotherNatureMovement_noSwitch, possibleMovement);
    }

    /**
     * A context switch-less version of the request
     * @param possibleMovement The island Mother Nature can move to
     */
    private void requestMotherNatureMovement_noSwitch(Island[] possibleMovement) {
        try {
            int islandNum = possibleMovement.length;

            Tuple<String, Boolean> islInd = new Tuple<>(null, false);

            showCursor(writer);

            while (!interrupted()) {
                List<String> menu = new ArrayList<>(logoList);
                menu.add("");
                menu.add("Here are the islands you can move Mother Nature to");
                menu.add("");

                display.clear();
                display.updateAnsi(menu, (terminal.getWidth() + 1) * menu.size());

                for (int i = 0, isl = model.getMotherNaturePosition() + 1, islCnt = 0; i < 2; ++i) {
                    for (int j = 0; j < 6; ++j, isl = (isl + 1) % model.getIslandsCount(), ++islCnt) {
                        if (islCnt >= islandNum)
                            break;

                        drawIsland(writer, model.getIsland(isl), false);

                        moveCursor(writer, UP, 9);
                        moveCursor(writer, RIGHT, 20);
                    }

                    moveCursor(writer, LEFT, terminal.getWidth());
                    moveCursor(writer, DOWN, 10);
                }

                writer.println();
                writer.print("Please enter the index, starting from 1, of the island you've chosen:");
                writer.print(" ");

                // Check if there is an illegal candidate for the value
                if (islInd.right()) {
                    // Print the candidate on a red background with an error message
                    writer.print(colorString(islInd.left() + " is not a valid index", Ansi.BACKGROUND_RED));

                    // Reset the tuple
                    islInd = new Tuple<>(null, false);

                    // Wait for a generic key, hiding the cursor
                    hideCursor(writer);
                    keyStream.read();
                    showCursor(writer);

                    continue;
                }

                // Otherwise, check if there is not a candidate
                else {
                    String read = reader.readLine();

                    try {
                        int readIslInd = Integer.parseInt(read);
                        if (readIslInd <= 0 || readIslInd > islandNum)
                            throw new NumberFormatException();

                        forwardViewToVirtualController((model.getMotherNaturePosition() + readIslInd) % model.getIslandsCount());
                        return;
                    }

                    catch (NumberFormatException ignored) {
                        islInd = new Tuple<>(read, true);
                        continue;
                    }
                }
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards) {
        contextSwitch(() -> {
            try {
                ModuloNat sel = new ModuloNat(2);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("You can move Mother Nature or play a character card");
                    menu.add("");
                    menu.add("What would you like to do?");

                    List<String> options = new ArrayList<>(2);
                    options.add("> Move Mother Nature");
                    options.add("> Play a character card");

                    colorElem(options, sel.value());

                    menu.addAll(options);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab or an arrow: go to the other menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                                sel.next();
                                continue MENU;
                            }

                            // Number one or Y key: jump to yes
                            case ONE, Y -> {
                                sel.set(0);
                                continue MENU;
                            }

                            // Number two or N: jump to no
                            case TWO, N -> {
                                sel.set(1);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                switch (sel.value()) {
                                    case 0 -> requestMotherNatureMovement_noSwitch(possibleMovement);
                                    case 1 -> requestPlayCharacterCard_noSwitch(playableCharacterCards);
                                }

                                return;
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
        });
    }

    @Override
    public void requestCloudTileSelection(CloudTile[] availableClouds) {
        contextSwitch(this::requestCloudTileSelection_noSwitch, availableClouds);
    }

    /**
     * A context switch-less version of the request
     * @param availableClouds The available clouds
     */
    private void requestCloudTileSelection_noSwitch(CloudTile[] availableClouds) {
        try {
            int cloudsNum = availableClouds.length;

            Tuple<String, Boolean> cldInd = new Tuple<>(null, false);

            showCursor(writer);

            while (true) {
                List<String> menu = new ArrayList<>(logoList);
                menu.add("");
                menu.add("Here are the cloud tiles from which you can choose from");
                menu.add("");

                display.clear();
                display.updateAnsi(menu, (terminal.getWidth() + 1) * menu.size());

                Arrays.stream(availableClouds).forEach(availableCloud -> {
                    drawCloud(writer, availableCloud);

                    moveCursor(writer, RIGHT, 20);
                    moveCursor(writer, UP, 7);
                });

                moveCursor(writer, LEFT, terminal.getWidth());
                moveCursor(writer, DOWN, 8);

                writer.println();
                writer.print("Please enter the index, starting from 1, of the cloud tile you've chosen:");
                writer.print(" ");

                // Check if there is an illegal candidate for the value
                if (cldInd.right()) {
                    // Print the candidate on a red background with an error message
                    writer.print(colorString(cldInd.left() + " is not a valid index", Ansi.BACKGROUND_RED));

                    // Reset the tuple
                    cldInd = new Tuple<>(null, false);

                    // Wait for a generic key, hiding the cursor
                    hideCursor(writer);
                    keyStream.read();
                    showCursor(writer);

                    continue;
                }

                // Otherwise, check if there is not a candidate
                else if (cldInd.left() == null) {
                    String read = reader.readLine();

                    try {
                        int readCldInd = Integer.parseInt(read);
                        if (readCldInd <= 0 || readCldInd > cloudsNum)
                            throw new NumberFormatException();

                        int cloudIndex = findCloud(availableClouds[readCldInd - 1].getStudents(), model.getCloudTiles());

                        Entrance en = getLocalPlayer().getSchoolBoard().getEntrance();
                        Arrays.stream(model.getCloudTile(cloudIndex).getStudents()).forEach(en::appendStudent);

                        forwardViewToVirtualController(cloudIndex);

                        return;
                    }

                    catch (NumberFormatException ignored) {
                        cldInd = new Tuple<>(read, true);
                        continue;
                    }
                }
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards) {
        contextSwitch(() -> {
            try {
                ModuloNat sel = new ModuloNat(2);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("You can choose a cloud tile or play a character card");
                    menu.add("");
                    menu.add("What would you like to do?");

                    List<String> options = new ArrayList<>(2);
                    options.add("> Choose a cloud tile");
                    options.add("> Play a character card");

                    colorElem(options, sel.value());

                    menu.addAll(options);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab or an arrow: go to the other menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW, UP_ARROW, LEFT_ARROW -> {
                                sel.next();
                                continue MENU;
                            }

                            // Number one or Y key: jump to yes
                            case ONE, Y -> {
                                sel.set(0);
                                continue MENU;
                            }

                            // Number two or N: jump to no
                            case TWO, N -> {
                                sel.set(1);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                switch (sel.value()) {
                                    case 0 -> requestCloudTileSelection_noSwitch(availableClouds);
                                    case 1 -> requestPlayCharacterCard_noSwitch(playableCharacterCards);
                                }

                                return;
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
        });
    }

    @Override
    public void requestHowManyStudentsToMove(int maxNumOfStudentMovable) {
        contextSwitch(() -> {
            try {
                Tuple<String, Boolean> cldInd = new Tuple<>(null, false);

                showCursor(writer);

                while (!interrupted()) {
                    display.clear();
                    display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

                    writer.println();
                    writer.print("Please enter how many students you'd like to move, with a maximum of " + maxNumOfStudentMovable + ":");
                    writer.print(" ");

                    // Check if there is an illegal candidate for the value
                    if (cldInd.right()) {
                        // Print the candidate on a red background with an error message
                        writer.print(colorString(cldInd.left() + " is not a valid number of students", Ansi.BACKGROUND_RED));

                        // Reset the tuple
                        cldInd = new Tuple<>(null, false);

                        // Wait for a generic key, hiding the cursor
                        hideCursor(writer);
                        keyStream.read();
                        showCursor(writer);

                        continue;
                    }

                    // Otherwise, check if there is not a candidate
                    else if (cldInd.left() == null) {
                        String read = reader.readLine();

                        try {
                            int readInt = Integer.parseInt(read);
                            if (readInt <= 0 || readInt > maxNumOfStudentMovable)
                                throw new NumberFormatException();

                            forwardViewToVirtualController(readInt);
                            return;
                        }

                        catch (NumberFormatException ignored) {
                            cldInd = new Tuple<>(read, true);
                            continue;
                        }
                    }
                }
            }

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void requestChooseColor(Color[] availableColors) {
        contextSwitch(() -> {
            try {
                int       numOfColors = (int) Arrays.stream(availableColors).filter(Objects::nonNull).count();
                ModuloNat colorSel    = new ModuloNat(numOfColors);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Please select a color:");

                    List<String> colorOptions = new ArrayList<>(numOfColors);

                    Arrays.stream(availableColors).forEach(color ->
                        ifNotNull(color, () ->
                            colorOptions.add("> " + colorString(capitalize(color.name()), getStudentColor(color, false)))));

                    underlineElem(colorOptions, colorSel.value());
                    menu.addAll(colorOptions);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                colorSel.next();
                                continue MENU;
                            }

                            // Up arrow or left arrow: go to the previous menu item
                            case UP_ARROW, LEFT_ARROW -> {
                                colorSel.prev();
                                continue MENU;
                            }

                            case ONE -> {
                                colorSel.set(0);
                                continue MENU;
                            }

                            case TWO -> {
                                if (numOfColors < 2)
                                    continue KEYPRESS;

                                colorSel.set(1);
                                continue MENU;
                            }

                            case THREE -> {
                                if (numOfColors < 3)
                                    continue KEYPRESS;

                                colorSel.set(2);
                                continue MENU;
                            }

                            case FOUR -> {
                                if (numOfColors < 4)
                                    continue KEYPRESS;

                                colorSel.set(3);
                                continue MENU;
                            }

                            case FIVE -> {
                                if (numOfColors < 5)
                                    continue KEYPRESS;

                                colorSel.set(4);
                                continue MENU;
                            }

                            case SIX -> {
                                if (numOfColors < 6)
                                    continue KEYPRESS;

                                colorSel.set(5);
                                continue MENU;
                            }

                            case SEVEN -> {
                                if (numOfColors < 7)
                                    continue KEYPRESS;

                                colorSel.set(6);
                                continue MENU;
                            }

                            case EIGHT -> {
                                if (numOfColors < 8)
                                    continue KEYPRESS;

                                colorSel.set(7);
                                continue MENU;
                            }

                            case NINE -> {
                                if (numOfColors < 9)
                                    continue KEYPRESS;

                                colorSel.set(8);
                                continue MENU;
                            }

                            case ZERO -> {
                                if (numOfColors < 10)
                                    continue KEYPRESS;

                                colorSel.set(9);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                forwardViewToVirtualController(availableColors[colorSel.value()]);
                                return;
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
        });
    }

    @Override
    public void chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent) {
        contextSwitch(() -> {
            try {
                int       numOfColors = (int) Arrays.stream(availableColors).filter(Objects::nonNull).count();
                ModuloNat colorSel    = new ModuloNat(numOfColors);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Please select a color from the card:");

                    List<String> colorOptions = new ArrayList<>(numOfColors);

                    for (Color color : availableColors)
                        ifNotNull(color, () ->
                            colorOptions.add("> " + colorString(capitalize(color.name()), getStudentColor(color, false))));

                    underlineElem(colorOptions, colorSel.value());
                    menu.addAll(colorOptions);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                colorSel.next();
                                continue MENU;
                            }

                            // Up arrow or left arrow: go to the previous menu item
                            case UP_ARROW, LEFT_ARROW -> {
                                colorSel.prev();
                                continue MENU;
                            }

                            case ONE -> {
                                colorSel.set(0);
                                continue MENU;
                            }

                            case TWO -> {
                                if (numOfColors < 2)
                                    continue KEYPRESS;

                                colorSel.set(1);
                                continue MENU;
                            }

                            case THREE -> {
                                if (numOfColors < 3)
                                    continue KEYPRESS;

                                colorSel.set(2);
                                continue MENU;
                            }

                            case FOUR -> {
                                if (numOfColors < 4)
                                    continue KEYPRESS;

                                colorSel.set(3);
                                continue MENU;
                            }

                            case FIVE -> {
                                if (numOfColors < 5)
                                    continue KEYPRESS;

                                colorSel.set(4);
                                continue MENU;
                            }

                            case SIX -> {
                                if (numOfColors < 6)
                                    continue KEYPRESS;

                                colorSel.set(5);
                                continue MENU;
                            }

                            case SEVEN -> {
                                if (numOfColors < 7)
                                    continue KEYPRESS;

                                colorSel.set(6);
                                continue MENU;
                            }

                            case EIGHT -> {
                                if (numOfColors < 8)
                                    continue KEYPRESS;

                                colorSel.set(7);
                                continue MENU;
                            }

                            case NINE -> {
                                if (numOfColors < 9)
                                    continue KEYPRESS;

                                colorSel.set(8);
                                continue MENU;
                            }

                            case ZERO -> {
                                if (numOfColors < 10)
                                    continue KEYPRESS;

                                colorSel.set(9);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                forwardViewToVirtualController(elemIndex(
                                    availableColors[colorSel.value()],
                                    ((CharacterCardStudent) model.getCharacterCard(characterCardPosition)).getStudents()));
                                return;
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
        });
    }

    @Override
    public void chooseStudentFromEntrance(Color[] availableColors) {
        contextSwitch(this::chooseStudentFromEntrance_noSwitch, availableColors);
    }

    /**
     * A context switch-less version of the request
     * @param availableColors The available colors
     */
    private void chooseStudentFromEntrance_noSwitch(Color[] availableColors) {
        try {
            int       numOfColors = (int) Arrays.stream(availableColors).filter(Objects::nonNull).count();
            ModuloNat colorSel    = new ModuloNat(numOfColors);

            hideCursor(writer);

            // Main menu
            MENU:
            while (!interrupted()) {
                List<String> menu = new ArrayList<>(logoList);
                menu.add("");
                menu.add("Please select a color from your entrance:");

                List<String> colorOptions = new ArrayList<>(numOfColors);

                Arrays.stream(availableColors).forEach(color ->
                    ifNotNull(color, () ->
                        colorOptions.add("> " + colorString(capitalize(color.name()), getStudentColor(color, false)))));

                underlineElem(colorOptions, colorSel.value());
                menu.addAll(colorOptions);

                display.clear();
                display.updateAnsi(menu, 0);

                // Key pressing loop
                KEYPRESS:
                while (!interrupted()) {
                    // Interpret the available key
                    switch (parseKey(keyStream)) {
                        // Tab, down arrow or right arrow: go to next menu item
                        case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                            colorSel.next();
                            continue MENU;
                        }

                        // Up arrow or left arrow: go to the previous menu item
                        case UP_ARROW, LEFT_ARROW -> {
                            colorSel.prev();
                            continue MENU;
                        }

                        case ONE -> {
                            colorSel.set(0);
                            continue MENU;
                        }

                        case TWO -> {
                            if (numOfColors < 2)
                                continue KEYPRESS;

                            colorSel.set(1);
                            continue MENU;
                        }

                        case THREE -> {
                            if (numOfColors < 3)
                                continue KEYPRESS;

                            colorSel.set(2);
                            continue MENU;
                        }

                        case FOUR -> {
                            if (numOfColors < 4)
                                continue KEYPRESS;

                            colorSel.set(3);
                            continue MENU;
                        }

                        case FIVE -> {
                            if (numOfColors < 5)
                                continue KEYPRESS;

                            colorSel.set(4);
                            continue MENU;
                        }

                        case SIX -> {
                            if (numOfColors < 6)
                                continue KEYPRESS;

                            colorSel.set(5);
                            continue MENU;
                        }

                        case SEVEN -> {
                            if (numOfColors < 7)
                                continue KEYPRESS;

                            colorSel.set(6);
                            continue MENU;
                        }

                        case EIGHT -> {
                            if (numOfColors < 8)
                                continue KEYPRESS;

                            colorSel.set(7);
                            continue MENU;
                        }

                        case NINE -> {
                            if (numOfColors < 9)
                                continue KEYPRESS;

                            colorSel.set(8);
                            continue MENU;
                        }

                        // Return: select the item
                        case ENTER -> {
                            forwardViewToVirtualController(elemIndex(
                                availableColors[colorSel.value()],
                                getLocalPlayer().getSchoolBoard().getEntrance().getStudents()));
                            return;
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

    @Override
    public void requestChooseIsland(Island[] availableIslands) {
        contextSwitch(() -> {
            try {
                int islandNum = availableIslands.length;

                Tuple<String, Boolean> islInd = new Tuple<>(null, false);

                showCursor(writer);

                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Here are the islands you can select");
                    menu.add("");

                    display.clear();
                    display.updateAnsi(menu, (terminal.getWidth() + 1) * menu.size());

                    for (int i = 0, isl = 0; i < 2; ++i) {
                        for (int j = 0; j < 6; ++j, ++isl) {
                            if (isl >= islandNum)
                                break;

                            int avlblIslIndx = elemIndex(availableIslands[isl], model.getIslands());

                            drawIsland(writer, model.getIsland(avlblIslIndx), avlblIslIndx == model.getMotherNaturePosition());

                            moveCursor(writer, UP, 9);
                            moveCursor(writer, RIGHT, 20);
                        }

                        moveCursor(writer, LEFT, terminal.getWidth());
                        moveCursor(writer, DOWN, 10);
                    }

                    writer.println();
                    writer.print("Please enter the index, starting from 1, of the island you've chosen:");
                    writer.print(" ");

                    // Check if there is an illegal candidate for the value
                    if (islInd.right()) {
                        // Print the candidate on a red background with an error message
                        writer.print(colorString(islInd.left() + " is not a valid index", Ansi.BACKGROUND_RED));

                        // Reset the tuple
                        islInd = new Tuple<>(null, false);

                        // Wait for a generic key, hiding the cursor
                        hideCursor(writer);
                        keyStream.read();
                        showCursor(writer);

                        continue;
                    }

                    // Otherwise, check if there is not a candidate
                    else {
                        String read = reader.readLine();

                        try {
                            int readIslInd = Integer.parseInt(read);
                            if (readIslInd <= 0 || readIslInd > islandNum)
                                throw new NumberFormatException();

                            forwardViewToVirtualController(elemIndex(availableIslands[readIslInd - 1], model.getIslands()));
                            return;
                        }

                        catch (NumberFormatException ignored) {
                            islInd = new Tuple<>(read, true);
                            continue;
                        }
                    }
                }
            }

            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void requestChooseDiningRoom(Color[] compatibleDiningRoomTable) {
        contextSwitch(() -> {
            try {
                int       numOfColors = compatibleDiningRoomTable.length;
                ModuloNat colorSel    = new ModuloNat(numOfColors);

                hideCursor(writer);

                // Main menu
                MENU:
                while (!interrupted()) {
                    List<String> menu = new ArrayList<>(logoList);
                    menu.add("");
                    menu.add("Please select the color of the dining room you'll pick the student:");

                    List<String> colorOptions = new ArrayList<>(numOfColors);

                    Arrays.stream(compatibleDiningRoomTable).forEach(color ->
                        colorOptions.add("> " + colorString(capitalize(color.name()), getStudentColor(color, false))));

                    underlineElem(colorOptions, colorSel.value());
                    menu.addAll(colorOptions);

                    display.clear();
                    display.updateAnsi(menu, 0);

                    // Key pressing loop
                    KEYPRESS:
                    while (!interrupted()) {
                        // Interpret the availale key
                        switch (parseKey(keyStream)) {
                            // Tab, down arrow or right arrow: go to next menu item
                            case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                                colorSel.next();
                                continue MENU;
                            }

                            // Up arrow or left arrow: go to the previous menu item
                            case UP_ARROW, LEFT_ARROW -> {
                                colorSel.prev();
                                continue MENU;
                            }

                            case ONE -> {
                                colorSel.set(0);
                                continue MENU;
                            }

                            case TWO -> {
                                if (numOfColors < 2)
                                    continue KEYPRESS;

                                colorSel.set(1);
                                continue MENU;
                            }

                            case THREE -> {
                                if (numOfColors < 3)
                                    continue KEYPRESS;

                                colorSel.set(2);
                                continue MENU;
                            }

                            case FOUR -> {
                                if (numOfColors < 4)
                                    continue KEYPRESS;

                                colorSel.set(3);
                                continue MENU;
                            }

                            case FIVE -> {
                                if (numOfColors < 5)
                                    continue KEYPRESS;

                                colorSel.set(4);
                                continue MENU;
                            }

                            // Return: select the item
                            case ENTER -> {
                                forwardViewToVirtualController(compatibleDiningRoomTable[colorSel.value()]);
                                return;
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
        });
    }

    @Override
    public void notifyGameInProgress() {
        virtualController.close();

        display.clear();
        display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

        writer.println();
        writer.println("Another game is already in progress!");
        writer.println("Please come back later");
        writer.println();

        try {
            hideCursor(writer);
            writer.print("Press any key to continue...");
            keyStream.read();
            showCursor(writer);
        }

        // Should never happen
        catch (IOException ignored) {}
    }

    @Override
    public void notifyGameStart() {
        contextSwitch(() -> {
            display.clear();
            display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

            writer.println();
            writer.println("The game is now starting!");
            writer.println("Please wait");
            writer.println();
        });
    }

    @Override
    public void notifyPlayerDisconnection() {
        contextSwitch(() -> {
            display.clear();
            display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

            writer.println();
            writer.println("You have been disconnected from the server because another player left the game!");
            writer.println("Please come back soon");
            writer.println();

            try {
                hideCursor(writer);
                writer.print("Press any key to continue...");
                keyStream.read();
                showCursor(writer);
            }

            // Should never happen
            catch (IOException ignored) {}
        });
    }

    @Override
    public void notifyStartGameTurn(String playingPlayerUsername) {
        contextSwitch(() -> {
            display.clear();
            display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

            writer.println();
            writer.println("It is now " + capitalize(playingPlayerUsername) + "'s turn!");
            writer.println("Please wait for your's");
            writer.println();

            try {
                sleep(1500);
            }

            catch (InterruptedException ignored) {}

            updateModel(model, null);
        });
    }

    @Override
    public void notifyEndOfTurn() {
        display.clear();
        display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

        writer.println();
        writer.println("Your turn has now ended!");
        writer.println("Please wait for the other's to end");
        writer.println();


        try {
            sleep(1500);
        }

        catch (InterruptedException ignored) {}

        updateModel(model, null);
    }

    @Override
    public void signalWinner(Player winner) {
        contextSwitch(() -> {
            display.clear();
            display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

            writer.println();
            writer.println("The game has now ended!");
            writer.println("Please, a round of applause for the winner, " + colorString(capitalize(winner.getUsername()), GREEN, UNDERLINE) + "!");
            writer.println();

            try {
                hideCursor(writer);
                writer.print("Press any key to continue...");
                keyStream.read();
                showCursor(writer);
            }

            // Should never happen
            catch (IOException ignored) {}

            finally {
                virtualController.close();
            }
        });
    }

    @Override
    public void signalWinner(List<Player> team) {
        contextSwitch(() -> {
            display.clear();
            display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

            writer.println();
            writer.println("The game has now ended!");
            writer.println("Please, a round of applause for the winning team, " +
                colorString(capitalize(team.get(0).getUsername()), GREEN, UNDERLINE) +
                " and " +
                colorString(capitalize(team.get(1).getUsername()), GREEN, UNDERLINE) +
                "!");
            writer.println();

            try {
                hideCursor(writer);
                writer.print("Press any key to continue...");
                keyStream.read();
                showCursor(writer);
            }

            // Should never happen
            catch (IOException ignored) {}

            finally {
                virtualController.close();
            }
        });
    }

    @Override
    public void signalDraw(List<Player> drawers) {
        contextSwitch(() -> {
            display.clear();
            display.updateAnsi(logoList, (terminal.getWidth() + 1) * logoList.size());

            writer.println();
            writer.println("The game has ended in a draw!");
            StringBuilder sb = new StringBuilder("Please, a round of applause for the winners, ");
            int i = 0;
            for (; i < drawers.size() - 2; ++i)
                sb.append(colorString(capitalize(drawers.get(i).getUsername()), GREEN, UNDERLINE)).append(", ");
            sb.append(colorString(capitalize(drawers.get(i).getUsername()), GREEN, UNDERLINE)).append(" and ");
            ++i;
            sb.append(colorString(capitalize(drawers.get(i).getUsername()), GREEN, UNDERLINE)).append("!");
            writer.println(sb);
            writer.println();

            try {
                hideCursor(writer);
                writer.print("Press any key to continue...");
                keyStream.read();
                showCursor(writer);
            }

            // Should never happen
            catch (IOException ignored) {}

            finally {
                virtualController.close();
            }
        });
    }

    // endregion

}
