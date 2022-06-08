package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.GameCommand;
import it.polimi.ingsw.server.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * State representing the placement of the tokens on the board, following the model's initialization
 * @author Mattia Martelli
 */
public final class GameStatePlaceTokens implements GameStateSetup {
    public GameState nextState() {
        return ControllerData.getInstance().getExpertMode() ? new GameStateExpertInitialization() : new GameStateFillClouds();
    }

    public void executeState() {
        GameModel model = ControllerData.getInstance().getGameModel();
        Bag       bag   = model.getBag();

        // Set Mother Nature's position to a random number in [0, 11]
        int motherNaturePosition = (int) (Math.random() * 11);
        model.setMotherNaturePosition(motherNaturePosition);

        // Set the initial students on the islands
        setIslandsStudents(model, bag, motherNaturePosition);

        // Refill the bag with the remaining students
        bag.setStudentCounters(24);

        // Set the students in the school board's entrances
        setEntrancesStudents(bag);

        try {
            // Get all the Object/Fields have to be sent to each player's Client
            InfoMap placedTokens = packPlacedTokens();

            // Send those Object/Fields via a SendMessage message, through the Network
            for (Player player : ControllerData.getInstance().getGameModel().getPlayer()) {
                VirtualView playerView = ControllerData.getInstance().getPlayerView(player);
                GameCommand tokensUpdate = new GameCommandSendInfo(placedTokens);
                playerView.sendMessage(tokensUpdate);
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    private void setIslandsStudents(GameModel model, Bag bag, int motherNaturePosition) {
        try {
            // Use the bag to generate a random sequence of colors whilst following the restrictions
            bag.setStudentCounters(2);
            BagResult initialStudents = bag.drawStudents(10);

            if (initialStudents.emptyBag() || initialStudents.drawnStudents().length < 10)
                throw new IllegalStateException("Bag not correctly filled using the corresponding functions");

            // Iterate through the islands, adding the students
            // Note: "j++" executes the increment at the end of the statement, after the current value ("j") has been used
            for (int i = 0, j = 0; i < 12; ++i)
                if (i != motherNaturePosition && i != ((motherNaturePosition + 6) % 11))
                    model.getIsland(i).setStudentCounters(initialStudents.drawnStudents()[j++], 1);
        }

        catch (EmptyBagException e) {
            throw new IllegalStateException("Bag not correctly filled using the corresponding functions");
        }

        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void setEntrancesStudents(Bag bag) {
        try {
            Player[] players          = ControllerData.getInstance().getGameModel().getPlayer();
            int      entranceCapacity = players.length == 3 ? 9 : 7;

            // Iterate through all the players, adding a random set of students to the entrances
            for (Player player : players) {
                BagResult br = bag.drawStudents(entranceCapacity);

                if (br.emptyBag() || br.drawnStudents().length < entranceCapacity)
                    throw new IllegalStateException("Bag not correctly filled using the corresponding functions");

                player.getSchoolBoard().getEntrance().setStudents(br.drawnStudents());
            }
        }

        catch (EmptyBagException e) {
            throw new IllegalStateException("Bag not correctly filled using the corresponding functions");
        }

        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get all the Tokens that have to be sent to each Client and save them in a Map
     */
    private InfoMap packPlacedTokens() {
        InfoMap placedTokens = new InfoMap();

        /* TODO: [ClientCache] Caricare tutte le informazioni in una mappa, facendo attenzione ai puntatori disaccoppiati (player in team) */
        // Get all the objects/fields each player's Client need to know
        GameModel placedModel = ControllerData.getInstance().getGameModel();
        // ...

        // Add to placedTokens Map all the object/fields that will be sent to the client
        placedTokens.put(GameValues.MODEL, placedModel);
        // ...

        return placedTokens;
    }
}
