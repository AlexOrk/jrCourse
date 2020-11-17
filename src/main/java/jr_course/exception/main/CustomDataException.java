package jr_course.exception.main;

import org.springframework.http.HttpStatus;

public abstract class CustomDataException extends RuntimeException {
    public CustomDataException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
}
