package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Path;

import static hexlet.code.Differ.generate;

public final class TestDiffer {

    private Path file1 = Path.of("src/test/resources/file1.json").toAbsolutePath();
    private Path file2 = Path.of("src/test/resources/file2.json").toAbsolutePath();
    private Path file3 = Path.of("src/test/resources/file3.yml").toAbsolutePath();
    private Path file4 = Path.of("src/test/resources/file4.yml").toAbsolutePath();
    private Path file5 = Path.of("src/test/resources/file5.json").toAbsolutePath();
    private Path file6 = Path.of("src/test/resources/file6.json").toAbsolutePath();
    private Path file7 = Path.of("src/test/resources/file7.yml").toAbsolutePath();
    private Path file8 = Path.of("src/test/resources/file8.yml").toAbsolutePath();

    @Test
    public void testGenerateOne() throws IOException {
        assertThat(generate(file1, file2, "stylish")).isEqualTo("{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}");
        assertThat(generate(file3, file4, "stylish")).isEqualTo("{\n"
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
        assertThat(generate(file5, file6, "stylish")).isEqualTo("{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}");
        assertThat(generate(file7, file8, "stylish")).isEqualTo("{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}");
    }
}
