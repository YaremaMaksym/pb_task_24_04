package yaremax.com.pb_task_24_04.service.parser.strategy;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.exception.FileParsingException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class XmlFileParserStrategy<T> implements FileParserStrategy<T> {

    @Override
    public String getSupportedExtension() {
        return "xml";
    }

    @Override
    public Optional<List<T>> parse(Optional<File> fileOptional, Class<T> entityClass) {
        return fileOptional.flatMap(file -> {
            try {
                XmlMapper xmlMapper = new XmlMapper();
                CollectionType listType = xmlMapper.getTypeFactory().constructCollectionType(List.class, entityClass);
                return Optional.of(xmlMapper.readValue(file, listType));
            } catch (JsonParseException e) {
                throw new FileParsingException("Failed to parse XML file", e);
            } catch (IOException e) {
                throw new FileParsingException("Failed to parse XML file", e);
            }
        });
    }
}
