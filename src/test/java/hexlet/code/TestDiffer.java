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
        assertThat(generate(file1, file2)).isEqualTo("{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}");
        assertThat(generate(file3, file4)).isEqualTo("{\n"
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
        assertThat(generate(file5, file6)).isEqualTo("{\n"
                + "  - age: 27\n"
                + "    city: Saratov\n"
                + "  + follow: yes\n"
                + "  - name: Max\n"
                + "  + name: Victor\n"
                + "    status: false\n"
                + "}");
        assertThat(generate(file7, file8)).isEqualTo("{\n"
                + "  - age: 27\n"
                + "    city: Saratov\n"
                + "  + follow: yes\n"
                + "  - name: Max\n"
                + "  + name: Victor\n"
                + "    status: false\n"
                + "}");
    }
}
