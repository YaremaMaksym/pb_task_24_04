package yaremax.com.pb_task_24_04.exceptions;

public class FileParsingException extends RuntimeException {
    public FileParsingException(String message) {
        super(message);
    }

    public FileParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
