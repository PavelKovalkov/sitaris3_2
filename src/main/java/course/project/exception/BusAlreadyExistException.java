package course.project.exception;

public class BusAlreadyExistException extends BadRequestException {
    private static final String ERROR_MESSAGE = "Автобус с данным номером уже зарегистрирован в системе";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
