package io.jatinjindal.exception;

public class ArtCopilotException extends RuntimeException {
    public ArtCopilotException(String message) {
        super(message);
    }

    public ArtCopilotException(String message, Throwable cause) {
        super(message, cause);
    }
}
