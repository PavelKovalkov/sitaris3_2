package course.project.exception;

public class BusDoesNotExistException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Автобус с данным номером не найден";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
