package net.onedsix.ffw4j.core;

public class FFWRuntimeException extends RuntimeException {
    public FFWRuntimeException() {
        super();
    }
    
    public FFWRuntimeException(String message) {
        super(message);
    }
    public FFWRuntimeException(Throwable cause) {
        super(cause);
    }
    
    public FFWRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FFWRuntimeException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
