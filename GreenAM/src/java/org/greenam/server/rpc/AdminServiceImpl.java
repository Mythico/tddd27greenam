/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import java.util.LinkedList;
import java.util.List;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.AdminService;

/**
 *
 * @author Emil
 */
public class AdminServiceImpl extends ServiceImpl implements AdminService {

    @Override
    public void makeArtist(AdminRequest request) {
        //TODO: Check for moderator or admin status
        
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

    @Override
    public void deleteArtist(Artist artist) {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to delete artists.");
        }        
        delete(artist);
    }
    @Override
    public void deleteRequest(AdminRequest request) {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to delete request.");
        }        
        ofy.delete(request);
    }
    
    @Override
    public List<Artist> listArtists() {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to fetch all artists.");
        }
        return ofy.query(Artist.class).list();
    }
    
    @Override
    public List<AdminRequest> listArtistRequests() {
        if(!isAdmin()){
            throw new AccessException("You have to be an admin to fetch all artists.");
        }
        Query<AdminRequest> q = ofy.query(AdminRequest.class);
        return q.filter("type", AdminRequest.TYPE_ARTIST).list();
    }

    
}
