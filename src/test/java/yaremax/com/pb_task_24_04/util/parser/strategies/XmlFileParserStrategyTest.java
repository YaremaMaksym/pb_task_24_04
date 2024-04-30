package yaremax.com.pb_task_24_04.util.parser.strategies;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.animal.Animal;
import yaremax.com.pb_task_24_04.exceptions.FileParsingException;
import yaremax.com.pb_task_24_04.util.parser.strategies.XmlFileParserStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class XmlFileParserStrategyTest {

    private XmlFileParserStrategy<Animal> xmlFileParser;

    @BeforeEach
    void setUp() {
        xmlFileParser = new XmlFileParserStrategy<>();
    }

    private File createTempXmlFile(String xmlContent) throws IOException {
        File file = File.createTempFile("temp", ".xml");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(xmlContent);
        }
        return file;
    }

    @Test
    void getSupportedExtension() {
        assertThat("xml").isEqualTo(xmlFileParser.getSupportedExtension());
    }

    @Test
    void parse_ValidXmlFile_ReturnsData() throws IOException {
        // Arrange
        List<Animal> list = List.of(
                Animal.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).build(),
                Animal.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).build()
        );
        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <animals>
                    <animal>
                        <name>Milo</name>
                        <type>cat</type>
                        <sex>male</sex>
                        <weight>40</weight>
                        <cost>51</cost>
                    </animal>
                    <animal>
                        <name>Toby</name>
                        <type>dog</type>
                        <sex>female</sex>
                        <weight>7</weight>
                        <cost>14</cost>
                    </animal>
                </animals>
                """;
        File xmlFile = createTempXmlFile(xmlContent);

        // Act
        Optional<List<Animal>> optionalDataList = xmlFileParser.parse(Optional.of(xmlFile), Animal.class);

        // Assert
        assertThat(optionalDataList).isPresent();
        assertThat(optionalDataList.get()).asList().containsExactlyInAnyOrderElementsOf(list);
    }

    @Test
    void parse_InvalidXmlFile_ThrowsException() throws IOException {
        // Arrange
        String invalidXmlContent = "invalid xml content";
        File xmlFile = createTempXmlFile(invalidXmlContent);

        // Act & Assert
        assertThatThrownBy(() -> xmlFileParser.parse(Optional.of(xmlFile), Animal.class))
                .isInstanceOf(FileParsingException.class);
    }

    @Test
    void parse_NullFile_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> xmlFileParser.parse(null, Animal.class))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void parse_OptionalEmptyInsteadOfFile_ReturnsEmptyOptional() {
        // Act & Assert
        assertThat(xmlFileParser.parse(Optional.empty(), Animal.class))
                .isEmpty();
    }

    @Test
    void parse_EmptyFile_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> xmlFileParser.parse(Optional.of(new File("empty.xml")), Animal.class))
                .isInstanceOf(FileParsingException.class);
    }

    @Test
    void parse_NullClass_ThrowsException() throws IOException {
        // Arrange
        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <animals>
                    <animal>
                        <name>Milo</name>
                        <type>cat</type>
                        <sex>male</sex>
                        <weight>40</weight>
                        <cost>51</cost>
                    </animal>
                    <animal>
                        <name>Toby</name>
                        <type>dog</type>
                        <sex>female</sex>
                        <weight>7</weight>
                        <cost>14</cost>
                    </animal>
                </animals>
                """;
        File xmlFile = createTempXmlFile(xmlContent);

        // Act & Assert
        assertThatThrownBy(() -> xmlFileParser.parse(Optional.of(xmlFile), null))
                .isInstanceOf(NullPointerException.class);
    }
}