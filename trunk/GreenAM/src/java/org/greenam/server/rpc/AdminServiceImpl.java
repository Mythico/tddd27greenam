/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.googlecode.objectify.Query;
import java.util.List;
import org.greenam.client.domain.*;
import org.greenam.client.rpc.AdminService;


/**
 * An implementation of the AdminService interface.
 * All these function requires administrator status or they will throw
 * AccessExceptions.
 * 
 * @author Emil
 * @author Michael
 */
public class AdminServiceImpl extends ServiceImpl implements AdminService {

    /**
     * Makes a user an artist. The user that is giving the artist status to an
     * user has to be an administrator.
     * @param request A request for becomming an artist.
     */
    @Override
    public void makeArtist(AdminRequest request) {
        if(!isAdmin()){
            throw new AccessException("You have to be an administrator to make"
                    + " a user an artist.");
        }
        
        Long userId = request.getUserId();
        Query<Artist> q = ofy.query(Artist.class);
        Artist artist = q.filter("userId", userId).get();
        if (artist != null) {
            //TODO: Throw error? Artist allready an artist?
        }
        artist = new Artist(userId);
        ofy.put(artist);
        ofy.delete(request);
    }

    /**
     * Deletes an artist.
     * @param artist An artist to be deleted.
     */
    @Override
    public void deleteArtist(Artist artist) {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to delete artists.");
        }        
        delete(artist);
    }

    /**
     * Deletes an album.
     * @param album An album to be deleted.
     */
    @Override
    public void deleteAlbum(Album album) {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to delete album.");
        }        
        delete(album);
    }

    /**
     * Deletes a record.
     * @param record An record to be deleted.
     */
    @Override
    public void deleteRecord(Record record) {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to delete record.");
        }        
        delete(record);
    }
    
    /**
     * Deletes a request.
     * @param record An request to be deleted.
     */
    @Override
    public void deleteRequest(AdminRequest request) {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to delete request.");
        }        
        ofy.delete(request);
    }
    
    /**
     * Creates a list of all the artist that is using this service and 
     * returning it.
     * @return A list of artists.
     */
    @Override
    public List<Artist> listArtists() {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to fetch all artists.");
        }
        return ofy.query(Artist.class).list();
    }
    
    /**
     * Creates a list of all the AdminRequest that is requesting artist status
     * and returning it.
     * @return A list of adminRequest.
     */    
    @Override
    public List<AdminRequest> listArtistRequests() {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to fetch all artists.");
        }
        Query<AdminRequest> q = ofy.query(AdminRequest.class);
        return q.filter("type", AdminRequest.TYPE_ARTIST).list();
    }

    
}
