/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.LinkedList;

/**
 *
 * @author Emil
 */
public interface MusicSearchServiceAsync {

    public void search(String s, AsyncCallback<LinkedList<Record>> callback);
    public void searchForTitlesBeginingWith(String s, 
            AsyncCallback<LinkedList<String>> callback);

    public void searchArtist(int artistId, 
            AsyncCallback<LinkedList<Record>> callback);
    public void searchArtist(String name, 
            AsyncCallback<LinkedList<Record>> callback);
    
    public void searchTitle(int artistId, 
            AsyncCallback<LinkedList<Record>> callback);
    public void searchTitle(String name, 
            AsyncCallback<LinkedList<Record>> callback);
    
    public void searchAlbum(int albumId, 
            AsyncCallback<LinkedList<Record>> callback);
    public void searchAlbum(String album, 
            AsyncCallback<LinkedList<Record>> callback);

    public void searchGenre(int genre, 
            AsyncCallback<LinkedList<Record>> callback);
    public void searchGenre(String genre, 
            AsyncCallback<LinkedList<Record>> callback);
}
