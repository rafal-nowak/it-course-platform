package pl.sages.javadevpro.projecttwo.domain.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String exception) {
        super(exception);
    }
}
