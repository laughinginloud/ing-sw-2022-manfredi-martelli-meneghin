package it.polimi.ingsw;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.common.termutils.TermConstants;
import it.polimi.ingsw.common.termutils.Key;
import it.polimi.ingsw.common.termutils.Ansi;
import it.polimi.ingsw.server.Server;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Display;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.polimi.ingsw.common.termutils.Ansi.*;

/**
 * Entry point of the app and main mode menu
 * @author Mattia Martelli
 */
public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 0)
                startGame(args);

            else
                startGame();
        }

        // If the game didn't start, do not print "thanks for playing"
        catch (GameNotStarted ignored) {
            return;
        }

        System.out.println();
        System.out.println("Thanks for playing! See you soon!");
        System.out.println();
    }

    /**
     * Start the game, if possible, using the command line args
     * @param args The command line args
     * @throws GameNotStarted If the game did not start
     */
    private static void startGame(String[] args) throws GameNotStarted {
        // There's at least one arg, so start with the first
        switch (args[0].trim().toLowerCase()) {
            // Simply start the server
            case "server" -> {
                if (args.length == 1)
                    Server.main();

                else {
                    Optional<Integer> port = parsePort(args[1].trim());

                    if (port.isEmpty()) {
                        System.out.println("Illegal port number: " + args[1]);
                        throw new GameNotStarted();
                    }

                    Server.main(port.get());
                }
            }

            // The client needs a second parameter to signal the UI
            case "client" -> {
                // If there is not a second parameter, signal the user
                if (args.length == 1) {
                    System.out.println("Please specify the client mode (either CLI or GUI)");
                    throw new GameNotStarted();
                }

                // Otherwise, check that
                switch (args[1].trim().toLowerCase()) {
                    // Start the client in GUI mode
                    case "gui" -> Client.main(true);

                    // Start the client in CLI mode
                    case "cli", "cui", "tui" -> Client.main(false);

                    // Unrecognized UI: signal the user
                    default -> {
                        System.out.println("Unrecognized client mode: " + args[1]);
                        throw new GameNotStarted();
                    }
                }
            }

            // Print a simple help text on how to start the game
            case "help" -> {
                System.out.println("Usage: eriantys [server | client gui | client cli]");
                throw new GameNotStarted();
            }

            // Unrecognized mode: signal the user
            default -> {
                System.out.println("Unrecognized mode: " + args[0]);
                throw new GameNotStarted();
            }
        }
    }

    /**
     * Start the game, if possible, by asking the player directly
     * @throws GameNotStarted If the game did not start
     */
    @SuppressWarnings({"UnnecessaryLabelOnContinueStatement", "UnnecessaryContinue"})
    private static void startGame() throws GameNotStarted {
        try {
            // The interface with the concrete terminal emulator
            Terminal terminal = TerminalBuilder.builder()
                .name("Eriantys")
                .encoding("UTF-8")
                .system(true)
                .nativeSignals(true)
                //.type("screen")
                .jna(true)
                .build();

            // The interface with an abstract "screen", an isolated buffer that will show what is written
            Display display = new Display(terminal, true);
            display.resize(terminal.getHeight(), terminal.getWidth());

            // Save the current state of the terminal and gain more granular control of it
            Attributes attributes = terminal.enterRawMode();
            terminal.puts(InfoCmp.Capability.enter_ca_mode);

            // Generate the reader and writer that will be used for this menu
            InputStream inputStream = terminal.input();
            PrintWriter writer      = terminal.writer();

            // Variable that represents the currently selected menu item
            Selection selection = Selection.SERVER;

            // Used to generate the menu
            List<String> mainMenu    = new ArrayList<>();
            List<String> menuOptions = new ArrayList<>();

            hideCursor(writer);

            // Main menu loop
            MENU:
            while (true) {
                // Reset the objects
                display.clear();
                mainMenu.clear();
                menuOptions.clear();

                // Add the top text to the menu
                mainMenu.addAll(TermConstants.logoList);
                mainMenu.add("Welcome! Please select the desired mode: ");
                mainMenu.add("");

                // Add the options to the menu
                menuOptions.add("> Server");
                menuOptions.add("> Client CLI");
                menuOptions.add("> Client GUI");
                menuOptions.add("> Exit");

                // Color the one currently selected
                menuOptions.set(selection.ordinal(), Ansi.colorString(menuOptions.get(selection.ordinal()), Ansi.CYAN));

                // Fuse the entire menu
                mainMenu.addAll(menuOptions);

                // Print the entire menu
                display.updateAnsi(mainMenu, 0);

                // Key pressing loop
                KEYPRESS:
                while (true) {
                    // If no keys have been pressed just wait for them
                    //if (inputStream.available() == 0)
                    //    continue KEYPRESS;

                    // Interpret the availale key
                    switch (Key.parseKey(inputStream)) {
                        // Tab, down arrow or right arrow: go to next menu item
                        case TAB, DOWN_ARROW, RIGHT_ARROW -> {
                            selection = selection.nextItem();
                            continue MENU;
                        }

                        // Up arrow or left arrow: go to the previous menu item
                        case UP_ARROW, LEFT_ARROW -> {
                            selection = selection.prevItem();
                            continue MENU;
                        }

                        // Number one: jump to server option
                        case ONE -> {
                            selection = Selection.SERVER;
                            continue MENU;
                        }

                        // Number two: jump to CLI option
                        case TWO -> {
                            selection = Selection.CLI;
                            continue MENU;
                        }

                        // Number three: jump to GUI option
                        case THREE -> {
                            selection = Selection.GUI;
                            continue MENU;
                        }

                        // Number four: jump to exit option
                        case FOUR -> {
                            selection = Selection.EXIT;
                            continue MENU;
                        }

                        // Return: select the item
                        case ENTER -> {
                            switch (selection) {
                                // Option 1: start the server
                                case SERVER -> {
                                    showCursor(writer);
                                    display.clear();
                                    display.updateAnsi(new ArrayList<>(), 0);
                                    Server.main();
                                }

                                // Option 2: start the client in CLI mode
                                case CLI -> {
                                    showCursor(writer);
                                    Client.main(terminal, display, inputStream, attributes);
                                }

                                // Option 3: start the client in GUI mode
                                case GUI -> {
                                    showCursor(writer);
                                    Client.main(true);
                                }

                                // Option 4: exit without doing anything
                                case EXIT -> {
                                    // Before closing the program restore the terminal to its original state
                                    terminal.puts(InfoCmp.Capability.exit_ca_mode);
                                    terminal.setAttributes(attributes);
                                    showCursor(writer);
                                    terminal.close();
                                    throw new GameNotStarted();
                                }

                                // Should never happen as selection is in modulo 4
                                default -> throw new IllegalStateException("Illegal selection: " + selection);
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

            // After the game has ended, close and restore the terminal
            terminal.setAttributes(attributes);
            terminal.puts(InfoCmp.Capability.exit_ca_mode);
            terminal.close();
        }

        // Should never happen, as it would mean that the program does not have access to the terminal
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse and check whether the provided string represents a valid port number
     * @param s The string to try and parse
     * @return An optional containing the desired port, if valid
     */
    private static Optional<Integer> parsePort(String s) {
        try {
            if (!Address.checkPort(s))
                return Optional.empty();

            return Optional.of(Integer.parseInt(s));
        }

        catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    /**
     * Exception used to signal that the game has not started
     */
    private static class GameNotStarted extends Exception {}

    /**
     * Enum to represent the currently selected item
     */
    private enum Selection {
        SERVER, CLI, GUI, EXIT;

        /**
         * Get the next item
         */
        public Selection nextItem() {
            return switch (this) {
                case SERVER -> CLI;
                case CLI    -> GUI;
                case GUI    -> EXIT;
                case EXIT   -> SERVER;
            };
        }

        /**
         * Get the previous item
         */
        public Selection prevItem() {
            return switch (this) {
                case SERVER -> EXIT;
                case CLI    -> SERVER;
                case GUI    -> CLI;
                case EXIT   -> GUI;
            };
        }
    }
}
