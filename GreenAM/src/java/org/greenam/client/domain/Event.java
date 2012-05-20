package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Date;

/**
 * A simple wrapper class containing a date and a short string
 * @author Emil
 * @author Michael
 */
@Entity
public class Event extends DatastoreObject {
    
    public Long artistId;
    public Date date;
    public String message;

    public Event() {
    }
   
    /**
     * Create an event with an id of the artist the event belongs to, a date
     * when the event is occurring and a message.
     * @param artistId
     * @param date
     * @param message 
     */
    public Event(Long artistId, Date date, String message) {
        this.artistId = artistId;
        this.date = date;
        this.message = message;
    }

    /**
     * Get the date the event is occurring.
     * @return A date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the message of the event.
     * @return A message.
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Get the id of the artist that posted this event.
     * @return An artist id.
     */
    public Long getArtistId() {
        return artistId;
    }
    
    
    
}
