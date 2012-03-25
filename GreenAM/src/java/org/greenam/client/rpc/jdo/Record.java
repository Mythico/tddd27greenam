/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import java.io.Serializable;

/**
 *
 * @author Emil
 */
public class Record implements Serializable{
    
    public int recordId;
    public String title;
    public String album;
    public Long artistId;
    public int genre;
    public int[] tags;
    
  
    public Record() {      
    }
    
    

    public Record(int recordId, String title, String album,
            Long artistId, int genre, int[] tags) {
        this.title = title;
        this.album = album;
        this.artistId = artistId;
        this.genre = genre;
        this.tags = tags;
    }
}
