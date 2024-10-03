package net.onedsix.ffw4j.core;

/** An {@link java.io.IOException} for specifically FFW4J classes.<br>
 * Almost all of it's uses are to wrap around ambiguous exception checks like in {@link FileWrapper#read(Object)}.
 * Its not recommended to call on its own, and use it to add your own re-throw to the stack trace.
 * */
public class FFWException extends Exception {
    public FFWException() {
        super();
    }
    
    public FFWException(String message) {
        super(message);
    }
    public FFWException(Throwable cause) {
        super(cause);
    }
    
    public FFWException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FFWException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
