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
 * An implementation of the UserService interface.
 * 
 * @author Emil
 * @author Michael
 */
public class UserServiceImpl extends ServiceImpl implements UserService {

    /*
     * Check if a User is logged in or not.
     * 
     * @return true if a user is logged in otherwise false.
     */
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
    
    /*
     * Check if the artist has access.
     */
    @Override
    public boolean hasAccess(Long artistId) {
        return super.hasAccess(artistId);
    }

    /*
     * Check if the user is an admin.
     */
    @Override
    public boolean isAdmin() {
        return super.isAdmin();
    }   
    
    /*
     * Get the artist that is current logged in.
     */
    @Override
    public Artist getArtist(Long artistId) {
        Objectify ofy = ObjectifyService.begin();

        Artist artist = ofy.get(Artist.class, artistId);
        return artist;
    }

    /*
     * Get the user that is currenly logged in.
     */
    @Override
    public User getCurrentUser() {
        if (!userService.isUserLoggedIn()) {
            return null;
        }
        String fid = getFederatedId();
        return getOrCreateUser(fid);
    }
    
    /*
     * Get a list of the names of all the artists that exists in the database.
     */
    @Override
    public List<LinkObject<String>> getArtistNames(List<Long> ids) {        
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
        Artist a = ofy.query(Artist.class).filter("userId", user.getId()).get();
        return a;
    }

    /*
     * Add money to a User.
     */
    @Override
    public User addMoney(int amount) {
        User user = getCurrentUser();
        user.addMoney(amount);
        save(user);
        return user;
    }

    /*
     * Sends a request to the admins about becoming an Artist.
     */
    @Override
    public void sendRequest(String msg, int type) {
        if(!isLogin()){
            throw new AccessException("You have to be login to send requests.");
        }
        Long id = getCurrentUser().getId();
        AdminRequest request = new AdminRequest(type, id, msg);
        ofy.put(request);
    }

}
