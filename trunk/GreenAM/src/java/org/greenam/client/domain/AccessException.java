package org.greenam.client.domain;

/**
 * Access Exception is thrown when client is trying to access information
 * it dosn't have access to.
 * 
 * @author Emil
 * @author Michael
 */
public class AccessException extends RuntimeException{

    public AccessException(Throwable cause) {
        super(cause);
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessException(String message) {
        super(message);
    }

    public AccessException() {
    }
    
    
}
