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
 *
 * @author Emil
 */
@RemoteServiceRelativePath("rpc/recordservice")
public interface RecordService extends RemoteService {

    public List<Record> search(String s);

    String getBlobStoreUploadUrl();
    
    List<Album> getAlbums(Artist artist);
    List<LinkObject<String>> getAlbumNamesFromRecords(List<Long> recordIds);
    
    void buyRecord(Record record);
}
