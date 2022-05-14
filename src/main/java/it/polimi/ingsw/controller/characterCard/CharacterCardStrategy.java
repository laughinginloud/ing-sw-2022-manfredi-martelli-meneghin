package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommand;
import it.polimi.ingsw.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing the strategy of the CharacterCard
 * @author Giovanni Manfredi
 */
public abstract class CharacterCardStrategy {
    // Reference to the corresponding card in the model
    protected CharacterCard card;

    // The map that will be updated by the concrete strategies with the data they modified
    protected Map<GameCommandValues, Object> afterEffectUpdate = new HashMap<>();

    /**
     * Factory method: Associates the strategy to the CharacterCard
     * @param card The card that needs the strategy
     * @return the CharacterCardStrategy containing the association between card and strategy
     */
    public static CharacterCardStrategy build(CharacterCard card) {
        return switch (card.getCharacter()) {
            case MONK            -> new MonkStrategy(card);
            case FARMER          -> new FarmerStrategy(card);
            case STANDARD_BEARER -> new StandardBearerStrategy(card);
            case MAGICIAN        -> new MagicianStrategy(card);
            case HERBALIST       -> new HerbalistStrategy(card);
            case CENTAUR         -> new CentaurStrategy(card);
            case JESTER          -> new JesterStrategy(card);
            case CAVALIER        -> new CavalierStrategy(card);
            case MERCHANT        -> new MerchantStrategy(card);
            case BARD            -> new BardStrategy(card);
            case PRINCESS        -> new PrincessStrategy(card);
            case THIEF           -> new ThiefStrategy(card);
        };
    }

    /**
     * Method that activates all the CharacterCard routine:
     * - Moves coins from player to globalCoinPool
     * - Sends info to players about the previous changes
     * - Activates the card effect (varies depending on the card - 12 possible)
     */
    public void playCharacterCard(){
        ControllerData data = ControllerData.getInstance();
        Player currentPlayer = data.getCurrentPlayer();

        // This method makes the player 'spend' his coins
        moveCoins(currentPlayer, card.getCost());

        // TODO: SendInfo

        // This method activates the card effect
        this.activateEffect();

        // Sets that a flag which indicates that a CharacterCard has been played this turn
        data.setPlayedCard();

        updatePlayers(); //TODO
    }

    /**
     * Moves coins from the player to the globalCoin pool, decrementing and incrementing the pool respectively
     * @param currentPlayer the player that has activated the CharacterCard
     * @param characterCardCost the cost of the activated CharacterCard
     */
    private void moveCoins(Player currentPlayer, int characterCardCost){
        int currentCoins;
        // Based on the number of players the Player instance could vary, so this handles both cases
        // Here the player's coins are decremented by the characterCardCost
        if (currentPlayer instanceof PlayerExpert expertPlayer) {
            currentCoins = expertPlayer.getCoinCount();
            expertPlayer.setCoinCount(currentCoins - characterCardCost);
        }
        else if (currentPlayer instanceof PlayerTeamExpert teamExpertPlayer){
            currentCoins = teamExpertPlayer.getCoinCount();
            teamExpertPlayer.setCoinCount(currentCoins - characterCardCost);
        };

        GameModel model = ControllerData.getInstance().getGameModel();
        // Based on if the card was already played once or not, the card could have or have not a coin on it
        // This coin is a reminder for the increased cost that needs to be paid for playing the card
        int currentCoinPool = model.getCoinPool();
        if (card.getHasCoin()){
            model.setCoinPool(currentCoinPool + characterCardCost);
        }
        else {
            card.setHasCoin(true);
            card.setCost(characterCardCost + 1);
            // A coin 'is placed' on the card, so a coin less will be put in the global pool
            model.setCoinPool(currentCoinPool + (characterCardCost - 1));
        }
    }

    /**
     * Activates the effect of the CharacterCard
     */
    abstract public void activateEffect();

    /**
     * Send the updated data contained in the afterEffectUpdate map to every player
     */
    private void updatePlayers() {
        GameCommand sendInfo = new GameCommandSendInfo(afterEffectUpdate);

        Player[] players = ControllerData.getInstance().getPlayersOrder();
        for (Player playersToUpdate : players) {
            VirtualView playerToUpdateView = ControllerData.getInstance().getPlayerView(playersToUpdate);
            playerToUpdateView.sendMessage(sendInfo);
        }
    }
}
