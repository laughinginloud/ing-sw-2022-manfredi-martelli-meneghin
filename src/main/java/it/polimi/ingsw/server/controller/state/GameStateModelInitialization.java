package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.GameModel;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.common.model.PlayerTeam;
import it.polimi.ingsw.common.model.PlayerTeamExpert;

/**
 * State representing the initial model initialization
 * @author Mattia Martelli
 */
public final class GameStateModelInitialization implements GameStateSetup {
    public GameState nextState() {
        return new GameStatePlaceTokens();
    }

    public void executeState() {
        ControllerData data = ControllerData.getInstance();
        data.setGameModel(new GameModel(data.getNumOfPlayers(), data.getExpertMode()));

        GameModel model   = data.getGameModel();
        Player[]  players = data.getPlayersOrder();
        for (Player player : players)
            model.setPlayer(player, player.getPlayerID());

        if (data.getNumOfPlayers() == 4)
            setMembers(players);
    }

    /**
     * Set up the teams (0, 2) and (1, 3)
     */
    private void setMembers(Player[] players) {
        for (int i = 0; i < 4; ++i) {
            if (players[i] instanceof PlayerTeam p)
                p.setTeamMember(players[(i + 2) % 4].getPlayerID());

            else if (players[i] instanceof PlayerTeamExpert p)
                p.setTeamMember(players[(i + 2) % 4].getPlayerID());

            else
                throw new IllegalStateException("The players were not correctly created: use PlayerTeam or PlayerTeamExpert when there are four players");
        }
    }
}
