package course.project.exception.handler;

import course.project.exception.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = ExpectedException.class)
    protected ResponseEntity expectedException(ExpectedException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity badRequest(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();

        exception.getBindingResult()
            .getFieldErrors()
            .forEach(
                f -> errors.add(f.getDefaultMessage())
            );

        errors.sort(String::compareTo);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity anyException(Exception ex) {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

