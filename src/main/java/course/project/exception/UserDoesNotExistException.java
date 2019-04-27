package course.project.exception;

public class UserDoesNotExistException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Пользователь не найден";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
