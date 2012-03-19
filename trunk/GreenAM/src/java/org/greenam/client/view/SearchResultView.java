/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import java.util.LinkedList;
import org.greenam.client.rpc.MusicSearchService;
import org.greenam.client.rpc.MusicSearchServiceAsync;
import org.greenam.client.rpc.Record;
import org.greenam.client.widget.RecordListWidget;

/**
 *
 * @author Emil
 */
public class SearchResultView extends VerticalPanel {

    private ScrollPanel scrollPanel;
    private final ViewController viewController;
    private final RecordListWidget recList;
    private final MusicSearchServiceAsync async = GWT.create(MusicSearchService.class);

    public SearchResultView(ViewController viewController) {
        setStyleName("gam-ContentView");
        this.viewController = viewController;
        recList = new RecordListWidget(viewController);
        scrollPanel = new ScrollPanel(recList);

        add(scrollPanel);
    }
    
    public void search(String search){
        async.search(search, recList.callback);        
    }
    

    public void searchAlbum(int albumId) {
        async.searchAlbum(albumId, recList.callback);
    }
    
    public void searchAlbum(String album) {
        async.searchAlbum(album, recList.callback);
    }

    public void searchTitle(int titleId) {
        async.searchTitle(titleId, recList.callback);
    }

    public void searchTitle(String title) {
        async.searchTitle(title, recList.callback);
    }

    public void searchGenre(int genre) {
        async.searchGenre(genre, recList.callback);        
    }

    void searchGenre(String genre) {
        async.searchGenre(genre, recList.callback);        
    }
}
