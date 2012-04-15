/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.util.List;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.ArtistService;

/**
 *
 * @author Emil
 */
public class ArtistServiceImpl extends RemoteServiceServlet implements ArtistService {
    
    Objectify ofy = ObjectifyService.begin();
    
    @Override
    public List<Event> getEvents(Artist artist, int month) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Blog> getBlog(Artist artist) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void postEvent(Artist artist, Event event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void postBlog(Artist artist, Blog blog) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void save(Artist artist) {
        ofy.put(artist);
    }

    @Override
    public Artist update(Artist artist) {
        return ofy.get(Artist.class, artist.getId());
    }
}
