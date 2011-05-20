package nl.chess.it.util.config.resolv; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Guus Bosman (Chess-iT)
 */
public class PropertyInputStreamValidator {

    public static void validate(InputStream inStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "8859_1"));
        int lineNumber = 0;
        while (true) {
            String line = in.readLine();
            lineNumber++;
            if (line == null) return;

            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '\\') {
                    if (i < line.length()) {
                        if (!isAllowedEscape(line.charAt(i + 1))) {
                            throw new IOException("Invalid escape sequence (\\" +line.charAt(i + 1) + ") in InputStream on line " +lineNumber + ": " + line);
                        }
                    }
                }
            }

        }
    }

    private static boolean isAllowedEscape(char c) {
        if ("\\tnfr\"'u".indexOf(c) >= 0) return true;

        return false;
    }
}