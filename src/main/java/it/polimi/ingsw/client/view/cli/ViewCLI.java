package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.View;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.PrintWriter;

public abstract class ViewCLI implements View {
    Terminal terminal;

    PrintWriter writer;

    public void initialize() {
        try {
            terminal = TerminalBuilder.builder()
                .name("Eriantys")
                .encoding("UTF-8")
                .nativeSignals(true)
                .type("ansi")
                .jna(true)
                .build();

            writer = terminal.writer();
            writer.println(
                """
                 _____      _             _
                | ____|_ __(_) __ _ _ __ | |_ _   _ ___
                |  _| | '__| |/ _` | '_ \\| __| | | / __|
                | |___| |  | | (_| | | | | |_| |_| \\__ \\
                |_____|_|  |_|\\__,_|_| |_|\\__|\\__, |___/
                                              |___/
                """);
            writer.flush();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpBoard() {

    }

    public Address getAddress() throws IOException {
        // Both tuples contain a string representing a candidate for the value
        // and a bool representing whether that value is acceptable
        Tuple<String, Boolean> ip   = new Tuple<>(null, false);
        Tuple<String, Boolean> port = new Tuple<>(null, false);

        do {
            //Update the console with the logo
            display.clear();
            display.updateAnsi(Constants.logoList, (terminal.getWidth() + 1) * Constants.logoList.size());

            // Print the request for the IP
            // NB: the last space is used to solve a bug, because otherwise backspace would delete the whole line
            writer.println("Welcome to the magical world of Eriantys!");
            writer.println();
            writer.print("Please enter the IP address of the server you're trying to connect to:");
            writer.print(" ");

            // Check if there is an illegal candidate for the value
            if (ip.right()) {
                // Print the candidate on a red background with an error message
                writer.print(Ansi.colorString(ip.left() + " is not a valid IP address", Ansi.BACKGROUND_RED));

                // Reset the tuple
                ip = new Tuple<>(null, false);

                // Wait for a generic key, hiding the cursor
                writer.print(Ansi.CURSOR_HIDE);
                keyStream.read();
                writer.print(Ansi.CURSOR_SHOW);

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
            }

            // Otherwise, there already is a correct candidate, so print it
            else
                writer.println(ip.left());

            // Print the request for the port number
            // NB: the last space is used to solve a bug, because otherwise backspace would delete the whole line
            writer.println();
            writer.print("Please enter the server port:");
            writer.print(" ");

            // Check if there is an illegal candidate for the value
            if (port.right()) {
                // Print the candidate on a red background with an error message
                writer.print(Ansi.colorString(port.left() + " is not a valid port number", Ansi.BACKGROUND_RED));

                // Reset the tuple
                port = new Tuple<>(null, false);

                // Wait for a generic key, hiding the cursor
                writer.print(Ansi.CURSOR_HIDE);
                keyStream.read();
                writer.print(Ansi.CURSOR_SHOW);

                continue;
            }

            // Otherwise, read the port and try to parse it
            String readPort = reader.readLine();
            try {
                int portInt = Integer.parseInt(readPort);

                // Filter for well known ports, that will not be accepted
                if ((portInt > 1023 && portInt < 65536))
                    return new Address(ip.left(), portInt);
            }

            // If the port cannot be parsed, simply ignore the exception
            catch (NumberFormatException ignored) {}

            // The port number is illegal because of parsing or for beign well known, so update the tuple accordingly
            port = new Tuple<>(readPort, true);
        } while (true);
    }

    public void signalConnectionError() {

    }
}
