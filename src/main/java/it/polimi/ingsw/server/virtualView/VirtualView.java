package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.message.MessageType;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.command.GameCommand;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Class describing the general virtual view interface, encapsulating the sockets
 * @author Mattia Martelli
 */
public class VirtualView extends Thread implements AutoCloseable {
    // A couple of useful constants, representing the "ping" and "pong" messages
    private static final Message PING_MESSAGE = new Message(MessageType.PING, null);
    private static final Message PONG_MESSAGE = new Message(MessageType.PONG, null);


    // Fields that hold the communication data
    private final Socket           socket;
    private final DataInputStream  inputStream;
    private final DataOutputStream outputStream;

    public VirtualView(Socket socket) throws SocketException {
        try {
            this.socket  = socket;
            inputStream  = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            // Set a timeout of 1 second for every input stream read
            socket.setSoTimeout(1000);

            start();
        }

        // If the socket cannot be opened, throw an exception
        catch (Exception e) {
            throw new SocketException();
        }
    }

    /**
     * Close the connection with the concrete view and end the thread
     */
    public void close() {
        try {
            if (isInterrupted())
                return;

            // Stop the thread and close the socket
            interrupt();
            socket.close();
        }

        // If the close throws an exception it means the socket is already closed, so I don't care
        catch (Exception ignored) {}
    }

    /**
     * Send a command to the view, without waiting for a response
     * @param command the command to be sent
     */
    public void sendMessage(GameCommand command) {
        try {
            String message = MessageBuilder.commandToJson(command);
            outputStream.writeUTF(message);
        }

        catch (Exception ignored) {
            endThread();
        }
    }

    /**
     * Wait for a command from the view
     * @return The command received from the client
     * @throws SocketException Throws a socket exception if the connection has been closed
     */
    public synchronized GameCommand getMessage() throws SocketException {
        Message msg;

        while (!isInterrupted()) {
            try {
                msg = MessageBuilder.jsonToMessage(inputStream.readUTF());

                if (msg == null)
                    sendPing();

                else if (msg.equals(PONG_MESSAGE))
                    msg = null;

                else
                    return MessageBuilder.messageToCommand(msg);
            }

            catch (EOFException ignored) {
                try {
                    sleep(1000);
                    sendPing();
                }

                catch (NotPongException e) {
                    e.getCommand().executeCommand();
                }

                catch (Exception e) {
                    endThread();
                }
            }

            catch (SocketTimeoutException ignored) {
                try {
                    sendPing();
                }

                catch (NotPongException e) {
                    return e.getCommand();
                }

                catch (SocketException ignore) {
                    endThread();
                }
            }

            catch (Exception ignored) {
                endThread();
                throw new SocketException(); //TODO: controllare che tutti la gestiscano correttamente
            }
        }

        throw new SocketException();
    }

    /**
     * Send a command to the client and wait its response
     * @param request The command to send
     * @return The command received
     * @throws SocketException Throws a socket exception if the connection has been closed
     */
    public synchronized GameCommand sendRequest(GameCommand request) throws SocketException {
        sendMessage(request);
        return getMessage();
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                Message msg = MessageBuilder.jsonToMessage(inputStream.readUTF());

                if (msg == null)
                    sendPing();

                else if (!msg.equals(PONG_MESSAGE))
                    MessageBuilder.messageToCommand(msg).executeCommand(); //TODO: gestione delle richieste
            }

            catch (NotPongException e) {
                e.getCommand().executeCommand();
            }

            // If the socket timed out whilst reading, try sending a ping to see if the client is still alive
            catch (SocketTimeoutException ignored) {
                try {
                    sendPing();
                }

                catch (SocketException e) {
                    endThread();
                }

                catch (NotPongException e) {
                    e.getCommand().executeCommand();
                }
            }

            // If I can't sleep it's because I've been interrupted: just let the while end gracefully, ending the thread
            catch (Exception ignored) {
                endThread();
            }
        }
    }

    /**
     * A simple function to send a ping to check whether the view's still alive
     * @throws NotPongException Throws an exception to signal that a command different from a ping response as been received
     * @throws SocketException Throws a socket exception if the connection has been closed
     */
    private synchronized void sendPing() throws NotPongException, SocketException {
        Message pong = null;

        try {
            outputStream.writeUTF(MessageBuilder.messageToJson(PING_MESSAGE));
            pong = MessageBuilder.jsonToMessage(inputStream.readUTF());
        }

        catch (Exception e) {
            endThread();
        }

        if (pong == null)
            endThread();

        else if (!pong.equals(PONG_MESSAGE))
            throw new NotPongException(MessageBuilder.messageToCommand(pong));
    }

    private void endThread() {
        GameController.signalPlayerDisconnected(this);
        close();
    }
}
