/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Blog;
import org.greenam.client.domain.Comment;
import org.greenam.client.domain.Event;

/**
 * An asynchronous service interface for fetching 
 * and sending data about artists.
 * 
 * @author Emil
 * @author Michael
 */
public interface ArtistServiceAsync {  
    
    public void getEvents(Artist artist, AsyncCallback<List<Event>> callback);

    public void getBlog(Artist artist, AsyncCallback<ArrayList<Blog>> callback);
    
    public void getComment(Blog blog, AsyncCallback<ArrayList<Comment>> callback);

    public void editBiography(String bio, Long artistId, AsyncCallback callback);
    
    public void postEvent(Event event, AsyncCallback callback);
    
    public void deleteEvent(Event event, AsyncCallback callback);

    public void postBlog(Blog blog, AsyncCallback callback);
    
    public void postComment(Comment comment, AsyncCallback callback);
    
    public void deleteBlog(Artist artist, AsyncCallback callback);
    
    public void deleteBlog(Blog blog, AsyncCallback callback);    
}
