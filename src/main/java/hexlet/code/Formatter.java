package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

import static hexlet.code.formatters.Json.getFormatJson;
import static hexlet.code.formatters.Plain.getFormatPlain;
import static hexlet.code.formatters.Stylish.getFormatStylish;

public class Formatter {

    public static String format(Map<String, List<Object>> resultMap, String format) throws JsonProcessingException {
        return switch (format) {
            case "plain" -> getFormatPlain(resultMap);
            case "json" -> getFormatJson(resultMap);
            default -> getFormatStylish(resultMap);
        };
    }
}
