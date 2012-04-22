/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import java.util.List;

/**
 *
 * @author Emil
 */
@Entity
public class Record extends DatastoreObject {
    
    private String title;
    private List<Long> artistIds;
    private String audioUrl;
    private int price;

    public Record() {
    }
  
    public Record(String title,
            List<Long> artistIds, int price, String audioUrl) {
        this.title = title;
        this.artistIds = artistIds;
        this.price = price;
        this.audioUrl = audioUrl;
    }

    public List<Long> getArtistIds() {
        return artistIds;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getAudioUrl() {
        return audioUrl;
    }
    
    
    
}
