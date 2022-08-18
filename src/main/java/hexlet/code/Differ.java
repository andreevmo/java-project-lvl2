package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;

public class Differ {

    public static String generate(String filepath1, String filepath2, String format) throws IOException {
        if (!(isCorrectFormat(filepath1) && isCorrectFormat(filepath2))) {
            return "wrong file format";
        }
        String text1 = Files.readString(Path.of(filepath1));
        String text2 = Files.readString(Path.of(filepath2));
        Map<String, Object> fileOne = Parser.parseFile(text1, getFormatFile(filepath1));
        Map<String, Object> fileSecond = Parser.parseFile(text2, getFormatFile(filepath2));
        Map<String, List<Object>> comparisonResult = compareFiles(fileOne, fileSecond);
        return Formatter.format(comparisonResult, format);
    }

    private static String getFormatFile(String file) {
        return file.substring(file.lastIndexOf(".") + 1);
    }

    public static boolean isCorrectFormat(String filepath) {
        return filepath.endsWith(".json") || filepath.endsWith(".yml");
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    static Map<String, List<Object>> compareFiles(Map<String, Object> fileOne, Map<String, Object> fileSecond) {
        Map<String, List<Object>> result = new TreeMap<>();
        Set<String> setKey = new HashSet<>(fileOne.keySet());
        setKey.addAll(fileSecond.keySet());
        for (String key : setKey) {
            if (isUnchanged(fileOne, fileSecond, key)) {
                result.put(key, createValue("unchanged", fileOne.get(key)));
            } else if (isUpdate(fileOne, fileSecond, key)) {
                result.put(key, createValue("update", fileOne.get(key), fileSecond.get(key)));
            } else if (isRemove(fileOne, fileSecond, key)) {
                result.put(key, createValue("remove", fileOne.get(key)));
            } else {
                result.put(key, createValue("add", fileSecond.get(key)));
            }
        }
        return result;
    }

    private static boolean isUnchanged(Map<String, Object> f1, Map<String, Object> f2, String key) {
        return f1.containsKey(key) && f2.containsKey(key) && Objects.equals(f1.get(key), f2.get(key));
    }

    private static boolean isUpdate(Map<String, Object> f1, Map<String, Object> f2, String key) {
        return f1.containsKey(key) && f2.containsKey(key) && !Objects.equals(f1.get(key), f2.get(key));
    }

    private static boolean isRemove(Map<String, Object> f1, Map<String, Object> f2, String key) {
        return f1.containsKey(key) && !f2.containsKey(key);
    }

    private static List<Object> createValue(Object... value) {
        return new ArrayList<>(Arrays.asList(value));
    }
}
