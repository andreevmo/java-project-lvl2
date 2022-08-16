package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parseFile(String text) throws IOException {
        Map<String, Object> parseFile;
        ObjectMapper mapper = new ObjectMapper();
        if (!isJson(text)) {
            mapper = new ObjectMapper(new YAMLFactory());
        }
        parseFile = mapper.readValue(text, Map.class);
        return parseFile;
    }

    public static boolean isJson(String text) {
        return text.charAt(0) == '{';
    }
}
