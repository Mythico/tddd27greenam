/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 * A simple wrapper class containing a date and a short string
 * @author Emil
 */
@Entity
public class Event extends DatastoreObject {
    
    public Long artistId;
    public Date date;
    public String message;

    public Event() {
    }
   
    public Event(Long artistId, Date date, String message) {
        this.artistId = artistId;
        this.date = date;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Long getArtistId() {
        return artistId;
    }
    
    
    
}
