/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

/**
 *
 * @author Emil
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
