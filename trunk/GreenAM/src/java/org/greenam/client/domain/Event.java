/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Date;

/**
 * A simple wrapper class containing a date and a short string
 * @author Emil
 */
@Entity
public class Event extends DatastoreObject {
    
    public Date date;
    public String info;
    
}
