/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.ArtistService;

/**
 *
 * @author Emil
 */
public class ArtistServiceImpl extends ServiceImpl implements ArtistService {

    @Override
    public List<Event> getEvents(Artist artist) {
        Date today = new Date();

        List<Event> list = ofy.query(Event.class).filter("artistId", artist.getId()).filter("date >=", today).order("date").list();
        return list;
    }

    @Override
    public ArrayList<Blog> getBlog(Artist artist) {
        List<Blog> list = ofy.query(Blog.class).filter("artistId", artist.getId()).order("date").list();
        return new ArrayList<Blog>(list);
    }

    @Override
    public void editBiography(String bio, Long artistId) {
        if (!hasAccess(artistId)) {
            throw new AccessException("You don't have access to edit the biography.");
        }
        Artist artist = ofy.get(Artist.class, artistId);
        artist.setBiography(bio);
        ofy.put(artist);
    }
    
    @Override
    public void postEvent(Event event) {
        if (!hasAccess(event.artistId)) {
            throw new AccessException("You don't have access to post new events.");
        }
        ofy.put(event);
    }
    
    @Override
    public void deleteEvent(Event event) {
        if (!hasAccess(event.artistId)) {
            throw new AccessException("You don't have access to post new events.");
        }        
        ofy.delete(Event.class, event.getId());
    }

    @Override
    public void postBlog(Blog blog) {
        if (!hasAccess(blog.getArtistId())) {
            throw new AccessException("You don't have access to post on this blog.");
        }
        ofy.put(blog);
    }
    
    @Override
    public void deleteBlog(Artist artist) {
        if (!hasAccess(artist.getId())) {
            throw new AccessException("You don't have access to clear the blogs.");
        }
        List<Blog> blog = ofy.query(Blog.class).filter("artistId", artist.getId()).list();
        ofy.delete(blog);
    }
    
    @Override
    public void deleteBlog(Blog blog) {
        if(!hasAccess(blog.getArtistId())){
            throw new AccessException("You don't have access to delete this blog.");
        }
        ofy.delete(Blog.class, blog.getId());
    }
}
