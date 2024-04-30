package yaremax.com.pb_task_24_04.util.parser.strategies;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import yaremax.com.pb_task_24_04.exceptions.FileParsingException;
import yaremax.com.pb_task_24_04.markers.Processable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Component
public class CsvFileParserStrategy<T extends Processable> implements FileParserStrategy<T> {

    @Override
    public String getSupportedExtension() {
        return "csv";
    }

    @Override
    public Optional<List<T>> parse(Optional<File> fileOptional, Class<T> entityClass) {
        return fileOptional.flatMap(file -> {
            try(Reader reader = new FileReader(file)) {
                CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                        .withType(entityClass)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                return Optional.of(csvToBean.parse());
            } catch (IOException e) {
                throw new FileParsingException("Failed to parse CSV file", e);
            }
        });
    }
}
