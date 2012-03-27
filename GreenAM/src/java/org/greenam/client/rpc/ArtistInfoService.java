/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.greenam.client.rpc.exception.AccessException;
import org.greenam.client.rpc.exception.DataNotFoundException;
import org.greenam.client.rpc.jdo.Blog;
import org.greenam.client.rpc.jdo.Event;



/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("rpc/artistinfoservice")
public interface ArtistInfoService extends RemoteService {

    public String getBiogarphy(Long artistId) throws DataNotFoundException ;
    //public BlogPage getBlog(Long artistId, int page)
    //        throws DataNotFoundException ;
    public List<Event> getEventCalender(Long artistId, int month)
            throws DataNotFoundException ;
    
    
    
    //Need to be login
    
    public void postBlogComment(Long blogId, String content)
            throws DataNotFoundException, AccessException;
    
    //Need to be author.
    public void postBlog(Long artistId, Blog blog)
            throws DataNotFoundException, AccessException;
    public void postEvent(Long artistId, Event event)
            throws DataNotFoundException, AccessException;
    public void postBiography(Long artistId, String bio)
            throws DataNotFoundException, AccessException;
}
