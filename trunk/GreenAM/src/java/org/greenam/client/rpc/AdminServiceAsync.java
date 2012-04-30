/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import org.greenam.client.domain.AdminRequest;
import org.greenam.client.domain.Album;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Record;

/**
 *
 * @author Emil
 */
public interface AdminServiceAsync {
    
    public void makeArtist(AdminRequest request, AsyncCallback callback);

    public void deleteArtist(Artist artist, AsyncCallback callback);

    public void deleteAlbum(Album album, AsyncCallback callback);
    
    public void deleteRecord(Record record, AsyncCallback callback);

    public void deleteRequest(AdminRequest request, AsyncCallback callback);

    public void listArtists(AsyncCallback<List<Artist>> callback);

    public void listArtistRequests(AsyncCallback<List<AdminRequest>> callback);

}
