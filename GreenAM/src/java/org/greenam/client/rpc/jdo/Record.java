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
public class Record implements Serializable{
    
    @PrimaryKey
    @Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String title;
    @Persistent
    private String album;
    @Persistent
    private Long artistId;
    @Persistent
    private int genre;
    //@Persistent
    //private int[] tags;
    
  
    public Record() {      
    }
    
    

    public Record(String title, String album,
            Long artistId, int genre/*, int[] tags*/) {
        this.title = title;
        this.album = album;
        this.artistId = artistId;
//        this.genre = genre;
//        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public Long getArtistId() {
        return artistId;
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
