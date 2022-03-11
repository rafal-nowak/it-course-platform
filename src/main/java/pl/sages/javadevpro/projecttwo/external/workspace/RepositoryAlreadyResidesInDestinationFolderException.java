package pl.sages.javadevpro.projecttwo.external.workspace;


public class RepositoryAlreadyResidesInDestinationFolderException extends RuntimeException{

    public RepositoryAlreadyResidesInDestinationFolderException(String message) {
        super(message);
    }

    public RepositoryAlreadyResidesInDestinationFolderException(String message, Throwable ex) {
        super(message, ex);
    }

}