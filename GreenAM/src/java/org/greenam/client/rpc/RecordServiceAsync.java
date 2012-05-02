/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import org.greenam.client.domain.*;

/**
 *
 * @author Emil
 */
public interface RecordServiceAsync {

    void search(String s, AsyncCallback<List<Record>> callback);

    void getBlobStoreUploadUrl(AsyncCallback<String> callback);
    
    void getAlbums(Artist artist, AsyncCallback<List<Album>> callback);
    
     void getAlbumNamesFromRecords(List<Long> recordIds, AsyncCallback<List<LinkObject<String>>> albumsSet);
     
     void buyRecord(Record record, AsyncCallback<User> callback);

    public void getRecords(List<Long> recordIds, AsyncCallback<List<Record>> callback);
}