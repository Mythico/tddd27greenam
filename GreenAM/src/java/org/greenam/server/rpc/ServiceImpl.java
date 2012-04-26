/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.util.List;
import org.greenam.client.domain.*;

/**
 *
 * @author Emil
 */
public abstract class ServiceImpl extends RemoteServiceServlet {

    protected UserService userService = UserServiceFactory.getUserService();
    protected Objectify ofy = ObjectifyService.begin();

    static {
        ObjectifyService.register(Album.class);
        ObjectifyService.register(Artist.class);
        ObjectifyService.register(Blog.class);
        ObjectifyService.register(Event.class);
        ObjectifyService.register(Record.class);
        ObjectifyService.register(User.class);
        ObjectifyService.register(AdminRequest.class);
    }

    /**
     * Checks if there is a user that is login.
     *
     * @return True if a user has login, otherwise false.
     */
    protected boolean isLogin() {
        return userService.isUserLoggedIn();
    }

    /**
     * Checks if the current user has access to pages that requires
     * artist-access.
     *
     * @param artistId
     * @return False if the current user doesn't have access or that there is no
     * user login, otherwise true.
     */
    protected boolean hasAccess(Long artistId) {
        if (!isLogin()) {
            return false;
        }
        //Create an useraccount if the user dosn't allready have one.
        String fid = getFederatedId();
        Long accessingUser = getOrCreateUser(fid).getId();

        Objectify ofy = ObjectifyService.begin();
        Artist artist = ofy.query(Artist.class).filter("userId", accessingUser).get();
        if (artist == null) { //User is not an artist
            return false;
        }
        return artist.getId().equals(artistId);
    }

    /**
     * Checks if the current user is an administrator.
     *
     * @return True if the user is an administrator, otherwise false.
     */
    protected boolean isAdmin() {
        return isLogin() && userService.isUserAdmin();
    }

    /**
     * Fetches the internal userid for a federatedId. If there is no internal id
     * it will create one.
     *
     * @param fid
     * @return A user.
     */
    protected User getOrCreateUser(String fid) {
        User user = ofy.query(User.class).filter("federatedId", fid).get();
        if (user == null) {//First time user.
            user = new User(fid, userService.getCurrentUser().getEmail());
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

    /**
     * Fetches the current user.
     *
     * @return Returns a user or null if no one has login.
     */
    protected User getCurrentUser() {
        String fid = getFederatedId();
        User user = ofy.query(User.class).filter("federatedId", fid).get();
        return user;
    }

    protected void save(User user) {
        ofy.put(user);
    }

    protected void save(Artist artist) {
        ofy.put(artist);
    }

    protected void save(Album album) {
        ofy.put(album);
    }

    protected void save(Record record) {
        ofy.put(record);
    }

    protected void delete(Album album) {
        ofy.delete(Album.class, album.getId());
    }

    protected void delete(Record record) {
        ofy.delete(Record.class, record.getId());
        removeReferenses(record);

        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        BlobKey bk = new BlobKey(record.getBlobKey());
        blobstoreService.delete(bk);
        //TODO: Remove actual blob
    }

    protected void delete(Artist artist) {
        ofy.delete(Artist.class, artist.getId());

        //Remove blogs and events owned by an artist.
        List<Key<Event>> evtKeys = ofy.query(Event.class).filter("artistId", artist.getId()).listKeys();
        ofy.delete(evtKeys);

        List<Key<Blog>> blogKeys = ofy.query(Blog.class).filter("artistId", artist.getId()).listKeys();
        ofy.delete(blogKeys);

        removeReferenses(artist);
    }

    private void removeReferenses(Record record) {
        //Remove all referenses to record in albums.
        for (Album album : ofy.query(Album.class).filter("recordIds", record.getId())) {
            List<Long> recordIds = album.getRecordIds();
            recordIds.remove(album.getId());

            //Remove an album if it doesn't have any records, otherwise save it.
            if (recordIds.isEmpty()) {
                delete(album);
            } else {
                save(album);
            }
        }

        //Remove all referenses to record in user.
        for (User user : ofy.query(User.class).filter("recordIds", record.getId())) {
            List<Long> recordIds = user.getBoughtRecords();
            recordIds.remove(record.getId());
            save(user);
        }
    }

    private void removeReferenses(Artist artist) {
        //Remove all referenses to artist in albums.
        for (Album album : ofy.query(Album.class).filter("artistIds", artist.getId())) {
            List<Long> recordIds = album.getRecordIds();
            recordIds.remove(album.getId());

            //Remove an album if it doesn't have any records, otherwise save it.
            if (recordIds.isEmpty()) {
                delete(album);
            } else {
                save(album);
            }
        }

        //Remove all referenses to artist in record.
        for (Record record : ofy.query(Record.class).filter("artistIds", artist.getId())) {
            List<Long> artistIds = record.getArtistIds();
            artistIds.remove(record.getId());

            //Remove an album if it doesn't have any records, otherwise save it.
            if (artistIds.isEmpty()) {
                delete(record);
            } else {
                save(record);
            }
        }

    }
}
