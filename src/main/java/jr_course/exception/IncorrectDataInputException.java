package jr_course.exception;


import jr_course.exception.main.CustomDataException;
import org.springframework.http.HttpStatus;

public class IncorrectDataInputException extends CustomDataException {

    public IncorrectDataInputException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}