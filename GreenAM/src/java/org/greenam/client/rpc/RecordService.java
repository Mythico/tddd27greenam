/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.greenam.client.domain.*;

/**
 * A RPC interface for fetching and sending data about records and albums.
 * 
 * @author Emil
 * @author Michael
 */
@RemoteServiceRelativePath("rpc/recordservice")
public interface RecordService extends RemoteService {

    List<Record> search(String s);

    String getBlobStoreUploadUrl();
    
    List<Album> getAlbums(Artist artist);
    
    List<Record> getRecords(List<Long> recordIds);
    
    List<LinkObject<String>> getAlbumNamesFromRecords(List<Long> recordIds);
    
    User buyRecord(Record record);
    
}

