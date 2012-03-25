/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import org.greenam.client.rpc.jdo.Event;
import org.greenam.client.rpc.jdo.BlogPage;
import org.greenam.client.rpc.jdo.Blog;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

/**
 *
 * @author Emil
 */
public interface ArtistInfoServiceAsync {

    //Public
    public void getBiogarphy(Long artistId, AsyncCallback<String> callback);
    public void getBlog(Long artistId, int page, AsyncCallback<BlogPage> callback);
    public void getEventCalender(Long artistId, int month, AsyncCallback<List<Event>> callback);
    
    
    
    //Need to be login
    
    public void postBlogComment(Long blogId, String content, AsyncCallback c);
    
    //Need to be author.
    public void postBlog(Long artistId, Blog blog, AsyncCallback c);
    public void postEvent(Long artistId, Event event, AsyncCallback c);
    public void postBiography(Long artistId, String bio, AsyncCallback c);
}
