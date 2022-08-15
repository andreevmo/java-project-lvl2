package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parseFile(String address) throws IOException {
        Map<String, Object> parseFile = new HashMap<>();
        Path filepath = Path.of(address).toAbsolutePath();
        ObjectMapper mapper = new ObjectMapper();
        if (isJson(filepath)) {
            parseFile = mapper.readValue(filepath.toFile(), Map.class);
        } else if (isYaml(filepath)) {
            mapper = new ObjectMapper(new YAMLFactory());
            parseFile = mapper.readValue(filepath.toFile(), Map.class);
        }
        return parseFile;
    }

    public static boolean isJson(Path filepath) {
        return filepath.toString().endsWith("json");
    }

    public static boolean isYaml(Path filepath) {
        return filepath.toString().endsWith("yml");
    }
}
