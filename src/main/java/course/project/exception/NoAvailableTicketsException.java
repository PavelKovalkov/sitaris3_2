package course.project.exception;

public class NoAvailableTicketsException extends BadRequestException {
    private static final String ERROR_MESSAGE = "Регистрация на данный рейс окончена(все билеты куплены)";

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
