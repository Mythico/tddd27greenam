/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

    public List<Event> getEvents(Artist artist, int month);

    public List<Blog> getBlogs(Artist artist, int page);

    public void postEvent(Artist artist, Event event);

    public void postBlog(Artist artist, Blog blog);
    
    public void save(Artist artist);
    public Artist update(Artist artist);
}
