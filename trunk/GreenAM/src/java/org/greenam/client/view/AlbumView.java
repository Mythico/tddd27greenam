/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;
import org.greenam.client.widget.AlbumListWidget;

/**
 *
 * @author Emil
 */
public class AlbumView extends VerticalPanel {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final ScrollPanel scrollPanel = new ScrollPanel();
    //Commands used when clicking the menu.
    private final AlbumListWidget albumListWidget;

    public AlbumView(ViewController viewController) {
        setStyleName("gam-ContentView");

        albumListWidget = new AlbumListWidget(viewController);
        scrollPanel.add(albumListWidget);
        add(scrollPanel);
    }    
}
