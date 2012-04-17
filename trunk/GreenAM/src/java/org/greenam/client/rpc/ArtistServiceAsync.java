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
import org.greenam.client.domain.Event;

/**
 *
 * @author Emil
 */
public interface ArtistServiceAsync {  
    
    public void getEvents(Artist artist, AsyncCallback<List<Event>> callback);

    public void getBlog(Artist artist, AsyncCallback<ArrayList<Blog>> callback);

    public void postEvent(Event event, AsyncCallback callback);

    public void postBlog(Blog blog, AsyncCallback callback);
    
    public void save(Artist artist, AsyncCallback<Void> callback);
    public void update(Artist artist, AsyncCallback<Artist> callback);
}
