/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.greenam.client.widget.AlbumListWidget;
import org.greenam.client.widget.RecordListWidget;
import org.greenam.shared.service.ApplicationRequestFactory;
import org.greenam.shared.service.ApplicationRequestFactory.RecordRequestContext;
import org.greenam.shared.service.ClientFactory;

/**
 *
 * @author Emil
 */
public class SearchResultView extends VerticalPanel {

    private ScrollPanel scrollPanel;
    private final RecordListWidget recordList;
    private final AlbumListWidget albumList;
    private final ApplicationRequestFactory rf = ClientFactory.getRequestFactory();
    
    public SearchResultView(ViewController viewController) {
        setStyleName("gam-ContentView");
        recordList = new RecordListWidget(viewController);
        albumList = new AlbumListWidget(viewController);
        
        scrollPanel = new ScrollPanel(recordList);

        add(scrollPanel);
    }
    
    public void search(String search){
        scrollPanel.setWidget(recordList);
        RecordRequestContext reqCtx = rf.recordRequest();
        reqCtx.search(search).with("title", "artists", "genre", "tags")
                .fire(recordList.callback); 
    }
}