package it.polimi.ingsw.client.virtualController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.Address;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class VirtualController extends Thread implements Closeable {
    private static final Gson messageBuilder = new GsonBuilder().setPrettyPrinting().create();

    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public VirtualController(Address address) throws IOException {
        socket       = new Socket(address.ipAddress(), address.port());
        inputStream  = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void close() throws IOException {
        interrupt();
        socket.close();
    }

    public void run() {
        while (!interrupted()) {
            inputStream.readUTF(); //TODO: interfaccia interna comandi
        }
    }
}
