/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import java.io.Serializable;
import java.util.Date;

/**
 * A simple wrapper class containing a date and a short string
 * @author Emil
 */
public class Event implements Serializable{
    
    public Date date;
    public String info;
    
}
