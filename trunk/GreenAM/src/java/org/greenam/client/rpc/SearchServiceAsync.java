/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import org.greenam.client.rpc.jdo.Record;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import org.greenam.client.rpc.jdo.Album;

/**
 *
 * @author Emil
 */
public interface SearchServiceAsync {

    public void search(String s, AsyncCallback<List<Record>> callback);
    public void searchForTitlesBeginingWith(String s, 
            AsyncCallback<List<String>> callback);

    public void searchArtist(Long id, AsyncCallback<List<Record>> callback);
    public void searchArtist(String name, AsyncCallback<List<Record>> callback);
    
    public void searchTitle(Long id, AsyncCallback<Record> callback);
    public void searchTitle(String name, AsyncCallback<List<Record>> callback);
    
    public void searchAlbum(Long id, AsyncCallback<Album> callback);
    public void searchAlbum(String name, AsyncCallback<List<Album>> callback);

    public void searchGenre(int genre, AsyncCallback<List<Record>> callback);
}
