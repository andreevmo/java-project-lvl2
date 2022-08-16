package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestDiffer {

    private static final String FILE1 = "src/test/resources/file1.json";
    private static final String FILE2 = "src/test/resources/file2.json";
    private static final String FILE3 = "src/test/resources/file3.yml";
    private static final String FILE4 = "src/test/resources/file4.yml";
    private static final String EXPECTED_STYLISH = "src/test/resources/expectedStylish.txt";
    private static final String EXPECTED_PLAIN = "src/test/resources/expectedPlain.txt";
    private static final String EXPECTED_JSON = "src/test/resources/expectedJson.txt";

    @ParameterizedTest
    @ArgumentsSource(TestArgumentsProvider.class)
    public void testGenerate(String file1, String file2, String format, String expected) throws IOException {
        assertThat(Differ.generate(file1, file2, format)).isEqualTo(expected);
    }

    static class TestArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            String expectedStylish = Files.lines(Path.of(EXPECTED_STYLISH))
                    .collect(Collectors.joining("\n"));
            String expectedPlain = Files.lines(Path.of(EXPECTED_PLAIN))
                    .collect(Collectors.joining("\n"));
            String expectedJson = Files.lines(Path.of(EXPECTED_JSON))
                    .collect(Collectors.joining());
            return Stream.of(
                    Arguments.of(FILE1, FILE2, "stylish", expectedStylish),
                    Arguments.of(FILE3, FILE4, "stylish", expectedStylish),
                    Arguments.of(FILE1, FILE2, "plain", expectedPlain),
                    Arguments.of(FILE3, FILE4, "plain", expectedPlain),
                    Arguments.of(FILE1, FILE2, "json", expectedJson),
                    Arguments.of(FILE3, FILE4, "json", expectedJson));
        }
    }

    @Test
    void testGenerateWithoutFormat() throws IOException {
        String expectedStylish = Files.lines(Path.of(EXPECTED_STYLISH))
                .collect(Collectors.joining("\n"));
        assertThat(Differ.generate(FILE1, FILE2)).isEqualTo(expectedStylish);
    }

    @Test
    void testGenerateWithDifferentFileFormat() throws IOException {
        assertThat(Differ.generate(FILE1, FILE3)).isEqualTo("wrong file format");
    }

    @Test
    void testParser() throws IOException {
        String stringTest = Files.lines(Path.of(FILE1))
                .collect(Collectors.joining("\n"));
        Map<String, Object> mapTest = new ObjectMapper().readValue(stringTest, Map.class);
        assertThat(Parser.parseFile(stringTest)).isEqualTo(mapTest);
    }

    @Test
    void testDifferCompareFiles() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String stringTest1 = Files.lines(Path.of(FILE1))
                .collect(Collectors.joining("\n"));
        Map<String, Object> mapTest1 = mapper.readValue(stringTest1, Map.class);
        String stringTest2 = Files.lines(Path.of(FILE2))
                .collect(Collectors.joining("\n"));
        Map<String, Object> mapTest2 = mapper.readValue(stringTest2, Map.class);
        Set<String> setTest = new HashSet<>(mapTest1.keySet());
        setTest.addAll(mapTest2.keySet());
        assertThat(Differ.compareFiles(mapTest1, mapTest2).keySet().containsAll(setTest)).isTrue();
    }

    @Test
    void testFormat() throws IOException {
        Map<String, List<Object>> testMap = Map.of(
                "Key1", List.of("test1", 1),
                "Key2", List.of("test2", 2));
        assertThat(Formatter.format(testMap, "json")).isInstanceOf(String.class);
        assertThat(Formatter.format(testMap, "json").endsWith("}")).isTrue();
        assertThat(Formatter.format(testMap, "json").startsWith("{")).isTrue();
        assertThat(!Formatter.format(testMap, "plain").endsWith("}")).isTrue();
        assertThat(!Formatter.format(testMap, "plain").startsWith("{")).isTrue();
        assertThat(Formatter.format(testMap, "stylish").endsWith("}")).isTrue();
        assertThat(Formatter.format(testMap, "stylish").startsWith("{")).isTrue();
    }
}
