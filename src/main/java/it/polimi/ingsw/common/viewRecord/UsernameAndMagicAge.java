package it.polimi.ingsw.common.viewRecord;

/**
 * Record containing an username and magicAge of the player, along with a couple utility methods
 * @param username A string containing the player's username
 * @param magicAge An integer containing the player's magicAge
 */
public record UsernameAndMagicAge(String username, int magicAge) {

    /**
     * Return a trimmed, lower case of the passed string
     * @param username The string to sanitize
     * @return The sanitized string
     */
    public static String sanitizeUsername(String username){
        return username.toLowerCase().trim();
    }
}
