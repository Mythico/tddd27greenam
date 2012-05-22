/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.widget.AlbumListWidget;

/**
 * AlbumView is used to display the albums of an artist, with the help of 
 * AlbumListWidget. 
 * 
 * @author Emil
 * @author Michael
 */
public class AlbumView extends VerticalPanel {

    private final ScrollPanel scrollPanel = new ScrollPanel();
    private final AlbumListWidget albumListWidget;

    public AlbumView(ViewController viewController) {
        setStyleName("gam-BaseWidget");

        albumListWidget = new AlbumListWidget(viewController);
        scrollPanel.add(albumListWidget);
        add(scrollPanel);
    }    
}
