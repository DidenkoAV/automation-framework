package core.helpers.framework.general;

public class StringHelper {

    public static String extractNumbersFromText(String input) {
        return input.replaceAll("[^0-9]", "");
    }
}
