/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.util.LinkedList;
import java.util.List;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.UserService;

/**
 *
 * @author Emil
 */
public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public boolean isLogin() {
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
    public boolean isAdmin() {
        System.out.println("IsAdmin: " + super.isAdmin());
        return super.isAdmin();
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
    public void deleteArtist(Artist artist) {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to delete artists.");
        }        
        delete(artist);
    }

    @Override
    public Artist getArtist(Long artistId) {
        Objectify ofy = ObjectifyService.begin();

        Artist artist = ofy.get(Artist.class, artistId);
        return artist;
    }
    
    @Override
    public List<Artist> getAllArtists() {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to fetch all artists.");
        }
        
        Objectify ofy = ObjectifyService.begin();
        return ofy.query(Artist.class).list();
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
    public List<LinkObject<String>> getArtistNames(List<Long> ids) {
        Objectify ofy = ObjectifyService.begin();
        
        List<LinkObject<String>> list = new LinkedList<LinkObject<String>>();
        
        if(ids.isEmpty()){
            return list;
        }
        
        for(Artist artist : ofy.query(Artist.class).filter("id in", ids)){
            Long id = artist.getId();
            String name = artist.getName();
            list.add(new LinkObject<String>(id, id, name));
        }
        
        return list;
    }
    
    /**
     * Takes a user and look if that user is also an artist. If so it will 
     * return the artist, otherwise it will return null.
     * @param user A user.
     * @return An artistId or null if no artist was found.
     */
    @Override
    public Artist getAsArtist(User user){
        Objectify ofy = ObjectifyService.begin();
        Artist a = ofy.query(Artist.class).filter("userId", user.getId()).get();
        return a;
    }

    @Override
    public User addMoney(int amount) {
        User user = getCurrentUser();
        user.addMoney(amount);
        save(user);
        return user;
    }

}
