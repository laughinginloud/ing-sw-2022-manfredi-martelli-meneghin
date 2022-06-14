package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.server.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class containing the initialization procedure of ExpertMode feature
 * @author Sebastiano Meneghin
 */
public final class GameStateExpertInitialization implements GameStateSetup {
    public GameState nextState() {
        return new GameStateFillClouds();
    }

    public void executeState() {
        ControllerData  data           = ControllerData.getInstance();
        GameModel       model          = data.getGameModel();
        Player[]        players        = model.getPlayer();
        CharacterCard[] characterCards = model.getCharacterCards();

        setStudentsOnCards(characterCards, model);
        setCoins(players, model);
        data.setCardStrategies(Arrays.stream(characterCards).map(CharacterCardStrategy::build).toArray(CharacterCardStrategy[]::new));

        try {
            for (Player player : players) {
                // Get the virtualView of the player we need to send the updated information to
                VirtualView playerView = ControllerData.getInstance().getPlayerView(player);

                int coinPool = model.getCoinPool();

                // Creates the Map to send via GameCommand and adds updated CharacterCards, CoinPool and PlayerCoinCounts
                InfoMap updatedExpertInfo = new InfoMap();
                updatedExpertInfo.put(GameValues.CHARACTERCARDARRAY, characterCards   );
                updatedExpertInfo.put(GameValues.COINPOOL,           coinPool         );
                updatedExpertInfo.put(GameValues.PLAYERARRAY,        model.getPlayer());

                playerView.sendMessage(new GameCommandSendInfo(updatedExpertInfo));
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * Set students to CharacterCardStudent that contain an array of students as attribute
     * @param characterCards Array of characterCard that could contain the specific CharacterCardStudent
     * @param model GameModel which the CharacterCard are part of
     */
    private void setStudentsOnCards(CharacterCard[] characterCards, GameModel model){
        try {
            int numOfRequiredStudents;
            BagResult studentsToSet;

            //Select numOfRequiredStudents depending on the CharacterCard's character
            for (CharacterCard characterCard : characterCards) {
                switch (characterCard.getCharacter()) {
                    case MONK, PRINCESS -> numOfRequiredStudents = 4;
                    case JESTER         -> numOfRequiredStudents = 6;
                    default             -> numOfRequiredStudents = 0;
                }

                //Set numOfRequiredStudents students to the CharacterCard when it's needed
                if (numOfRequiredStudents != 0) {
                    studentsToSet = model.getBag().drawStudents(numOfRequiredStudents);
                    for (int j = 0; j < numOfRequiredStudents; j++) {
                        if (characterCard instanceof CharacterCardStudent c)
                            c.setStudents(studentsToSet.drawnStudents()[j], j);
                    }
                }
            }
        }

        catch (EmptyBagException e) {
            throw new IllegalStateException("Bag not correctly filled using the corresponding functions");
        }

        catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * Give a coin to each player, removing the corresponding amount from the model
     * @param players Array of player to add a single coin to
     * @param model GameModel which the players are part of
     */
    private void setCoins(Player[] players, GameModel model) {
        for (Player player : players) {
            if (player instanceof PlayerExpert p)
                p.setCoinCount(1);

            else if ((player instanceof PlayerTeamExpert p))
                p.setCoinCount(1);

            else
                throw new IllegalStateException("The players were not correctly created: use PlayerExpert or PlayerExpertTeam when using the expert ruleset");
        }

        model.decreaseCoinPool(players.length);
    }
}
