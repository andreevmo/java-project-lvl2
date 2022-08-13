package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String getFormatStylish(Map<String, List<Object>> resultMap) {
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<String, List<Object>> element : resultMap.entrySet()) {
            result.append("\n  ");
            switch (element.getValue().get(0).toString()) {
                case "add" -> result.append("+ ")
                        .append(buildValue(element.getKey(), element.getValue().get(1)));
                case "remove" -> result.append("- ")
                        .append(buildValue(element.getKey(), element.getValue().get(1)));
                case "update" -> result.append("- ")
                        .append(buildValue(element.getKey(), element.getValue().get(1)))
                        .append("\n  + ")
                        .append(buildValue(element.getKey(), element.getValue().get(2)));
                default -> result.append("  ")
                        .append(buildValue(element.getKey(), element.getValue().get(1)));
            }
        }
        return result.append("\n}").toString();
    }

    private static String buildValue(String key, Object value) {
        StringBuilder line = new StringBuilder();
        return line.append(key)
                .append(": ")
                .append(value)
                .toString();
    }
}
