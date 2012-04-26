/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Date;

/**
 *
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
    
    public Blog(String entry, Artist artist) {
        this.entry = entry;
        this.date = new Date();
        artistId = artist.getId();
    }
    
    public String getEntry() {
        return entry;
    }
    
    public Long getArtistId() {
        return artistId;
    }
    
    public Date getDate() {
        return date;
    }    
}
