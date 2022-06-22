package it.polimi.ingsw.common.termutils;

import java.io.IOException;
import java.io.InputStream;

import static it.polimi.ingsw.common.utils.Methods.interrupted;
import static java.lang.Thread.sleep;

/**
 * Enum that represents some common keys
 * @author Mattia Martelli
 */
public enum Key {
    TAB,
    ENTER,

    // Both esc key and escape sequence
    ESCAPE,

    UP_ARROW, DOWN_ARROW, LEFT_ARROW, RIGHT_ARROW,

    // Used to jump directly to menu options
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO,

    // Useful letter keys
    Y, N,

    // Used as a generic key that does not need to be evaluated
    GENERIC;

    /**
     * Parse the first key of the provided stream
     * @param inputStream The stream to read from
     * @return The first key read
     * @throws IOException if an I/O error occurs
     */
    public static Key parseKey(InputStream inputStream) throws IOException {
        return switch (inputStream.read()) {
            case 9 -> TAB;
            case 13 -> ENTER;

            case 27 -> {
                if (inputStream.available() == 0)
                    yield ESCAPE;

                if (inputStream.read() != 79)
                    yield GENERIC;

                yield switch (inputStream.read()) {
                    case 65 -> UP_ARROW;
                    case 66 -> DOWN_ARROW;
                    case 67 -> RIGHT_ARROW;
                    case 68 -> LEFT_ARROW;

                    default -> GENERIC;
                };
            }

            case 49 -> ONE;
            case 50 -> TWO;
            case 51 -> THREE;
            case 52 -> FOUR;
            case 53 -> FIVE;
            case 54 -> SIX;
            case 55 -> SEVEN;
            case 56 -> EIGHT;
            case 57 -> NINE;
            case 48 -> ZERO;

            case 121 -> Y;
            case 110 -> N;

            default -> GENERIC;
        };
    }
}
