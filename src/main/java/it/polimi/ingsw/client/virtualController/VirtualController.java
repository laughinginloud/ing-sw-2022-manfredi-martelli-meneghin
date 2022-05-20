package it.polimi.ingsw.client.virtualController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.message.MessageType;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.server.controller.command.GameCommandValues;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;

public class VirtualController extends Thread implements Closeable {
    private static final Gson messageBuilder = new GsonBuilder().setPrettyPrinting().create();

    private final Socket           socket;
    private final DataInputStream  inputStream;
    private final DataOutputStream outputStream;

    private final View             view;

    public VirtualController(Address address, View view) throws IOException {
        socket       = new Socket(address.ipAddress(), address.port());
        inputStream  = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        this.view    = view;
    }

    public void close() {
        try {
            interrupt();
            socket.close();
        }

        // If the close throws an exception it means the socket is already closed, so I don't care
        catch (IOException ignored) {}
    }

    public void run() {
        while (!interrupted()) {
            try {
                messageInterpreter(messageBuilder.fromJson(inputStream.readUTF(), Message.class));
            }

            catch (SocketTimeoutException ignored) {/*FIXME: pensare a ping*/}

            catch (Exception e) {
                view.signalConnectionError();
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            outputStream.writeUTF(messageBuilder.toJson(message));
        }

        catch (Exception ignored) {
            //TODO
        }
    }

    private void messageInterpreter(Message message) {
        switch (message.type()) {
            case PING -> sendMessage(new Message(MessageType.PONG, null));

            case REQUESTVALUE -> {
                //TODO
            }

            case SENDINFO -> {
                Map<GameCommandValues, Object> map = (Map<GameCommandValues, Object>) message.value();

                if (map.containsKey(GameCommandValues.MODEL)) {
                    Client.setModel((GameModel) map.get(GameCommandValues.MODEL));
                    map.remove(GameCommandValues.MODEL);
                }

                map.forEach((v, o) -> modifyModelInfo(Client.getModel(), v, o));

                view.setUpBoard(); //TODO: cambiare in notifymodelchanges
            }

            case ILLEGALMESSAGE -> view.signalConnectionError(); //FIXME: cambiare in illegalmessage, tanto server rimanda comando

            case ILLEGALVALUE   -> view.signalConnectionError(); //FIXME: cambiare in illegalvalue, tanto server rimanda comando

            case REQUESTACTION -> {
                //TODO
            }

            case GAMEWINNER -> view.signalConnectionError(); //TODO: cambiare in signalwinner //FIXME: correggere sendinfo dei winner/s/cassetti

            case GAMEDRAW -> view.signalConnectionError(); //TODO: cambiare in signaldraw

            case GAMESTART -> view.setUpBoard(); //TODO: cambiare in startgame

            case GAMEPROGRESS -> {
                view.signalConnectionError(); //TODO: cambiare in signalgameprogress
                close();
            }
        }
    }

    private void modifyModelInfo(GameModel model, GameCommandValues value, Object object) {
        switch (value) {
            case ISLANDARRAY -> model.setIsland((Island[]) object);

            case PLAYERARRAY -> model.setPlayer((Player[]) object);

            case CLOUDARRAY  -> model.setCloudTile((CloudTile[]) object); //Utilizzo: scelta tra nuvole, sostituzione dati model

            case CHARACTERCARDARRAY -> model.setCharacterCard((CharacterCard[]) object); //Utilizzo: scelta, sostuzione

            case MOTHERNATURE -> model.setMotherNaturePosition((int) object);

            case ENTRANCE -> {/*TODO: gestire anche player (indice?)*/
                Tuple<Integer, Entrance> tuple = (Tuple<Integer, Entrance>) object;
                model.getPlayer(tuple.left()).getSchoolBoard().setEntrance(tuple.right());
            }

            case ENTRANCEARRAY -> {/*TODO: entrance ordinate per player*/
                Entrance[] entrances = (Entrance[]) object;
                for (int i = 0; i < entrances.length; ++i)
                    model.getPlayer(i).getSchoolBoard().setEntrance(entrances[i]);
            }

            case DININGROOM -> {
                Tuple<Integer, DiningRoom> tuple = (Tuple<Integer, DiningRoom>) object;
                model.getPlayer(tuple.left()).getSchoolBoard().setDiningRoom(tuple.right());
            }

            case DININGROOMARRAY -> {
                DiningRoom[] diningRooms = (DiningRoom[]) object;
                for (int i = 0; i < diningRooms.length; ++i)
                    model.getPlayer(i).getSchoolBoard().setDiningRoom(diningRooms[i]);
            }

            case SCHOOLBOARDARRAY -> {
                SchoolBoard[] schoolBoards = (SchoolBoard[]) object;
                for (int i = 0; i < schoolBoards.length; ++i)
                    model.getPlayer(i).setSchoolBoard(schoolBoards[i]);
            }

            case GLOBALPROFESSORTABLE -> model.setGlobalProfessorTable((GlobalProfessorTable) object);

            case COINPOOL -> model.setCoinPool((int) object);

            //TODO: hmm
            // Decidere come gestire i casi fisicamente impossibili
            case REMAININGTOWER -> {
            }

            case ISLANDNUM -> {
            }
            //ENDTODO
        }
    }
}
