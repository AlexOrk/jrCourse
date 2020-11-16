package jr_course.exception;

import jr_course.exception.main.CustomDataException;
import org.springframework.http.HttpStatus;

public class DataNotFoundException extends CustomDataException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
