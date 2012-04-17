/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.util.HashMap;
import java.util.HashSet;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.UserService;

/**
 *
 * @author Emil
 */
public class UserServiceImpl extends ServiceImpl implements UserService {

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
    public boolean hasAccess(Long artistId) {
        return super.hasAccess(artistId);
    }

    @Override
    public long makeArtist(Long userId, String name) {
        //TODO: Check for moderator or admin status
        Objectify ofy = ObjectifyService.begin();

        Artist artist = ofy.query(Artist.class).filter("userId", userId).get();
        if (artist != null) {
            //TODO: Throw error? Artist allready an artist?
        }
        artist = new Artist(userId, name);
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
        if (!userService.isUserLoggedIn()) {
            return null;
        }
        String fid = getFederatedId();
        return getOrCreateUser(fid);
    }

    @Override
    public HashMap<Long, Artist> getArtists(HashSet<Long> ids) {
        Objectify ofy = ObjectifyService.begin();
        return new HashMap<Long, Artist>(ofy.get(Artist.class, ids));
    }
    
    
}
