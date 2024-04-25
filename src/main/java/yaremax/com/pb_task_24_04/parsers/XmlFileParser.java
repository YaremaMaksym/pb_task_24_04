package yaremax.com.pb_task_24_04.parsers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yaremax.com.pb_task_24_04.dto.Parsable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class XmlFileParser<T extends Parsable> implements FileParser<T> {

    @Override
    public String getSupportedExtension() {
        return "xml";
    }

    @Override
    public Optional<List<T>> parse(File file, Class<T> entityClass) {
        Optional<List<T>> optionalDataList = Optional.empty();
        try {
            XmlMapper xmlMapper = new XmlMapper();
            CollectionType listType = xmlMapper.getTypeFactory().constructCollectionType(List.class, entityClass);
            optionalDataList = Optional.of(xmlMapper.readValue(file, listType));
        } catch (JsonParseException e) {
            System.out.println("Exception: " + e.getClass() + " occurred during parsing data from xml: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception: " + e.getClass() + " occurred during parsing data from xml: " + e.getMessage());
        }
        return optionalDataList;
    }
}
