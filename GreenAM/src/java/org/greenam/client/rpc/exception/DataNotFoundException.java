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
public class DataNotFoundException extends Exception implements Serializable{

    public DataNotFoundException() {
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(String message) {
        super(message);
    }
    
    
}
