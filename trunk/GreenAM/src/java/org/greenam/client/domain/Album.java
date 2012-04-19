/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Emil
 */
@Entity
public class Album extends DatastoreObject {

    private String title;
    private List<Long> artistIds;
    private List<Long> recordIds;

    public Album() {
    }
      
    
    
    public Album(String title, List<Long> artistIds) {
        this.title = title;
        this.artistIds = artistIds;
    }

    public List<Long> getArtistIds() {
        return artistIds;
    }

    public void setArtistIds(List<Long> artistIds) {
        this.artistIds = artistIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(List<Long> recordIds) {
        this.recordIds = recordIds;
    }

    public void addRecord(Long id) {
        if(recordIds == null){
            recordIds = new LinkedList<Long>();
        }
        recordIds.add(id);
    }
    
    
}
