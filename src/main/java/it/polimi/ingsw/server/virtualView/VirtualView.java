package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.message.MessageType;
import it.polimi.ingsw.common.utils.Methods;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.command.GameCommand;
import it.polimi.ingsw.server.controller.command.GameCommandInterruptGame;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Class describing the general virtual view interface, encapsulating the sockets
 * @author Mattia Martelli
 */
public class VirtualView extends Thread implements AutoCloseable {

    // region Constants and fields

    // A couple of useful constants, representing the "ping" and "pong" messages

    /**
     * The "ping" message
     */
    private static final Message PING_MESSAGE = new Message(MessageType.PING, null);

    /**
     * The "pong" message
     */
    private static final Message PONG_MESSAGE = new Message(MessageType.PONG, null);

    /**
     * A constant that represents whether assertions are currently enabled
     */
    private static final boolean assertions = Methods.assertionsEnabled();

    /**
     * Signal whether a pong has been received
     */
    private       boolean          pongReceived;

    private final Socket           socket;
    private final DataInputStream  inputStream;
    private final DataOutputStream outputStream;

    // endregion

    // region Constructor and thread methods

    public VirtualView(Socket socket) throws SocketException {
        try {
            this.socket  = socket;
            inputStream  = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            // Set a timeout of 1/2 second for every input stream read
            socket.setSoTimeout(500);

            start();
        }

        // If the socket cannot be opened, throw an exception
        catch (Exception e) {
            throw new SocketException();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Message msg = getMessage();

                if (msg.equals(PONG_MESSAGE))
                    pongReceived = true;

                else
                    MessageBuilder.messageToCommand(msg).executeCommand();
            }

            catch (NotPongException e) {
                MessageBuilder.messageToCommand(e.getMsg()).executeCommand();
            }

            // If I can't sleep it's because I've been interrupted: just let the while end gracefully, ending the thread
            catch (Exception ignored) {
                endThread();
            }
        }
    }

    /**
     * Close the connection with the concrete view and end the thread
     */
    public void close() {
        try {
            if (isInterrupted())
                return;

            if (assertions)
                System.out.println("Player left");

            // Stop the thread and close the socket
            interrupt();
            socket.close();
        }

        // If the close throws an exception it means the socket is already closed, so I don't care
        catch (Exception ignored) {}
    }

    /**
     * End the current thread, sending a signal to the game controller
     */
    private void endThread() {
        GameController.signalPlayerDisconnected(this);
        close();
    }

    // endregion

    // region Communication methods

    /**
     * Send a command to the view, without waiting for a response
     * @param command the command to be sent
     */
    public void sendMessage(GameCommand command) {
        try {
            String message = MessageBuilder.commandToJson(command);

            synchronized (outputStream) {
                outputStream.writeUTF(message);
                outputStream.flush();
            }
        }

        catch (Exception ignored) {
            endThread();
        }
    }

    /**
     * Send a GameCommandInterruptGame to the view and close the view
     */
    public void sendInterrupt() {
        try {
            String message = MessageBuilder.commandToJson(new GameCommandInterruptGame());

            synchronized (outputStream) {
                outputStream.writeUTF(message);
                outputStream.flush();
            }
        }

        catch (Exception ignored) {}

        finally {
            close();
        }
    }

    /**
     * Wait for a command from the view
     * @return The command received from the client
     * @throws SocketException Throws a socket exception if the connection has been closed
     */
    public GameCommand getCommand() throws SocketException {
        try {
            return MessageBuilder.messageToCommand(getMessage());
        }

        catch (NotPongException e) {
            return MessageBuilder.messageToCommand(e.getMsg());
        }
    }

    /**
     * Get a message from the network
     * @return The message received
     * @throws NotPongException If a message different from "pong" was received unexpectedly
     * @throws SocketException  If the socket was terminated
     */
    private Message getMessage() throws NotPongException, SocketException {
        Message msg;

        while (!isInterrupted()) {
            try {
                String message;

                synchronized (inputStream) {
                    message = inputStream.readUTF();
                }

                if (assertions)
                    System.out.println(message);

                msg = MessageBuilder.jsonToMessage(message);

                if (msg == null)
                    sendPing();

                else if (msg.equals(PONG_MESSAGE)) {
                    pongReceived = true;
                    getMessage();
                }

                else
                    return msg;
            }

            catch (EOFException | SocketTimeoutException ignored) {
                try {
                    sendPing();
                }

                catch (NotPongException e) {
                    throw e;
                }

                catch (Exception e) {
                    endThread();
                    throw new SocketException();
                }
            }

            catch (Exception ignored) {
                endThread();
                throw new SocketException();
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
        return getCommand();
    }

    /**
     * A simple function to send a ping to check whether the view's still alive
     * @throws NotPongException Throws an exception to signal that a command different from a ping response as been received
     * @throws SocketException  Throws a socket exception if the connection has been closed
     */
    private synchronized void sendPing() throws NotPongException, SocketException {
        Message pong;

        try {
            String msg = MessageBuilder.messageToJson(PING_MESSAGE);

            synchronized (outputStream) {
                outputStream.writeUTF(msg);
                outputStream.flush();
            }

            synchronized (inputStream) {
                msg = inputStream.readUTF();
            }

            pong = MessageBuilder.jsonToMessage(msg);

            if (!pong.equals(PONG_MESSAGE))
                throw new NotPongException(pong);
        }

        catch (SocketTimeoutException e) {
            try {
                String msg;
                synchronized (inputStream) {
                    msg = inputStream.readUTF();
                }

                pong = MessageBuilder.jsonToMessage(msg);
            }

            catch (Exception ignored) {
                if (pongReceived) {
                    pongReceived = false;
                    return;
                }

                endThread();
                throw new SocketException();
            }

            if (!pong.equals(PONG_MESSAGE))
                throw new NotPongException(pong);
        }

        catch (IOException e) {
            endThread();
            throw new SocketException();
        }
    }

    // endregion

}
