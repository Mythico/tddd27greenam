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
        Objectify ofy = ObjectifyService.begin();

        Date today = new Date();

        List<Event> list = ofy.query(Event.class).filter("artistId", artist.getId()).filter("date >=", today).order("date").list();
        return list;
    }

    @Override
    public ArrayList<Blog> getBlog(Artist artist) {
        Objectify ofy = ObjectifyService.begin();

        List<Blog> list = ofy.query(Blog.class).filter("artistId", artist.getId()).order("date").list();
        return new ArrayList<Blog>(list);
    }

    @Override
    public void postEvent(Event event) {
        if (!hasAccess(event.artistId)) {
            return; //TODO: Throw exception?
        }
        System.out.println("Posting " + event);
        Objectify ofy = ObjectifyService.begin();
        ofy.put(event);
    }

    @Override
    public void postBlog(Blog blog) {
        /*if (!hasAccess(blog.artistId)) {
            return; //TODO: Throw exception?
        }*/
        Objectify ofy = ObjectifyService.begin();
        System.out.println("JAG SPARAR " + blog.getEntry());
        ofy.put(blog);
    }

    @Override
    public void save(Artist artist) {
        Objectify ofy = ObjectifyService.begin();
        ofy.put(artist);
    }

    @Override
    public Artist update(Artist artist) {
        Objectify ofy = ObjectifyService.begin();
        return ofy.get(Artist.class, artist.getId());
    }
}
