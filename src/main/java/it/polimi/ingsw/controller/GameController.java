package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.controller.save.GameSave;
import it.polimi.ingsw.controller.state.GameStateThread;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class GameController {
    private static ControllerData data;
    private static boolean activeGame;

    private static GameStateThread gameStateThread;

    //TODO

    public static void main(String[] args) throws IOException {
        data = ControllerData.getInstance();
        activeGame = false;

        ServerSocket serverSocket = new ServerSocket(6554); //TODO: gestire IOException

        while (true) {
            VirtualView view = new VirtualView(serverSocket.accept()); //TODO: gestire IOException

            if (activeGame) {
                view.sendMessage(new GameCommandGameProgress());
                view.close();
            }

            else
                try {
                    //TODO: gestione configurazione iniziale regole

                    if (addPlayer(view)) {
                        activeGame = true;
                        sendGameStart();
                        (gameStateThread = new GameStateThread()).start();
                    }
                }

                // If there's a SocketException whilst trying to add the player it means that it's no longer available
                // In this case, just go on normally, since the model hasn't been modified yet
                catch (SocketException ignored) {}
        }
    }

    public static void signalPlayerDisconnected(VirtualView player) {
        //TODO: salvare il gioco interrompendo il DFA
        GameSave.saveGame(/*TODO*/ null);
        gameStateThread.interrupt();
        //TODO: mandare a tutti il messaggio di fine partita
    }

    public static synchronized void signalEndGame() {
        activeGame = false;
        ControllerData.nukeInstance();
        data = ControllerData.getInstance();
    }

    // Returns true if the correct number of players has been reached
    public static boolean addPlayer(VirtualView view) throws SocketException {
        Player[] players = data.getPlayersOrder();
        int numOfPlayers = data.getNumOfPlayers();

        String username  = (String) view.sendRequest(new GameCommandRequestValueClient(GameCommandValues.USERNAME)).executeCommand();
        Wizard wizard    = (Wizard) view.sendRequest(new GameCommandRequestValueClient(GameCommandValues.WIZARD))  .executeCommand();

        boolean teamMode = numOfPlayers == 4;

        for (int i = 0; i < numOfPlayers; ++i) {
            if (players[i] == null) {
                players[i] =
                    buildPlayer(
                        data.getExpertMode(),
                        teamMode,
                        i,
                        username,
                        wizard,
                        // If there are three players, each one gets 6 towers, else if there are two players, each one takes 8 towers,
                        // otherwise, there are four players and the first member of each team gets 8 towers, whilst its teammate gets 0
                        numOfPlayers == 3 ? 6 : numOfPlayers == 2 || i <= 1 ? 8 : 0,
                        // The entrance's size is 7 for two or four players games, 9 otherwise
                        numOfPlayers == 3 ? 9 : 7);

                if (teamMode && i > 1)
                    linkTeams(players, i);

                // Add the newly created player to the isomorphism (Player, View)
                data.getPlayerViewMap().put(players[i], view);

                return i + 1 == numOfPlayers;
            }
        }

        // Should never be reached
        throw new IllegalStateException();
    }

    private static Player buildPlayer(boolean expertMode, boolean teamMode, int id, String username, Wizard wizard, int towerCount, int entranceSize) {
        if (!expertMode && !teamMode) return new Player          (id, username, wizard, new SchoolBoard(TowerColor.fromInt(id),          towerCount, new Entrance(entranceSize), new DiningRoom()));
        if (!expertMode /*teamMode*/) return new PlayerTeam      (id, username, wizard, new SchoolBoard(TowerColor.fromInt(id % 2), towerCount, new Entrance(entranceSize), new DiningRoom()));
        if (/*expertMode*/ !teamMode) return new PlayerExpert    (id, username, wizard, new SchoolBoard(TowerColor.fromInt(id),          towerCount, new Entrance(entranceSize), new DiningRoom()));
        /*otherwise*/                 return new PlayerTeamExpert(id, username, wizard, new SchoolBoard(TowerColor.fromInt(id % 2), towerCount, new Entrance(entranceSize), new DiningRoom()));
    }

    private static void linkTeams(Player[] players, int index) {
        if (players[index] instanceof PlayerTeamExpert p) {
            PlayerTeamExpert teamMember = (PlayerTeamExpert) players[index - 2];
            p.setTeamMember(teamMember);
            teamMember.setTeamMember(p);
        }

        else if (players[index] instanceof PlayerTeam p) {
            PlayerTeam teamMember = (PlayerTeam) players[index - 2];
            p.setTeamMember(teamMember);
            teamMember.setTeamMember(p);
        }

        else
            // Should never be reached
            throw new IllegalStateException();
    }

    public static void sendGameStart() {
        //TODO
    }

    //TODO
    public static Object requestValue(GameCommandValues value) {
        return switch (value) {
            case MOTHERNATURE -> ControllerData.getInstance().getGameModel().getMotherNaturePosition();
            default           -> throw new IllegalArgumentException();
        };
    }
}
