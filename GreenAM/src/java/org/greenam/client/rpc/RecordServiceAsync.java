/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import org.greenam.client.domain.Album;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.LinkObject;
import org.greenam.client.domain.Record;

/**
 *
 * @author Emil
 */
public interface RecordServiceAsync {

    void search(String s, AsyncCallback<List<Record>> callback);

    void getBlobStoreUploadUrl(AsyncCallback<String> callback);
    
    void getAlbums(Artist artist, AsyncCallback<List<Album>> callback);
    
     void getAlbumNamesFromRecords(List<Long> recordIds, AsyncCallback<List<LinkObject<String>>> albumsSet);
}