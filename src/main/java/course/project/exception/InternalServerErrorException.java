package course.project.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends ExpectedException {
    private static final String ERROR_MESSAGE = "Внутренняя ошибка сервера";

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
