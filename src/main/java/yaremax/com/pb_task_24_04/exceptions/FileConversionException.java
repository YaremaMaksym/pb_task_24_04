package yaremax.com.pb_task_24_04.exceptions;

public class FileConversionException extends RuntimeException {
    public FileConversionException(String message) {
        super(message);
    }

    public FileConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
