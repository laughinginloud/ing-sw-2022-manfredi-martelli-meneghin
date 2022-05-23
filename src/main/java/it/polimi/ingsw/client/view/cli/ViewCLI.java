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

    public Address getAddress() {
        return null;
    }

    public void signalConnectionError() {

    }
}
