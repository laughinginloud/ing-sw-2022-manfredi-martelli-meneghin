package it.polimi.ingsw.virtualView;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.command.GameCommand;

import java.io.*;
import java.net.Socket;

public class VirtualView extends Thread implements AutoCloseable {
    Socket           player;
    DataInputStream  inputStream;
    DataOutputStream outputStream;

    public VirtualView(Socket player) throws IOException {
        this.player  = player;
        inputStream  = new DataInputStream(player.getInputStream());
        outputStream = new DataOutputStream(player.getOutputStream());
        start();
    }

    public void close() throws IOException {
        player.close();
        interrupt();
    }

    public void sendMessage(GameCommand command) throws IOException {
        String message = MessageBuilder.fromCommand(command);
        outputStream.writeUTF(message);
    }

    public GameCommand getMessage() throws IOException {
        String message = inputStream.readUTF();
        return new Gson().fromJson(message, GameCommand.class);
    }

    public GameCommand sendRequest(GameCommand request) throws IOException {
        sendMessage(request);
        return getMessage();
    }

    public void run() {
        while (!interrupted()) {
            try {
                GameCommand command = getMessage();
                command.executeCommand();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
