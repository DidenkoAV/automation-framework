package framework.helpers.general;

public class StringHelper {
    public static final String NUMBER_REGEX = "[^0-9]";

    public static String extractNumbersFromText(String input) {
        return input.replaceAll(NUMBER_REGEX, "");
    }
}
