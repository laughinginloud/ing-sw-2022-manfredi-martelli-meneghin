package it.polimi.ingsw.client.view.cli;

/**
 * Enum containing all the ANSI color constants
 * @author Mattia Martelli
 */
enum TermColor {

    // region Enum constants

    // Dark foreground colors
    BLACK        ("\u001B[30m"),
    DARK_RED     ("\u001B[31m"),
    DARK_GREEN   ("\u001B[32m"),
    DARK_YELLOW  ("\u001B[33m"),
    DARK_BLUE    ("\u001B[34m"),
    DARK_MAGENTA ("\u001B[35m"),
    DARK_CYAN    ("\u001b[36m"),
    DARK_GRAY    ("\u001b[90m"),

    // Light foreground colors
    WHITE         ("\u001b[97m"),
    LIGHT_RED     ("\u001b[91m"),
    LIGHT_GREEN   ("\u001b[92m"),
    LIGHT_YELLOW  ("\u001b[93m"),
    LIGHT_BLUE    ("\u001b[94m"),
    LIGHT_MAGENTA ("\u001b[95m"),
    LIGHT_CYAN    ("\u001b[96m"),
    LIGHT_GRAY    ("\u001b[37m"),

    // Dark background colors
    BACKGROUND_BLACK        ("\u001B[40m"),
    BACKGROUND_DARK_RED     ("\u001B[41m"),
    BACKGROUND_DARK_GREEN   ("\u001B[42m"),
    BACKGROUND_DARK_YELLOW  ("\u001B[43m"),
    BACKGROUND_DARK_BLUE    ("\u001B[44m"),
    BACKGROUND_DARK_MAGENTA ("\u001B[45m"),
    BACKGROUND_DARK_CYAN    ("\u001b[46m"),
    BACKGROUND_DARK_GRAY    ("\u001b[100m"),

    // Light background colors
    BACKGROUND_WHITE         ("\u001b[107m"),
    BACKGROUND_LIGHT_RED     ("\u001b[101m"),
    BACKGROUND_LIGHT_GREEN   ("\u001b[102m"),
    BACKGROUND_LIGHT_YELLOW  ("\u001b[103m"),
    BACKGROUND_LIGHT_BLUE    ("\u001b[104m"),
    BACKGROUND_LIGHT_MAGENTA ("\u001b[105m"),
    BACKGROUND_LIGHT_CYAN    ("\u001b[106m"),
    BACKGROUND_LIGHT_GRAY    ("\u001b[47m"),

    // Miscellaneous
    BOLD            ("\u001b[1m"),
    UNDERLINE       ("\u001b[4m"),
    NO_UNDERLINE    ("\u001b[24m"),
    REVERSE_TEXT    ("\u001b[7m"),
    NO_REVERSE_TEXT ("\u001b[27m"),
    DEFAULT         ("\u001B[0m");

    // endregion

    // region Utility methods

    /**
     * Return a string colored with the provided enum constant
     * @param string The string to color
     * @param color The color to use
     * @return A string containing the colored string
     */
    public static String colorString(String string, TermColor color) {
        return color + string + DEFAULT;
    }

    /**
     * Return the escape associated with the current enum object
     */
    public String toString() {
        return escape;
    }

    /**
     * Return the escape associated with the provided enum object
     */
    public static String toString(TermColor color) {
        return color.escape;
    }

    // endregion

    // region Contruction related methods

    private final String escape;

    TermColor(String escape) {
        this.escape = escape;
    }

    // endregion

}
