package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Differ {

    public static String generate(Path filepath1, Path filepath2) throws IOException {
        Map<String, Object> parseFileOne = Parser.parseFile(filepath1);
        Map<String, Object> parseFileSecond = Parser.parseFile(filepath2);
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                if (s.equals(t1)) {
                    return 1;
                }
                return s.compareTo(t1);
            }
        };
        return mapToFormatString(compareFiles(parseFileOne, parseFileSecond, comparator));
    }

    private static Map<String, Object> compareFiles(Map<String, Object> parseFileOne,
                                                    Map<String, Object> parseFileSecond, Comparator comparator) {
        Map<String, Object> resultMap = new TreeMap<>(comparator);
        for (Map.Entry element : parseFileOne.entrySet()) {
            if (parseFileSecond.containsKey(element.getKey())
                    && parseFileSecond.get(element.getKey()).equals(element.getValue())) {
                resultMap.put((String) element.getKey(), "  :" + element.getValue());
            } else if (parseFileSecond.containsKey(element.getKey())
                    && !(parseFileSecond.get(element.getKey()).equals(element.getValue()))) {
                resultMap.put((String) element.getKey(), "- :" + element.getValue());
                resultMap.put((String) element.getKey(), "+ :" + parseFileSecond.get(element.getKey()));
            } else if (!(parseFileSecond.containsKey(element.getKey()))) {
                resultMap.put((String) element.getKey(), "- :" + element.getValue());
            }
        }
        for (Map.Entry element : parseFileSecond.entrySet()) {
            if (!parseFileOne.containsKey(element.getKey())) {
                resultMap.put((String) element.getKey(), "+ :" + element.getValue());
            }
        }
        return resultMap;
    }

    private static String mapToFormatString(Map<String, Object> resultMap) {
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<String, Object> element : resultMap.entrySet()) {
            String[] partsValue = element.getValue().toString().split(":");
            result.append("\n  ")
                    .append(partsValue[0])
                    .append(element.getKey())
                    .append(": ")
                    .append(partsValue[1]);
        }
        return result.append("\n}").toString();
    }
}
