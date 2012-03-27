/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.ui.Label;
import java.util.Collection;
import org.greenam.client.rpc.jdo.Album;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class AlbumListWidget extends ListWidget<Album> {

    public AlbumListWidget(ViewController viewController) {
        super(viewController);


        resize(1, 2);
        setText(0, 1, "Name");
    }

    @Override
    protected void update(Collection<Album> list) {
        int row = list.size() + 1;
        resize(row, 6);

        int i = 1;
        for (final Album album : list) {
            Label name = new Label(album.getName());

            name.setStyleName("gam-RecordListWidgetLink");


            setWidget(i, 0, name);
            i++;
        }
    }
    
    
}
