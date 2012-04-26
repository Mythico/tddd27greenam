/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.greenam.client.domain.AdminRequest;
import org.greenam.client.domain.Artist;

/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("rpc/adminservice")
public interface AdminService extends RemoteService {

    public void makeArtist(AdminRequest request);

    public void deleteArtist(Artist artist);
    
    public void deleteRequest(AdminRequest request);
    
    public List<Artist> listArtists();
    
    public List<AdminRequest> listArtistRequest();
}