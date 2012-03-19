/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.LinkedList;

/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("musicsearchservice")
public interface MusicSearchService extends RemoteService {

    public LinkedList<Record> search(String s);
    public LinkedList<String> searchForTitlesBeginingWith(String s);
    
    
    public LinkedList<Record> searchArtist(int artistId);
    public LinkedList<Record> searchArtist(String name);
    
    public LinkedList<Record> searchTitle(int titleId);
    public LinkedList<Record> searchTitle(String title);    
    
    public LinkedList<Record> searchAlbum(int albumId);
    public LinkedList<Record> searchAlbum(String album);
    
    public LinkedList<Record> searchGenre(int genre);
    public LinkedList<Record> searchGenre(String genre);
}
