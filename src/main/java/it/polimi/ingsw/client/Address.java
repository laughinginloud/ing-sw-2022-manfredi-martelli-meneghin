package it.polimi.ingsw.client;

import java.util.Locale;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Record containing an IP address and a port, along with a couple utility methods
 * @param ipAddress A string containing the IP address
 * @param port An integer containing the port number
 */
public record Address(String ipAddress, int port) {
    /**
     * A predicate used to match the IP addresses
     */
    private static final Predicate<String> ipMatcher = Pattern.compile("localhost|(\\b25[0-5]|\\b2[0-4]\\d|\\b[01]?\\d\\d?)(\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)){3}").asMatchPredicate();

    /**
     * Return a trimmed, lower case, spaceless copy of the passed string
     * @param ip The string to sanitize
     * @return The sanitized string
     */
    public static String sanitizeIP(String ip) {
        return ip.toLowerCase().replaceAll("(\\r|\\n|\\r\\n|\\s)+", "");
    }

    /**
     * Check whether the provided IP is valid
     * @param ip A string containing the IP to check
     * @return <code>true</code> if the string contains a valid address, <code>false</code> otherwise
     */
    public static boolean checkIP(String ip) {
        return ipMatcher.test(sanitizeIP(ip));
    }

    /**
     * Try and parse the provided string into an integer
     * @param port A string containing the port number to parse
     * @return An integer containing the parsed port number
     * @throws NumberFormatException If the provided string does not contain a valid integer
     */
    public static int parsePort(String port) throws NumberFormatException {
        return Integer.parseInt(port);
    }

    /**
     * Check whether the provided port number is registered or dynamic
     * @param port An integer containing the port number
     * @return <code>true</code> if the integer contains a valid port number, <code>false</code> otherwise
     */
    public static boolean checkPort(int port) {
        return port > 1023 && port < 65536;
    }

    /**
     * Check whether the provided port number is registered or dynamic
     * @param port A string containing the port number
     * @return <code>true</code> if the integer contains a valid port number, <code>false</code> otherwise
     * @throws NumberFormatException If the provided string does not contain a valid integer
     */
    public static boolean checkPort(String port) throws NumberFormatException {
        return checkPort(parsePort(port));
    }
}
