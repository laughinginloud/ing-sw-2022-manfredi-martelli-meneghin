package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.termutils.Ansi;
import it.polimi.ingsw.common.termutils.TermConstants;
import it.polimi.ingsw.common.utils.Tuple;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void launchUI() {
        //TODO: decidere cosa fare di questa funzione
        /*
        try {
            terminal = TerminalBuilder.builder()
                .name("Eriantys")
                .encoding("UTF-8")
                .nativeSignals(true)
                .type("screen")
                .jna(true)
                .build();

            display = new Display(terminal, true);
            display.resize(terminal.getHeight(), terminal.getWidth());

            reader  = LineReaderBuilder.builder().terminal(terminal).build();
            writer  = terminal.writer();

            savedAttributes = terminal.enterRawMode();
            terminal.puts(InfoCmp.Capability.enter_ca_mode);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
         */
    }

    public void playExitMenu() {
        reader.readLine();
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

    public void setUpBoard() {

    }

    public Address requestAddress() throws IOException {
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
                if (Address.checkPort(port.left()))
                    return new Address(ip.left(), Address.parsePort(port.left()));
            }

            // If the port cannot be parsed, simply ignore the exception
            catch (NumberFormatException ignored) {}

            // The port number is illegal because of parsing or for beign well known, so update the tuple accordingly
            port = new Tuple<>(readPort, true);
        } while (true);
    }

    public void signalConnectionError() {

    }

    public void updateModel(GameModel model, Set<GameValues> updatedValues) {

    }

    @Override
    public void setModel(GameModel model) {

    }

    public GameModel getModel() {
        return null;
    }

    @Override
    public Player getLocalPlayer() {
        return null;
    }

    public void askAddress() {

    }

    public void askRules() {

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

    public void forwardViewToVirtualController(Object infoToSend) {

    }

    @Override
    public void setUsernameVirtualController(String readUsername) {

    }

    public void setVirtualController(VirtualController virtualController) {

    }

    public void signalWinner(Player winner) {

    }

    public void signalWinner(List<Player> team) {

    }

    public void signalDraw(List<Player> drawers) {

    }
}
