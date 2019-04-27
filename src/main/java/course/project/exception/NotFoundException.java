package course.project.exception;

public abstract class NotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return this.getErrorMessage();
    }

    protected abstract String getErrorMessage();
}
