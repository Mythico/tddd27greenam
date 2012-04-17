/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

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


        grid.resize(1, 2);
        grid.setText(0, 1, "Name");
    }

    @Override
    protected void update(List<Album> list) {
        int row = list.size() + 1;
        grid.resize(row, 6);

        int i = 1;
        for (final Album album : list) {
            Label name = new Label(album.getTitle());

            name.setStyleName("gam-RecordListWidgetLink");


            grid.setWidget(i, 0, name);
            i++;
        }
    }
    
    
}
