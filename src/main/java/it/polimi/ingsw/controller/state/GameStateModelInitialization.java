package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.*;

/**
 * State representing the initial model initialization
 * @author Mattia Martelli
 */
public class GameStateModelInitialization implements GameStateSetup {
    public GameState nextState() {
        return new GameStatePlaceTokens();
    }

    public void executeState() {
        ControllerData data = ControllerData.getInstance();
        data.setGameModel(new GameModel(data.getNumOfPlayers(), data.getExpertMode()));

        GameModel model   = data.getGameModel();
        Player[]  players = ControllerData.getInstance().getPlayersOrder();
        for (int i = 0; i < players.length; ++i)
            model.setPlayer(players[i], i);

        if (data.getNumOfPlayers() == 4)
            setMembers(players);

        if (data.getExpertMode())
            setCoins(players, model);
    }

    /**
     * Set up the teams (0, 2) and (1, 3)
     */
    private void setMembers(Player[] players) {
        for (int i = 0; i < 4; ++i) {
            if (players[i] instanceof PlayerTeam p)
                p.setTeamMember(players[(i + 2) % 4]);

            else if (players[i] instanceof PlayerTeamExpert p)
                p.setTeamMember(players[(i + 2) % 4]);

            else
                throw new IllegalStateException("The players were not correctly created: use PlayerTeam or PlayerTeamExpert when there are four players");
        }
    }

    /**
     * Give a coin to each player, removing the corresponding amount from the model
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
