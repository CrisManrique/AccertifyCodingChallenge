package org.accertify.exceptions;

public class AccertifyRuntimeException extends RuntimeException{
    public AccertifyRuntimeException(Exception e){
        super(e);
    }
    public AccertifyRuntimeException(String message) {
        super(message);
    }

    public AccertifyRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
