package hexlet.code;

import java.util.List;
import java.util.Map;
import static hexlet.code.formatters.Plain.getFormatPlain;
import static hexlet.code.formatters.Stylish.getFormatStylish;

public class Formatter {

    public static String format(Map<String, List<Object>> resultMap, String format) {
        return switch (format) {
            case "plain" -> getFormatPlain(resultMap);
            default -> getFormatStylish(resultMap);
        };
    }
}
