/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.service;

import java.util.List;
import org.greenam.server.domain.Artist;
import org.greenam.server.domain.Blog;
import org.greenam.server.domain.Event;

/**
 *
 * @author Emil
 */
public class ArtistDao extends ObjectifyDao<Artist>{
    
    public List<Event> getEvents(Artist artist, int month){
        return null;
    }
    
    public List<Blog> getBlogs(Artist artist, int page){
        return null;
    }
    
    public void postEvent(Artist artist, Event event){
        
    }
    
    public void postBlog(Artist artist, Blog blog){
        
    }
    
    
}
