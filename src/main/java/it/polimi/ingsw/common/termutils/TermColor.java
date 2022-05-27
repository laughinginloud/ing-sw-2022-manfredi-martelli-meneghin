package it.polimi.ingsw.common.termutils;

import it.polimi.ingsw.common.model.Color;

import java.util.List;

/**
 * Enum containing all the ANSI color constants
 * @author Mattia Martelli
 */
public enum TermColor {

    // region Enum constants

    // Dark foreground colors
    BLACK   ("\u001B[30m"),
    RED     ("\u001B[31m"),
    GREEN   ("\u001B[32m"),
    YELLOW  ("\u001B[33m"),
    BLUE    ("\u001B[34m"),
    MAGENTA ("\u001B[35m"),
    CYAN    ("\u001b[36m"),
    GRAY    ("\u001b[90m"),

    // Light foreground colors
    WHITE         ("\u001b[97m"),
    BRIGHT_RED     ("\u001b[91m"),
    BRIGHT_GREEN   ("\u001b[92m"),
    BRIGHT_YELLOW  ("\u001b[93m"),
    BRIGHT_BLUE    ("\u001b[94m"),
    BRIGHT_MAGENTA ("\u001b[95m"),
    BRIGHT_CYAN    ("\u001b[96m"),
    BRIGHT_GRAY    ("\u001b[37m"),

    // Dark background colors
    BACKGROUND_BLACK   ("\u001B[40m"),
    BACKGROUND_RED     ("\u001B[41m"),
    BACKGROUND_GREEN   ("\u001B[42m"),
    BACKGROUND_YELLOW  ("\u001B[43m"),
    BACKGROUND_BLUE    ("\u001B[44m"),
    BACKGROUND_MAGENTA ("\u001B[45m"),
    BACKGROUND_CYAN    ("\u001b[46m"),
    BACKGROUND_GRAY    ("\u001b[100m"),

    // Light background colors
    BACKGROUND_WHITE          ("\u001b[107m"),
    BACKGROUND_BRIGHT_RED     ("\u001b[101m"),
    BACKGROUND_BRIGHT_GREEN   ("\u001b[102m"),
    BACKGROUND_BRIGHT_YELLOW  ("\u001b[103m"),
    BACKGROUND_BRIGHT_BLUE    ("\u001b[104m"),
    BACKGROUND_BRIGHT_MAGENTA ("\u001b[105m"),
    BACKGROUND_BRIGHT_CYAN    ("\u001b[106m"),
    BACKGROUND_BRIGHT_GRAY    ("\u001b[47m"),

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
     * Return a string colored with the provided enum constants
     * @param string The string to color
     * @param colors The colors to use
     * @return A string containing the colored string
     */
    public static String colorString(String string, TermColor... colors) {
        return colorString(string, List.of(colors));
    }

    /**
     * Return a string colored with the current color
     * @param string The string to color
     * @return A string containing the colored string
     */
    public String colorString(String string) {
        return colorString(string, this);
    }

    /**
     * Return a string colored with the provided list of enum constant
     * @param string The string to color
     * @param colors The colors to use
     * @return A string containing the colored string
     */
    public static String colorString(String string, List<TermColor> colors) {
        StringBuilder sr = new StringBuilder();
        colors.forEach(c -> sr.append(c.escape));
        sr.append(string);
        sr.append(DEFAULT);
        return sr.toString();
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

    /**
     * Return the ANSI color associated with the provided student color
     */
    public static TermColor getStudentColor(Color color) {
        return switch (color) {
            case RED    -> BRIGHT_RED;
            case YELLOW -> BRIGHT_YELLOW;
            case GREEN  -> BRIGHT_GREEN;
            case PINK   -> BRIGHT_MAGENTA;
            case BLUE   -> BRIGHT_BLUE;
        };
    }

    // endregion

    // region Contruction related methods

    private final String escape;

    TermColor(String escape) {
        this.escape = escape;
    }

    // endregion

}
