package pl.sages.javadevpro.projecttwo.domain.exception;

@Deprecated
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String exception) {
        super(exception);
    }

}