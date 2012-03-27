/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.rpc.SearchService;
import org.greenam.client.rpc.SearchServiceAsync;
import org.greenam.client.widget.AlbumListWidget;
import org.greenam.client.widget.RecordListWidget;

/**
 *
 * @author Emil
 */
public class SearchResultView extends VerticalPanel {

    private ScrollPanel scrollPanel;
    private final RecordListWidget recordList;
    private final AlbumListWidget albumList;
    private final SearchServiceAsync async = GWT.create(SearchService.class);
    
    public SearchResultView(ViewController viewController) {
        setStyleName("gam-ContentView");
        recordList = new RecordListWidget(viewController);
        albumList = new AlbumListWidget(viewController);
        
        scrollPanel = new ScrollPanel(recordList);

        add(scrollPanel);
    }
    
    public void search(String search){
        scrollPanel.setWidget(recordList);
        async.search(search, recordList.callback);        
    }
    

    public void searchAlbum(Long id) {
        scrollPanel.setWidget(albumList);
        //async.searchAlbum(id, albumList.callbackId);
    }
    
    public void searchAlbum(String name) {
        scrollPanel.setWidget(albumList);
        async.searchAlbum(name, albumList.callback);
    }

    public void searchTitle(Long id) {
        scrollPanel.setWidget(recordList);
        //async.searchTitle(id, recordList.callbackId);
    }

    public void searchTitle(String name) {
        scrollPanel.setWidget(recordList);
        async.searchTitle(name, recordList.callback);
    }

    public void searchGenre(int genre) {
        //async.searchGenre(genre, albumList.callback);        
    }

    void searchGenre(String genre) {
        //async.searchGenre(genre, albumList.callback);        
    }
}
