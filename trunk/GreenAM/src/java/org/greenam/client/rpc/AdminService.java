/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.greenam.client.domain.AdminRequest;
import org.greenam.client.domain.Album;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Record;

/**
 * A RPC interface for fetching and sending data that requires 
 * administration status.
 * 
 * @author Emil
 * @author Michael
 */
@RemoteServiceRelativePath("rpc/adminservice")
public interface AdminService extends RemoteService {

    public void makeArtist(AdminRequest request);

    public void deleteArtist(Artist artist);
    
    public void deleteAlbum(Album album);
    
    public void deleteRecord(Record record);
    
    public void deleteRequest(AdminRequest request);
    
    public List<Artist> listArtists();
    
    public List<AdminRequest> listArtistRequests();
}