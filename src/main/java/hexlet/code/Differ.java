package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Differ {

    public static String generate(Path filepath1, Path filepath2, String format) throws IOException {
        Map<String, Object> fileOne = Parser.parseFile(filepath1);
        Map<String, Object> fileSecond = Parser.parseFile(filepath2);
        return switch (format) {
            case "unknown format" -> "unknown format";
            default -> stylish(compareFiles(fileOne, fileSecond));
        };
    }

    private static Map<String, List<Object>> compareFiles(Map<String, Object> fileOne, Map<String, Object> fileSecond) {
        Map<String, List<Object>> result = new TreeMap<>((s1, s2) -> s1.equals(s2) ? 1 : s1.compareTo(s2));
        for (Map.Entry element : fileOne.entrySet()) {
            if (fileSecond.containsKey(element.getKey())) {
                if (element.getValue() != null && element.getValue().equals(fileSecond.get(element.getKey()))) {
                    result.put((String) element.getKey(), createValue(element.getValue(), "unchanged"));
                } else if (element.getValue() == null && element.getValue() == fileSecond.get(element.getKey())) {
                    result.put((String) element.getKey(), createValue(element.getValue(), "unchanged"));
                } else {
                    result.put((String) element.getKey(), createValue(element.getValue(), "remove"));
                    result.put((String) element.getKey(), createValue(fileSecond.get(element.getKey()), "add"));
                }
            } else {
                result.put((String) element.getKey(), createValue(element.getValue(), "remove"));
            }
        }
        for (Map.Entry element : fileSecond.entrySet()) {
            if (!fileOne.containsKey(element.getKey())) {
                result.put((String) element.getKey(), createValue(element.getValue(), "add"));
            }
        }
        return result;
    }

    private static List<Object> createValue(Object value, String status) {
        List<Object> valueList = new ArrayList<>();
        valueList.add(value);
        valueList.add(status);
        return valueList;
    }

    private static String stylish(Map<String, List<Object>> resultMap) {
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<String, List<Object>> element : resultMap.entrySet()) {
            result.append("\n  ");
            switch (element.getValue().get(1).toString()) {
                case "add" -> result.append("+ ");
                case "remove" -> result.append("- ");
                default -> result.append("  ");
            }
            result.append(element.getKey())
                    .append(": ")
                    .append(element.getValue().get(0));
        }
        return result.append("\n}").toString();
    }
}
