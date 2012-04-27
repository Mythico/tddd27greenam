/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import java.util.List;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Blog;
import org.greenam.client.domain.Event;

/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("rpc/artistservice")
public interface ArtistService extends RemoteService {

    public List<Event> getEvents(Artist artist);

    public ArrayList<Blog> getBlog(Artist artist);

    public void editBiography(String bio, Long artistId);
    
    public void postEvent(Event event);
    
    public void deleteEvent(Event event);

    public void postBlog(Blog blog);
    
    public void deleteBlog(Artist artist);
    public void deleteBlog(Blog blog);    
}
