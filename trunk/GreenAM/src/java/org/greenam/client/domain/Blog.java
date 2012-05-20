/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Date;

/**
 * Blog is a datastore object containg an entry, a id of an artist and a date
 * @author Emil
 * @author Michael
 */
@Entity
public class Blog extends DatastoreObject {
     
    private String entry;
    private Long artistId;
    private Date date;
    
    public Blog(){
        
    }
    
    /**
     * Create a blog with an entry and an artist.
     * The dates is created automatically.
     * @param entry A blog entry
     * @param artist An artist.
     */
    public Blog(String entry, Artist artist) {
        this.entry = entry;
        this.date = new Date();
        artistId = artist.getId();
    }
    
    /**
     * Get the entry.
     * @return An entry.
     */
    public String getEntry() {
        return entry;
    }
    
    /**
     * Get the artist id.
     * @return An artist id.
     */
    public Long getArtistId() {
        return artistId;
    }
    
    /**
     * Get the date this blog was posted.
     * @return A date.
     */
    public Date getDate() {
        return date;
    }    
}
