package utils;

public final class TextUtils {

    // private constructor prevents creating instances
    private TextUtils() {}

    public static boolean isNullOrBlank(String text) {
        return text == null || text.isBlank();
    }

    public static String normalize(String word) {
        return word == null ? "" : word.toLowerCase();
    }

    public static String repeat(String word, int times) {
        return (word + " ").repeat(times);
    }
}
