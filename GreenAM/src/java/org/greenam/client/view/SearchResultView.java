package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.greenam.client.rpc.RecordService;
import org.greenam.client.rpc.RecordServiceAsync;
import org.greenam.client.widget.AlbumListWidget;
import org.greenam.client.widget.RecordListWidget;

/**
 *  SearchResultView is the view that shows the results of a new search, 
 *  replaces the Homeview.
 * 
 * @author Emil
 * @author Michael
 */
public class SearchResultView extends VerticalPanel {

    private ScrollPanel scrollPanel;
    private final RecordListWidget recordList;
    private final AlbumListWidget albumList;
    private final RecordServiceAsync async = GWT.create(RecordService.class);
    
    public SearchResultView(ViewController viewController) {
        setStyleName("gam-BaseWidget");
        setHeight("600px");
        recordList = new RecordListWidget(viewController);
        albumList = new AlbumListWidget(viewController);
        
        scrollPanel = new ScrollPanel(recordList);

        add(scrollPanel);
    }
    
    public void search(String search){
        scrollPanel.setWidget(recordList);
        
        async.search(search, recordList.callback);
    }
}