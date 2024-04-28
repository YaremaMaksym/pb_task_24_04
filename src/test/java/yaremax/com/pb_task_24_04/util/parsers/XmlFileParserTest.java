package yaremax.com.pb_task_24_04.util.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yaremax.com.pb_task_24_04.animal.AnimalDto;
import yaremax.com.pb_task_24_04.util.parsers.XmlFileParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class XmlFileParserTest {

    private XmlFileParser<AnimalDto> xmlFileParser;

    @BeforeEach
    void setUp() {
        xmlFileParser = new XmlFileParser<>();
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
        List<AnimalDto> dtoList = List.of(
                AnimalDto.builder().name("Milo").type("cat").sex("male").weight(40).cost(51).build(),
                AnimalDto.builder().name("Toby").type("dog").sex("female").weight(7).cost(14).build()
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
        Optional<List<AnimalDto>> optionalDataList = xmlFileParser.parse(xmlFile, AnimalDto.class);

        // Assert
        assertThat(optionalDataList).isPresent();
        assertThat(optionalDataList.get().size()).isEqualTo(2);
        assertThat(optionalDataList.get()).isEqualTo(dtoList);
    }

    @Test
    void parse_InvalidXmlFile_ReturnsEmptyOptional() throws IOException {
        // Arrange
        String invalidXmlContent = "invalid xml content";
        File xmlFile = createTempXmlFile(invalidXmlContent);

        // Act
        Optional<List<AnimalDto>> optionalData = xmlFileParser.parse(xmlFile, AnimalDto.class);

        // Assert
        assertThat(optionalData).isEmpty();
    }
}