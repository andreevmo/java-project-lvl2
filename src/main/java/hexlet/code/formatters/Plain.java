package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Plain {

    public static String getFormatPlain(Map<String, List<Object>> resultMap) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Object>> element : resultMap.entrySet()) {
            switch (element.getValue().get(0).toString()) {
                case "add" -> result.append("\nProperty '").append(element.getKey()).append("'")
                        .append(" was added with value: ")
                        .append(buildValue(element.getValue().get(1)));
                case "update" -> result.append("\nProperty '").append(element.getKey()).append("'")
                        .append(" was updated. From ")
                        .append(buildValue(element.getValue().get(1)))
                        .append(" to ")
                        .append(buildValue(element.getValue().get(2)));
                case "remove" -> result.append("\nProperty '").append(element.getKey()).append("'")
                        .append(" was removed");
                default -> {
                }
            }
        }
        return result.toString().trim();
    }

    private static Object buildValue(Object o) {
        if (o instanceof ArrayList<?> || o instanceof Map<?, ?>) {
            return "[complex value]";
        }
        if (o instanceof String) {
            return "'" + o + "'";
        }
        return o;
    }
}
