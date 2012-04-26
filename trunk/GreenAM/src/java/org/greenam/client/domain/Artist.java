/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Emil
 */
@Entity
public class Artist extends DatastoreObject{

    private Long userId;
    private String name;
    private String biography;
    
    public Artist() {
    }

    public Artist(Long userId) {
        this.userId = userId;
        this.name = "Artist [" + getId() + "]";
        this.biography = "";
        
        //this.events = events;
    }

    public Long getUserId() {
        return userId;
    }
    
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
