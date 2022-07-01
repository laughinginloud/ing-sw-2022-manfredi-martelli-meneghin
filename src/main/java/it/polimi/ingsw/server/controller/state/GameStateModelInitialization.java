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
    @Override
    public GameState nextState() {
        return new GameStatePlaceTokens();
    }

    @Override
    public void executeState() {
        ControllerData data = ControllerData.getInstance();
        data.setGameModel(new GameModel(data.getNumOfPlayers(), data.getExpertMode()));

        // Set all the players in the model in the correct order, which means that the ID should match the array index
        GameModel model   = data.getGameModel();
        Player[]  players = data.getPlayersOrder();
        for (Player player : players)
            model.setPlayer(player, player.getPlayerID());

        // If the game is being played by four people, link the teams
        if (data.getNumOfPlayers() == 4)
            setMembers(model.getPlayers());
    }

    /**
     * Set up the teams (0, 2) and (1, 3)
     */
    @SuppressWarnings("ConstantConditions")
    private void setMembers(Player[] players) {
        for (int i = 0; i < 4; ++i) {
            if (players[i] instanceof PlayerTeam p)
                p.setTeamMemberID((i + 2) % 4);

            else if (players[i] instanceof PlayerTeamExpert p)
                p.setTeamMemberID((i + 2) % 4);

            else
                throw new IllegalStateException("The players were not correctly created: use PlayerTeam or PlayerTeamExpert when there are four players");
        }
    }
}
