package pl.sages.javadevpro.projecttwo.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String exception) {
        super(exception);
    }

}