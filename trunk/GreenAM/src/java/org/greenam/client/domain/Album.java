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
    private List<Long> recordIds;

    public Album() {
    }
      
    
    
    public Album(String title, Long recordId) {
        this.title = title;
        recordIds = new LinkedList<Long>();
        recordIds.add(recordId);
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
        recordIds.add(id);
    }
    
    
}
