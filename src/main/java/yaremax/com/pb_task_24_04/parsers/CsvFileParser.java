package yaremax.com.pb_task_24_04.parsers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import yaremax.com.pb_task_24_04.dto.Parsable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public class CsvFileParser<T extends Parsable> implements FileParser<T> {

    @Override
    public String getSupportedExtension() {
        return "csv";
    }

    @Override
    public Optional<List<T>> parse(File file, Class<T> entityClass) {
        Optional<List<T>> optionalDataList = Optional.empty();

        try(Reader reader = new FileReader(file)) {

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(entityClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            optionalDataList = Optional.of(csvToBean.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return optionalDataList;
    }
}
