/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.greenam.client.rpc.jdo.Album;
import org.greenam.client.rpc.jdo.Artist;
import org.greenam.client.rpc.jdo.Record;

/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("searchservice")
public interface SearchService extends RemoteService {

    public List<Record> search(String s);
    public List<String> searchForTitlesBeginingWith(String s);
    
    
    public Record searchArtist(Long id);
    public List<Record> searchArtist(String name);
    
    public Record searchTitle(Long id);
    public List<Record> searchTitle(String name);    
    
    public Album searchAlbum(Long id);
    public List<Album> searchAlbum(String name);
    
    public List<Record> searchGenre(int genre);
}
