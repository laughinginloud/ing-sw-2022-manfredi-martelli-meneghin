package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Interface representing the strategy of the CharacterCard
 * @author Giovanni Manfredi
 */
public abstract class CharacterCardStrategy {
    // reference to the same object in the model
    protected CharacterCard card;

    /**
     * Activates the effect of the CharacterCard
     */
    abstract public void activateEffect();

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
}
