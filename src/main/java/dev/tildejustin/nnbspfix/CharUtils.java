package dev.tildejustin.nnbspfix;

public class CharUtils {
    public static int replaceNNBSPCharWithSpace(int c) {
        // a better implementation would be to check all zero width chars (\u00a0, \u2007, & \u202f)
        // but I don't that's necessary
        if (c == '\u202f' /* " ", narrow no-break space */)
            return ' ';
        return c;
    }

    // repeat code to avoid casts
    public static char replaceNNBSPCharWithSpace(char c) {
        if (c == '\u202f') return ' ';
        return c;
    }
}
