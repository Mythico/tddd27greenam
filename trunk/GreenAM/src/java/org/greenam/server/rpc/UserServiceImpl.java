/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.UserService;

/**
 *
 * @author Emil
 */
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

    static{
        ObjectifyService.register(Album.class);
        ObjectifyService.register(Artist.class);
        ObjectifyService.register(Blog.class);
        ObjectifyService.register(Event.class);
        ObjectifyService.register(Record.class);
        ObjectifyService.register(User.class);
    }
    
    private com.google.appengine.api.users.UserService userService = 
            UserServiceFactory.getUserService();
    
    @Override
    public boolean hasAccess() {
        if (!userService.isUserLoggedIn()) {
            return false;
        }
        //Create an useraccount if the user dosn't allready have one.
        String fid = getFederatedId();
        getOrCreateUser(fid);

        return true;
    }

    @Override
    public boolean hasAccess(Long userId) {
        if (!userService.isUserLoggedIn()) {
            return false;
        }
        //Create an useraccount if the user dosn't allready have one.
        String fid = getFederatedId();
        return getOrCreateUser(fid) == userId;
    }

    @Override
    public long makeArtist(Long userId) {
        //TODO: Check for moderator or admin status
        Objectify ofy = ObjectifyService.begin();

        Artist artist = ofy.query(Artist.class).filter("user", userId).get();
        if (artist != null) {
            //TODO: Throw error? Artist allready an artist?
        }
        artist = new Artist(userId);
        ofy.put(artist);
        return artist.getId();
    }

    @Override
    public void deleteArtist(Long artistId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Artist getArtist(Long artistId) {
        Objectify ofy = ObjectifyService.begin();

        Artist artist = ofy.get(Artist.class, artistId);
        return artist;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
    
    /**
     * Fetches the internal userid for a federatedId. If there is no internal id
     * it will create one.
     *
     * @param fid
     * @return A userid.
     */
    private Long getOrCreateUser(String fid) {
        Objectify ofy = ObjectifyService.begin();

        
        User user = ofy.query(User.class).filter("federatedId", fid).get();
        if (user == null) {//First time user.
            user = new User(fid, userService.getCurrentUser().getEmail()); //TODO: add so user can specify their name
            ofy.put(user);
        }

        return user.getId();
    }

    private String getFederatedId() {
        String fid = userService.getCurrentUser().getFederatedIdentity();
        if (fid == null) { //Should only happen in debugmode.
            fid = userService.getCurrentUser().getEmail();
        }
        return fid;
    }

}
