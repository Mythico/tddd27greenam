/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import java.io.Serializable;
import java.util.Set;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author Emil
 */
@PersistenceCapable
public class Album implements Ijdo ,Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String name;
    @Persistent
    private Set<Artist> artists;
    @Persistent
    private Set<Record> records;

    public Album() {
    }

    
    
    public Album(String name, Set<Artist> artists, Set<Record> records) {
        this.name = name;
        this.artists = artists;
        this.records = records;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public Long getId() {
        return id;
    }

    
    
}
