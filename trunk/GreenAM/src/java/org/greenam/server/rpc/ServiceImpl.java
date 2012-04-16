/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.greenam.client.domain.*;

/**
 *
 * @author Emil
 */
public abstract class ServiceImpl extends RemoteServiceServlet {

    protected UserService userService = UserServiceFactory.getUserService();

    static {
        ObjectifyService.register(Album.class);
        ObjectifyService.register(Artist.class);
        ObjectifyService.register(Blog.class);
        ObjectifyService.register(Event.class);
        ObjectifyService.register(Record.class);
        ObjectifyService.register(User.class);
    }

    protected boolean hasAccess(Long artistId) {
        if (!userService.isUserLoggedIn()) {
            return false;
        }
        //Create an useraccount if the user dosn't allready have one.
        String fid = getFederatedId();
        Long accessingUser = getOrCreateUser(fid).getId();
        
        Objectify ofy = ObjectifyService.begin();
        Artist artist = ofy.query(Artist.class).filter("userId", accessingUser).get();
        if(artist == null){ //User is not an artist
            return false;
        }
        
        return artist.getId() == artistId;
    }
    
    /**
     * Fetches the internal userid for a federatedId. If there is no internal id
     * it will create one.
     *
     * @param fid
     * @return A userid.
     */
    protected User getOrCreateUser(String fid) {
        Objectify ofy = ObjectifyService.begin();

        
        User user = ofy.query(User.class).filter("federatedId", fid).get();
        if (user == null) {//First time user.
            user = new User(fid, userService.getCurrentUser().getEmail()); //TODO: add so user can specify their name
            ofy.put(user);
        }

        return user;
    }

    protected String getFederatedId() {
        String fid = userService.getCurrentUser().getFederatedIdentity();
        if (fid == null) { //Should only happen in debugmode.
            fid = userService.getCurrentUser().getEmail();
        }
        return fid;
    }
}
