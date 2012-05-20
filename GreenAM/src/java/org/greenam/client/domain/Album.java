package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.LinkedList;
import java.util.List;

/**
 * Album is a datastore object that has a name and a list of record ids.
 * 
 * @author Emil
 * @author Michael
 */
@Entity
public class Album extends DatastoreObject {

    private String title;
    private List<Long> recordIds;

    public Album() {
    }
      
    /**
     * Creates a new album with a title and a record.
     * @param title A title.
     * @param recordId A record id.
     */    
    public Album(String title, Long recordId) {
        this.title = title;
        recordIds = new LinkedList<Long>();
        recordIds.add(recordId);
    }

    /**
     * Get the title of the album.
     * @return A title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get a list of records ids.
     * @return A list containing record ids.
     */
    public List<Long> getRecordIds() {
        return recordIds;
    }

    /**
     * Add a record to this album.
     * @param id A record id.
     */
    public void addRecord(Long id) {
        recordIds.add(id);
    }
    
    
}
