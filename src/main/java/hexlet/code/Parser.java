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
        if (filepath.toString().endsWith("json")) {
            ObjectMapper mapper = new ObjectMapper();
            parseFile = mapper.readValue(filepath.toAbsolutePath().toFile(), Map.class);
        } else if (filepath.toString().endsWith("yml")) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            parseFile = mapper.readValue(filepath.toAbsolutePath().toFile(), Map.class);
        }
        return parseFile;
    }
}
