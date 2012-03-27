/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author Emil
 */
@PersistenceCapable
public class Record implements Ijdo ,Serializable{
    
    @PrimaryKey
    @Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String title;
    @Persistent
    private Long album;
    @Persistent
    private Long artist;
    @Persistent
    private int genre;
    //@Persistent
    //private int[] tags;
    
  
    public Record() {      
    }
    
    

    public Record(String title, Album album,
            Artist artist, int genre/*, int[] tags*/) {
        this.title = title;
        this.album = album.getId();
        this.artist = artist.getId();
//        this.genre = genre;
//        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public Long getAlbum() {
        return album;
    }

    public Long getArtist() {
        return artist;
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
    
}
