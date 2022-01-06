package pl.sages.javadevpro.projecttwo.domain.exception;

public class DuplicateRecordException extends RuntimeException{

    public DuplicateRecordException(String message) {
        super(message);
    }

}