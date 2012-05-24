package org.greenam.server.rpc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenam.client.domain.*;
import org.greenam.client.rpc.ArtistService;

/**
 * An implementation of the ArtistService interface.
 * Some functions needs access to be used. Has to be the right artist that
 * uses them.
 * 
 * @author Emil 
 * @author Michael
 */
public class ArtistServiceImpl extends ServiceImpl implements ArtistService {

    /**
     * Get all events posted by an artist.
     * @param artist An artist.
     * @return A list of events.
     */
    @Override
    public List<Event> getEvents(Artist artist) {
        Date today = new Date();

        List<Event> list = ofy.query(Event.class).filter("artistId", artist.getId()).filter("date >=", today).order("date").list();
        return list;
    }

    /**
     * Get all blog entries made by the Artist.
     * 
     * @param artist An artist
     * @return A list of blogs.
     */
    @Override
    public ArrayList<Blog> getBlog(Artist artist) {
        List<Blog> list = ofy.query(Blog.class).filter("artistId", artist.getId()).order("date").list();
        return new ArrayList<Blog>(list);
    }
    
    /**
     * Get all the blog comments of a blog entry.
     * 
     * @param blog A blog.
     * @return A list of comments.
     */
    @Override
    public ArrayList<Comment> getComment(Blog blog) {
        List<Comment> list = ofy.query(Comment.class).filter("blogId", blog.getId()).order("date").list();
        return new ArrayList<Comment>(list);
    }

    /**
     * Save the biography of an artist if they have access to do so.
     * 
     * @param bio A biography to be saved.
     * @param artistId An artist id
     */
    @Override
    public void editBiography(String bio, Long artistId) {
        if (!hasAccess(artistId)) {
            throw new AccessException("You don't have access to edit the biography.");
        }
        Artist artist = ofy.get(Artist.class, artistId);
        artist.setBiography(bio);
        ofy.put(artist);
    }
    
    /**
     * Save a new event if the artist has access to do so.
     * 
     * @param event An event to be posted.
     */
    @Override
    public void postEvent(Event event) {
        if (!hasAccess(event.artistId)) {
            throw new AccessException("You don't have access to post new events.");
        }
        ofy.put(event);
    }
    
    /**
     * Delete a event if the artist has access to do so.
     * 
     * @param event An event to be deleted.
     */
    @Override
    public void deleteEvent(Event event) {
        if (!hasAccess(event.artistId)) {
            throw new AccessException("You don't have access to post new events.");
        }        
        ofy.delete(Event.class, event.getId());
    }

    /**
     * Saves a blog entry if the artist has access to do so.
     * 
     * @param blog A blog to be posted.
     */
    @Override
    public void postBlog(Blog blog) {
        if (!hasAccess(blog.getArtistId())) {
            throw new AccessException("You don't have access to post on this blog.");
        }
        ofy.put(blog);
    }
    
    /**
     * Saves comment to blog entry, no access needed since anyone should be able
     * to do it.
     * 
     * @param comment A comment to be posted.
     */
    @Override
    public void postComment(Comment comment) {
        ofy.put(comment);
    }
    
    /**      
     * Deletes all blog entry if artist has access to do so.
     * 
     * @param artist An artist whose blogs are being deleted.
     */
    @Override
    public void deleteBlog(Artist artist) {
        if (!hasAccess(artist.getId())) {
            throw new AccessException("You don't have access to clear the blogs.");
        }
        List<Blog> blog = ofy.query(Blog.class).filter("artistId", artist.getId()).list();
        ofy.delete(blog);
    }
    
    /**
     * Deletes single blog entry if artist has access to do so.
     * 
     * @param blog A blog to be deleted.
     */
    @Override
    public void deleteBlog(Blog blog) {
        if(!hasAccess(blog.getArtistId())){
            throw new AccessException("You don't have access to delete this blog.");
        }
        ofy.delete(Blog.class, blog.getId());
    }
}
