/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Set;

/**
 *
 * @author Emil
 */
@Entity
public class Album extends DatastoreObject {

    private String title;
    private Set<Long> artistIds;
    private Set<Long> recordIds;
      
    public Album(String title, Set<Long> artistIds, Set<Long> recordIds) {
        this.title = title;
        this.artistIds = artistIds;
        this.recordIds = recordIds;
    }

    public Set<Long> getArtistIds() {
        return artistIds;
    }

    public void setArtistIds(Set<Long> artistIds) {
        this.artistIds = artistIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Long> getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(Set<Long> recordIds) {
        this.recordIds = recordIds;
    }
    
    
}
