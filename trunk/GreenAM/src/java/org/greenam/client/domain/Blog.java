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
 */
@Entity
public class Blog extends DatastoreObject {
     
    public String entry;
    public Long artistId;
    private Date date;
    
    public Blog() {
        this.entry = "";
        this.date = null;
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
    
    public void addEntry(String entry, Date date) {
        this.entry = entry;
        this.date = date;
    }
}
