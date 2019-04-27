package course.project.exception;

public class BusTripAlreadyExistException extends BadRequestException {
    private static final String ERROR_MESSAGE = "Рейс с данным номером уже зарегистрирован в системе";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
