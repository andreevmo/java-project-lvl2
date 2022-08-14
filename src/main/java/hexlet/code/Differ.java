package hexlet.code;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class Differ {

    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        Map<String, Object> fileOne = Parser.parseFile(filepath1);
        Map<String, Object> fileSecond = Parser.parseFile(filepath2);
        return Formatter.format(compareFiles(fileOne, fileSecond), format);
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    private static Map<String, List<Object>> compareFiles(Map<String, Object> fileOne, Map<String, Object> fileSecond) {
        Map<String, List<Object>> result = new TreeMap<>((s1, s2) -> s1.equals(s2) ? 1 : s1.compareTo(s2));
        for (Map.Entry element : fileOne.entrySet()) {
            if (fileSecond.containsKey(element.getKey())) {
                if (objectsEquals(element.getValue(), fileSecond.get(element.getKey()))) {
                    result.put((String) element.getKey(), createValue("unchanged", element.getValue()));
                } else {
                    result.put((String) element.getKey(), createValue("update", element.getValue(),
                            fileSecond.get(element.getKey())));
                }
            } else {
                result.put((String) element.getKey(), createValue("remove", element.getValue()));
            }
        }
        for (Map.Entry element : fileSecond.entrySet()) {
            if (!fileOne.containsKey(element.getKey())) {
                result.put((String) element.getKey(), createValue("add", element.getValue()));
            }
        }
        return result;
    }

    private static boolean objectsEquals(Object o1, Object o2) {
        if (o1 != null) {
            return o1.equals(o2);
        }
        return  o2 == null;
    }

    private static List<Object> createValue(Object... value) {
        return new ArrayList<>(Arrays.asList(value));
    }
}
