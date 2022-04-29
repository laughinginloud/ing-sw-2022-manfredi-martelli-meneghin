package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.*;

public class GameStatePlaceTokens implements GameStateSetup {
    public GameState nextState() {
        return ControllerData.getInstance().getExpertMode() ? new GameStateExpertInitialization() : new GameStateInitialPlayer();
    }

    public void executeState() {
        GameModel model = ControllerData.getInstance().getGameModel();
        Bag       bag   = model.getBag();

        // Set Mother Nature's position to a random number in [0, 12]
        int motherNaturePosition = (int) (Math.random() * 12);
        model.setMotherNaturePosition(motherNaturePosition);

        // Set the initial students on the islands
        setIslandsStudents(model, bag, motherNaturePosition);

        // Refill the bag with the remaining students
        bag.setStudentCounters(24);

        // Set the students in the school board's entrances
        setEntrancesStudents(bag);
    }

    private void setIslandsStudents(GameModel model, Bag bag, int motherNaturePosition) {
        try {
            // Use the bag to generate a random sequence of colors whilst following the restrictions
            bag.setStudentCounters(2);
            Color[] initialStudents = bag.drawStudents(10);

            // Iterate through the islands, adding the students
            // Note: "j++" executes the increment at the end of the statement, after the current value ("j") has been used
            for (int i = 0, j = 0; i < 12; ++i)
                if (i != motherNaturePosition && i != ((motherNaturePosition + 6) % 12))
                    model.getIsland(i).setStudentCounters(initialStudents[j++], 1);
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
            Player[] players          = ControllerData.getInstance().getPlayersOrder();
            int      entranceCapacity = players.length == 3 ? 9 : 7;

            // Iterate through all the players, adding a random set of students to the entrances
            for (Player player : players)
                player.getSchoolBoard().getEntrance().setStudents(bag.drawStudents(entranceCapacity));
        }

        catch (EmptyBagException e) {
            throw new IllegalStateException("Bag not correctly filled using the corresponding functions");
        }

        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
