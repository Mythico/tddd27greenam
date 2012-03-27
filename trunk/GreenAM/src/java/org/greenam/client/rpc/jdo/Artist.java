/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class Artist implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    Long id;
    //@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    
    //@Persistent //(dependent="true") TODO: make dependent
    //private User user;
    @Persistent
    private String name;
    @Persistent
    private String biography;
    //@Persistent
    //private ArrayList<Blog> blogPosts;
    //@Persistent
    //private Set<Album> albums;
    //@Persistent
    //private List<Event> events;

    public Artist() {
    }

    public Artist(User user, String name, String biography,
            ArrayList<Blog> blogPosts, Set<Album> albums, List<Event> events) {
        //this.user = user;
        this.name = name;
        this.biography = biography;
        //this.blogPosts = blogPosts;
        //this.albums = albums;
        //this.events = events;
    }

    public Long getId() {
        return id;
    }

    //public User getUser() {
    //    return user;
    //}

    //public Set<Album> getAlbums() {
    //    return albums;
    //}

    //public void setAlbums(Set<Album> albums) {
    //    this.albums = albums;
    //}

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

//    public ArrayList<Blog> getBlogPosts() {
//        return blogPosts;
//    }
//
//    public void setBlogPosts(ArrayList<Blog> blogPosts) {
//        this.blogPosts = blogPosts;
//    }
//
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
