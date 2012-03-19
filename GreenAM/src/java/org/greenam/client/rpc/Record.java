/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import java.io.Serializable;

/**
 *
 * @author Emil
 */
public class Record implements Serializable{
    
    private String title;
    private int titleId;
    private String album;
    private int albumId;
    private String artist;
    private int artistId;
    private int genre;
    private int[] tags;

    /**
     * GWT RPC needs a default constructor.
     * It's privet because it should never be used.
     */
    private Record() {      
    }
    
    

    public Record(String title, int titleId, String album, int albumId, String artist, int artistId, int genre, int[] tags) {
        this.title = title;
        this.titleId = titleId;
        this.album = album;
        this.albumId = albumId;
        this.artist = artist;
        this.artistId = artistId;
        this.genre = genre;
        this.tags = tags;
    }

    public String getAlbum() {
        return album;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getArtist() {
        return artist;
    }

    public int getArtistId() {
        return artistId;
    }

    public int getGenre() {
        return genre;
    }

    public int[] getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public int getTitleId() {
        return titleId;
    }
    
    
}
