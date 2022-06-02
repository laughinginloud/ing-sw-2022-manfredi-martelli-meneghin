package it.polimi.ingsw.common.viewRecord;

import java.util.Set;

/**
 * Record containing a username and magicAge of the player, along with a couple utility methods
 * @param username A string containing the player's username
 * @param magicAge An integer containing the player's magicAge
 * @author Giovanni Manfredi & Sebastiano Meneghin
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

    /**
     * Return true if the username meets the condition of:
     * - A non-null string
     * - A string between 1 and 10 characters
     * - A string not contained in the set forbiddenUsernames
     * @param username the username to check
     * @param forbiddenUsernames the set of forbidden usernames
     * @return true if the condition is reached, false otherwise
     */
    public static boolean checkUsername(String username, Set<String> forbiddenUsernames){
        return username != null && (username.length() > 0 && username.length() <= 10) && !forbiddenUsernames.contains(username);
    }

    /**
     * Try and parse the provided string (magicAge) into an integer
     * @param magicAge A string containing the magicAge number to parse
     * @return An integer containing the parsed magicAge number
     * @throws NumberFormatException If the provided string does not contain a valid integer
     */
    public static int parseMagicAge(String magicAge) throws NumberFormatException {
        return Integer.parseInt(magicAge);
    }

    /**
     * Check whether the provided magicAge number is valid
     * @param magicAgeInt An integer containing the magicAge
     * @return <code>true</code> if the integer contains a valid magicAge number, <code>false</code> otherwise
     */
    public static boolean checkMagicAge(int magicAgeInt) {
        return magicAgeInt > 0;
    }

    /**
     * Check whether the provided magicAge number (as a string) is valid
     * @param magicAge A string containing the magicAge number
     * @return <code>true</code> if the integer contains a valid magicAge number, <code>false</code> otherwise
     * @throws NumberFormatException If the provided string does not contain a valid integer
     */
    public static boolean checkMagicAge(String magicAge) throws NumberFormatException{
        return checkMagicAge(parseMagicAge(magicAge));
    }
}
