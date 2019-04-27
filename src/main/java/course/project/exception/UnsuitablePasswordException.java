package course.project.exception;

public class UnsuitablePasswordException extends BadRequestException {
    private static final String ERROR_MESSAGE = "Неверный пароль";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
