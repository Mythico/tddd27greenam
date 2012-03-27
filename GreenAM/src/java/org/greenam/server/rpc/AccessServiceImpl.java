/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.greenam.client.rpc.AccessService;
import org.greenam.client.rpc.exception.AccessException;
import org.greenam.client.rpc.jdo.Artist;
import org.greenam.client.rpc.jdo.User;

/**
 *
 * @author Emil
 */
public class AccessServiceImpl extends RemoteServiceServlet implements AccessService {

    private UserService userService = UserServiceFactory.getUserService();

    /**
     * Checks if the the user is login to the site.
     *
     * @return True if the user is logged in, otherwise false.
     */
    @Override
    public boolean hasAccess() {
        if (!userService.isUserLoggedIn()) {
            return false;
        }

        //Create an useraccount if the user dosn't allready have one.
        String fid = userService.getCurrentUser().getFederatedIdentity();
        if (fid == null) { //Should only happen in debugmode.
            fid = userService.getCurrentUser().getEmail();
        }
        getUserId(fid);

        return true;
    }
    
    /**
     * Checks if the current user has the user id.
     *
     * @param userId
     * @return
     */
    @Override
    public boolean hasAccess(Long userId) {

        //Create an useraccount if the user dosn't allready have one.
        String fid = userService.getCurrentUser().getFederatedIdentity();
        if (fid == null) { //Should only happen in debugmode.
            fid = userService.getCurrentUser().getEmail();
        }
        return getUserId(fid) == userId;
    }

    @Override
    public String userLoggedIn(){
        if (userService.isUserLoggedIn())
            return userService.getCurrentUser().getEmail();
        else
            return "Not logged in";
    }

    /**
     * Fetches the internal userid for a federatedId. If there is no internal id
     * it will create one.
     *
     * @param federatedId
     * @return A userid.
     */
    public Long getUserId(String federatedId) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        Long id = null;

        try {
            Query query = pm.newQuery(User.class, "federatedId == '" + federatedId + "'");
            List<User> list = (List<User>) query.execute();
            System.out.println("------------------------------------\n"
                    + "TEMP: add check or better sulotion, size: " + list.size()
                    + "\n--------------------------------------------");
            User user = null;
            if (list.isEmpty()) {
                user = new org.greenam.client.rpc.jdo.User(federatedId);
                pm.makePersistent(user);
            } else {
                user = list.get(0);
            }
            id = user.getId();

        } finally {
            pm.close();
        }

        if (id == null) {
            //Throw some exception, it shouldn't happen.
        }
        return id;
    }

    @Override
    public void setArtistStatus(String user, boolean bool)
            throws AccessException {
        if (!userService.isUserAdmin()) {
            //Check if moderator
            //else
            throw new AccessException("Only administrators can add moderators");
        }

        Long id = getUserId(user);

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            Artist artist = pm.getObjectById(Artist.class, id);
            if (artist == null && bool) {
                artist = new Artist();
                pm.makePersistent(artist);
            } else if (artist != null && !bool) {
                pm.removeUserObject(artist);
            }
        } finally {
            pm.close();
        }
    }

    @Override
    public void setModeratorStatus(String user, boolean b)
            throws AccessException {
        if (!userService.isUserAdmin()) {
            throw new AccessException("Only administrators can add moderators");
        }
    }
}
