package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static hexlet.code.Differ.generate;

public final class TestDiffer {

    private Path file1;
    private Path file2;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() throws IOException {
        file1 = Path.of("src/test/resources/file1.json").toAbsolutePath();
        Files.deleteIfExists(file1);
        Files.createFile(file1);
        file2 = Path.of("src/test/resources/file2.json").toAbsolutePath();
        Files.deleteIfExists(file2);
        Files.createFile(file2);
    }

    @Test
    public void testGenerateOne() throws IOException {
        Map<String, Object> valueForFile1 = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", "123.234.53.22",
                "follow", false);
        Map<String, Object> valueForFile2 = Map.of(
                "timeout", 20,
                "verbose", true,
                "host", "hexlet.io");
        mapper.writeValue(file1.toFile(), valueForFile1);
        mapper.writeValue(file2.toFile(), valueForFile2);
        assertThat(generate(file1, file2)).isEqualTo("{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}");
    }

    @Test
    public void testGenerateSecond() throws IOException {
        Map<String, Object> valueForFile1 = Map.of(
                "name", "Max",
                "age", 27,
                "city", "Saratov",
                "status", false);
        Map<String, Object> valueForFile2 = Map.of(
                "name", "Victor",
                "status", false,
                "city", "Saratov",
                "follow", "yes");
        mapper.writeValue(file1.toFile(), valueForFile1);
        mapper.writeValue(file2.toFile(), valueForFile2);
        assertThat(generate(file1, file2)).isEqualTo("{\n"
                + "  - age: 27\n"
                + "    city: Saratov\n"
                + "  + follow: yes\n"
                + "  - name: Max\n"
                + "  + name: Victor\n"
                + "    status: false\n"
                + "}");
    }
}
