package org.greenam.server.rpc;

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

    
    /**
     * Checks if there is a user that is login.
     *
     * @return True if a user has login, otherwise false.
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
    
    /**
     * Checks if the current user has access to pages that requires
     * artist-access.
     *
     * @param artistId
     * @return False if the current user doesn't have access or that there is no
     * user login, otherwise true.
     */
    @Override
    public boolean hasAccess(Long artistId) {
        return super.hasAccess(artistId);
    }

    /**
     * Checks if the current user is an administrator.
     *
     * @return True if the user is an administrator, otherwise false.
     */
    @Override
    public boolean isAdmin() {
        return super.isAdmin();
    }   
    
    /**
     * Get artist by id.
     * @param artistId An artist id.
     * @return An artist.
     */
    @Override
    public Artist getArtist(Long artistId) {
        Artist artist = ofy.get(Artist.class, artistId);
        return artist;
    }

    /**
     * Get the user that is currenly logged in.
     * 
     * @return Null if the user isn't login, otherwise an User object.
     */
    @Override
    public User getCurrentUser() {
        if (!isLogin()) {
            return null;
        }
        String fid = getFederatedId();
        return getOrCreateUser(fid);
    }
    
    /**
     * Get a list of the names from a list of artist ids.
     * 
     * @param ids A list of artist ids.
     * @return A list of linkobjects linking names to artist ids.
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

    /**
     * Add money to a User.
     * 
     * @param amount The amount of money to be added.
     */
    @Override
    public User addMoney(int amount) {
        User user = getCurrentUser();
        user.addMoney(amount);
        save(user);
        return user;
    }

    /**
     * Sends a request to the admins about becoming an Artist.
     * @param msg A message to an administrator
     * @param type The type of the message.
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
