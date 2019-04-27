package course.project.exception;

public class BusTripDoesNotExistException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Рейс с данным номером не найден";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
