package course.project.exception;

import org.springframework.http.HttpStatus;

public abstract class ExpectedException extends RuntimeException {
    @Override
    public String getMessage() {
        return this.getErrorMessage();
    }

    public abstract HttpStatus getHttpStatus();

    protected abstract String getErrorMessage();
}
