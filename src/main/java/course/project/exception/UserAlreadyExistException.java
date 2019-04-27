package course.project.exception;

public class UserAlreadyExistException extends BadRequestException {
    private static final String ERROR_MESSAGE = "Пользователь с данным email уже зарегистрирован";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
