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
    private int genre;
    //@Persistent
    //private int[] tags;
    private String audioUrl;

    public Record() {
    }
  
    public Record(String title,
            List<Long> artistIds, int genre/*, int[] tags*/,
            String audioUrl) {
        this.title = title;
        this.artistIds = artistIds;
//        this.genre = genre;
//        this.tags = tags;
        this.audioUrl = audioUrl;
    }

    public List<Long> getArtistIds() {
        return artistIds;
    }

    public int getGenre() {
        return genre;
    }

    //public int[] getTags() {
    //    return tags;
   // }

    public String getTitle() {
        return title;
    }

    public String getAudioUrl() {
        return audioUrl;
    }
    
    
    
}