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
    //@Persistent
    private ArrayList<Blog> blogs = new ArrayList<Blog>();
    private Set<Long> albums;
    
    public Artist() {
    }

    public Artist(Long userId, String name) {
        this.userId = userId;
        this.name = name;
        this.biography = "";
        
        //this.events = events;
    }

    public Long getUserId() {
        return userId;
    }
    
    public Set<Long> getAlbums() {
        return albums;
    }    

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public ArrayList<Blog> getBlog() {
        return blogs;
    }

    public void setBlog(ArrayList<Blog> blogs) {
        this.blogs = blogs;
    }
    
//    public List<Event> getEvents() {
//        return events;
//    }
//
//    public void setEvents(List<Event> events) {
//        this.events = events;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
