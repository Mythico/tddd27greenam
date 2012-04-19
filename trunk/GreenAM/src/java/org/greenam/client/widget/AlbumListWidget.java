/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import java.util.List;
import org.greenam.client.domain.Album;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class AlbumListWidget extends ListWidget<Album> {

    public AlbumListWidget(ViewController viewController) {
        super(viewController);


        grid.resize(1, 3);
        grid.setText(0, 0, "Name");
        grid.setText(0, 1, "Records");
        grid.setText(0, 2, "Artists");
    }

    @Override
    protected void update(List<Album> list) {
        int row = list.size() + 1;
        grid.resize(row, 3);

        int i = 1;
        for (final Album album : list) {
            Label name = new Label(album.getTitle());
            name.setStyleName("gam-RecordListWidgetLink");
            
            HorizontalPanel rhp = new HorizontalPanel();
            for(Long rid :album.getRecordIds()){
                Label ridLabel = new Label("" + rid);
                ridLabel.setStyleName("gam-RecordListWidgetLink");
                rhp.add(ridLabel);
            }
            HorizontalPanel ahp = new HorizontalPanel();
            for(Long aid : album.getArtistIds()){
                
                Label ridLabel = new Label("" + aid);
                ridLabel.setStyleName("gam-RecordListWidgetLink");
                ahp.add(ridLabel);
            }



            grid.setWidget(i, 0, name);
            grid.setWidget(i, 1, rhp);
            grid.setWidget(i, 2, ahp);
            i++;
        }
    }
    
    
}
