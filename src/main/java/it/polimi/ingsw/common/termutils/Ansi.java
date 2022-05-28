package it.polimi.ingsw.common.termutils;

import it.polimi.ingsw.common.model.Color;

import java.util.List;

import static it.polimi.ingsw.common.termutils.Ansi.Escapes.CSI;

/**
 * Enum containing all the ANSI color constants
 * @author Mattia Martelli
 */
public enum Ansi {

    // region Enum constants

    // Dark foreground colors
    BLACK   (CSI + "30m"),
    RED     (CSI + "31m"),
    GREEN   (CSI + "32m"),
    YELLOW  (CSI + "33m"),
    BLUE    (CSI + "34m"),
    MAGENTA (CSI + "35m"),
    CYAN    (CSI + "36m"),
    GRAY    (CSI + "90m"),

    // Light foreground colors
    WHITE          (CSI + "97m"),
    BRIGHT_RED     (CSI + "91m"),
    BRIGHT_GREEN   (CSI + "92m"),
    BRIGHT_YELLOW  (CSI + "93m"),
    BRIGHT_BLUE    (CSI + "94m"),
    BRIGHT_MAGENTA (CSI + "95m"),
    BRIGHT_CYAN    (CSI + "96m"),
    BRIGHT_GRAY    (CSI + "37m"),

    // Dark background colors
    BACKGROUND_BLACK   (CSI + "40m"),
    BACKGROUND_RED     (CSI + "41m"),
    BACKGROUND_GREEN   (CSI + "42m"),
    BACKGROUND_YELLOW  (CSI + "43m"),
    BACKGROUND_BLUE    (CSI + "44m"),
    BACKGROUND_MAGENTA (CSI + "45m"),
    BACKGROUND_CYAN    (CSI + "46m"),
    BACKGROUND_GRAY    (CSI + "100m"),

    // Light background colors
    BACKGROUND_WHITE          (CSI + "107m"),
    BACKGROUND_BRIGHT_RED     (CSI + "101m"),
    BACKGROUND_BRIGHT_GREEN   (CSI + "102m"),
    BACKGROUND_BRIGHT_YELLOW  (CSI + "103m"),
    BACKGROUND_BRIGHT_BLUE    (CSI + "104m"),
    BACKGROUND_BRIGHT_MAGENTA (CSI + "105m"),
    BACKGROUND_BRIGHT_CYAN    (CSI + "106m"),
    BACKGROUND_BRIGHT_GRAY    (CSI + "47m"),

    // Cursor related
    CURSOR_HIDE (CSI + "?25l"),
    CURSOR_SHOW (CSI + "?25h"),

    // Miscellaneous
    BOLD            (CSI + "1m"),
    UNDERLINE       (CSI + "4m"),
    NO_UNDERLINE    (CSI + "24m"),
    REVERSE_TEXT    (CSI + "7m"),
    NO_REVERSE_TEXT (CSI + "27m"),
    DEFAULT         (CSI + "0m");

    // endregion

    // region Utility methods

    /**
     * Return a string colored with the provided enum constants
     * @param string The string to color
     * @param colors The colors to use
     * @return A string containing the colored string
     */
    public static String colorString(String string, Ansi... colors) {
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
    public static String colorString(String string, List<Ansi> colors) {
        StringBuilder sr = new StringBuilder();
        colors.forEach(sr::append);
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
    public static String toString(Ansi color) {
        return color.escape;
    }

    /**
     * Return the ANSI color associated with the provided student color
     */
    public static Ansi getStudentColor(Color color) {
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

    Ansi(String escape) {
        this.escape = escape;
    }

    // endregion

    // region Escapes

    public static class Escapes {
        public static final String ESC = "\u001B";
        public static final String CSI = "\u009B";
        public static final String DCS = "\u0090";
        public static final String OSC = "\u009D";
    }

    //endregion

}
