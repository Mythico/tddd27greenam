/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.exception;

import java.io.Serializable;

/**
 *
 * @author Emil
 */
public class AccessException extends Exception implements Serializable{

    public AccessException() {
    }

    public AccessException(Throwable cause) {
        super(cause);
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessException(String message) {
        super(message);
    }
    
}
