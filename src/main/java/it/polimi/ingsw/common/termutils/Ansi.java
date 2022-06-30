package it.polimi.ingsw.common.termutils;

import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.TowerColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import static it.polimi.ingsw.common.termutils.Ansi.Escapes.CSI;
import static it.polimi.ingsw.common.termutils.Ansi.Escapes.SGR;

/**
 * Enum containing some ANSI commands
 * @author Mattia Martelli
 */
@SuppressWarnings("unused")
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
    HIDE_CURSOR (CSI + "?25l"),
    SHOW_CURSOR (CSI + "?25h"),

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
     * @param color The student to convert
     * @param background If it's <code>true</code> then the result will be the background version, otherwise
     *                   it will be the foreground one
     * @return The enum constant associated
     */
    public static Ansi getStudentColor(Color color, boolean background) {
        return switch (color) {
            case RED    -> background ? BACKGROUND_BRIGHT_RED     : BRIGHT_RED;
            case YELLOW -> background ? BACKGROUND_BRIGHT_YELLOW  : BRIGHT_YELLOW;
            case GREEN  -> background ? BACKGROUND_BRIGHT_GREEN   : BRIGHT_GREEN;
            case PINK   -> background ? BACKGROUND_BRIGHT_MAGENTA : BRIGHT_MAGENTA;
            case BLUE   -> background ? BACKGROUND_BRIGHT_BLUE    : BRIGHT_BLUE;
        };
    }

    /**
     * Return the ANSI color associated with the provided tower color
     * @param color The tower to convert
     * @param background If it's <code>true</code> then the result will be the background version, otherwise
     *                   it will be the foreground one
     * @return The enum constant associated
     */
    public static Ansi getTowerColor(TowerColor color, boolean background) {
        if (color == null)
            return null;

        return switch (color) {
            case BLACK -> background ? BACKGROUND_GRAY        : GRAY;
            case WHITE -> background ? BACKGROUND_WHITE       : WHITE;
            case GREY  -> background ? BACKGROUND_BRIGHT_GRAY : BRIGHT_GRAY;
        };
    }

    /**
     * Move the cursor in one of the four cardinal directions, returning the ANSI code to do so
     * @param direction The direction of the movement
     * @param numOfCells The number of cells the movement will cover
     * @return A string containing the ANSI code
     */
    public static String moveCursor(Direction direction, int numOfCells) {
        return CSI + numOfCells + switch (direction) {
            case UP    -> "A";
            case DOWN  -> "B";
            case RIGHT -> "C";
            case LEFT  -> "D";
        };
    }

    /**
     * Move the cursor in one of the four cardinal directions
     * @param direction The direction of the movement
     * @param numOfCells The number of cells the movement will cover
     */
    public static void moveCursor(PrintWriter writer, Direction direction, int numOfCells) {
        writer.print(moveCursor(direction, numOfCells));
    }

    /**
     * Hide the cursor for the specified writer, if the terminal emulator supports it
     * @param writer The writer for which to hide the cursor
     */
    public static void hideCursor(PrintWriter writer) {
        writer.print(HIDE_CURSOR);
    }

    /**
     * Show the cursor for the specified writer
     * @param writer The writer for which to show the cursor
     */
    public static void showCursor(PrintWriter writer) {
        writer.print(SHOW_CURSOR);
    }

    /**
     * Read a generic key
     * @param stream The stream to read from
     * @param writer The writer to write to
     * @return The pressed key
     * @throws IOException if there was an error whilst reading
     */
    public static int readKey(InputStream stream, PrintWriter writer) throws IOException {
        Ansi.hideCursor(writer);
        int res = stream.read();
        Ansi.showCursor(writer);
        return res;
    }

    // endregion

    // region Contructor

    private final String escape;

    Ansi(String escape) {
        this.escape = escape;
    }

    // endregion

    // region Escapes and Direction

    /**
     * ANSI commands introducers
     */
    public static final class Escapes {
        /**
         * Escape sequence introducer
         */
        public static final String ESC = "\u001B";

        /**
         * Control Sequence Introducer
         */
        public static final String CSI = "\u001B[";

        /**
         * Device Control String Introducer
         */
        public static final String DCS = "\u001BP";

        /**
         * Operating System Command introducer
         */
        public static final String OSC = "\u001B]";

        /**
         * Return a Select Graphic Rendition command
         * @param code The code of the command
         * @return A string containing the command
         */
        public static String SGR(String code) {
            return CSI + code + "m";
        }
    }

    /**
     * Used for moving the cursor
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    //endregion

}
