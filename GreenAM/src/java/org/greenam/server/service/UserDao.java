/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.service;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.greenam.server.domain.Artist;
import org.greenam.server.domain.User;

/**
 *
 * @author Emil
 */
public class UserDao extends ObjectifyDao <User>{

    private UserService userService = UserServiceFactory.getUserService();

    public Boolean hasAccess() {
        if (!userService.isUserLoggedIn()) {
            return false;
        }
        //Create an useraccount if the user dosn't allready have one.
        String fid = getFederatedId();
        getOrCreateUser(fid);

        return true;
    }

    public Boolean hasAccess(Long userId) {
        if (!userService.isUserLoggedIn()) {
            return false;
        }
        //Create an useraccount if the user dosn't allready have one.
        String fid = getFederatedId();
        return getOrCreateUser(fid) == userId;
    }

    public Long makeArtist(Long userId) {
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

    public void deleteArtist(Long artistId) {
    }

    public Artist getArtist(Long artistId) {
        Objectify ofy = ObjectifyService.begin();

        Artist artist = ofy.get(Artist.class, artistId);
        return artist;
    }
    
    public void saveArtist(Artist artist) {
    }
    
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
