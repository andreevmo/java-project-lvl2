package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Json {
    public static String getFormatJson(Map<String, List<Object>> map) throws JsonProcessingException {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<Object>> e : map.entrySet()) {
            switch (e.getValue().get(0).toString()) {
                case "add" -> resultMap.put(" + " + e.getKey(), e.getValue().get(1));
                case "remove" -> resultMap.put(" - " + e.getKey(), e.getValue().get(1));
                case "update" -> {
                    resultMap.put(" + " + e.getKey(), e.getValue().get(1));
                    resultMap.put(" - " + e.getKey(), e.getValue().get(2));
                }
                default -> resultMap.put("   " + e.getKey(), e.getValue().get(1));
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(resultMap);
        return result.replaceAll("\" ", "\n\"")
                .substring(0, result.lastIndexOf("}")) + "\n}";
    }
}
