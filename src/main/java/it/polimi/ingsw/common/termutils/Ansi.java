package it.polimi.ingsw.common.termutils;

import it.polimi.ingsw.common.model.Color;

import java.util.List;

import static it.polimi.ingsw.common.termutils.Ansi.Escapes.CSI;
import static it.polimi.ingsw.common.termutils.Ansi.Escapes.SGR;

/**
 * Enum containing all the ANSI color constants
 * @author Mattia Martelli
 */
public enum Ansi {

    // region Enum constants

    // Dark foreground colors
    BLACK   (SGR("30")),
    RED     (SGR("31")),
    GREEN   (SGR("32")),
    YELLOW  (SGR("33")),
    BLUE    (SGR("34")),
    MAGENTA (SGR("35")),
    CYAN    (SGR("36")),
    GRAY    (SGR("90")),

    // Light foreground colors
    WHITE          (SGR("97")),
    BRIGHT_RED     (SGR("91")),
    BRIGHT_GREEN   (SGR("92")),
    BRIGHT_YELLOW  (SGR("93")),
    BRIGHT_BLUE    (SGR("94")),
    BRIGHT_MAGENTA (SGR("95")),
    BRIGHT_CYAN    (SGR("96")),
    BRIGHT_GRAY    (SGR("37")),

    // Dark background colors
    BACKGROUND_BLACK   (SGR("40")),
    BACKGROUND_RED     (SGR("41")),
    BACKGROUND_GREEN   (SGR("42")),
    BACKGROUND_YELLOW  (SGR("43")),
    BACKGROUND_BLUE    (SGR("44")),
    BACKGROUND_MAGENTA (SGR("45")),
    BACKGROUND_CYAN    (SGR("46")),
    BACKGROUND_GRAY    (SGR("100")),

    // Light background colors
    BACKGROUND_WHITE          (SGR("107")),
    BACKGROUND_BRIGHT_RED     (SGR("101")),
    BACKGROUND_BRIGHT_GREEN   (SGR("102")),
    BACKGROUND_BRIGHT_YELLOW  (SGR("103")),
    BACKGROUND_BRIGHT_BLUE    (SGR("104")),
    BACKGROUND_BRIGHT_MAGENTA (SGR("105")),
    BACKGROUND_BRIGHT_CYAN    (SGR("106")),
    BACKGROUND_BRIGHT_GRAY    (SGR("47")),

    // Cursor related
    CURSOR_HIDE (CSI + "?25l"),
    CURSOR_SHOW (CSI + "?25h"),

    // Miscellaneous
    BOLD            (SGR("1")),
    UNDERLINE       (SGR("4")),
    NO_UNDERLINE    (SGR("24")),
    REVERSE_TEXT    (SGR("7")),
    NO_REVERSE_TEXT (SGR("27")),
    DEFAULT         (SGR("0"));

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

        public static String SGR(String code) {
            return CSI + code + "m";
        }
    }

    //endregion

}
