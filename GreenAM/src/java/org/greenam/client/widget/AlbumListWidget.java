/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.ui.Label;
import java.util.List;
import org.greenam.client.view.ViewController;
import org.greenam.shared.proxy.AlbumProxy;

/**
 *
 * @author Emil
 */
public class AlbumListWidget extends ListWidget<AlbumProxy> {

    public AlbumListWidget(ViewController viewController) {
        super(viewController);


        resize(1, 2);
        setText(0, 1, "Name");
    }

    @Override
    protected void update(List<AlbumProxy> list) {
        int row = list.size() + 1;
        resize(row, 6);

        int i = 1;
        for (final AlbumProxy album : list) {
            Label name = new Label(album.getTitle());

            name.setStyleName("gam-RecordListWidgetLink");


            setWidget(i, 0, name);
            i++;
        }
    }
    
    
}
