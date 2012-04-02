/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Emil
 */
@Entity
public class Artist extends DatastoreObject {

    private Long userId;
    private String name;
    private String biography;
    //@Persistent
    private ArrayList<String> blogPosts;
    private Set<Long> albums;
    
    public Artist() {
    }

    public Artist(Long userId, String name) {
        this.userId = userId;
        this.name = name;
        this.biography = "";
        this.blogPosts = blogPosts;
        
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

    public ArrayList<String> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(ArrayList<String> blogPosts) {
        this.blogPosts = blogPosts;
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
