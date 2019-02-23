package course.project.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends ExpectedException {
    private static final String ERROR_MESSAGE = "Пользователь с данным email уже зарегистрирован";

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
