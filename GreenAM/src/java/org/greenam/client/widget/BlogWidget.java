/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;

/**
 *
 * @author Michael
 */
public class BlogWidget extends VerticalPanel {
    
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final Button editblogButton = new Button("Add a blog entry");
    private final RichTextArea textArea = new RichTextArea();
    private final UserServiceAsync async = GWT.create(UserService.class);
    
    public BlogWidget() {
        
        textArea.setEnabled(true);
        textArea.setText("BLOGG");  
        
        add(textArea);
        add(editblogButton);
        
               
        userInfo.hasAccess(new AsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Has access failed in BlogWidget.\n" + caught);
            }

            @Override
            public void onSuccess(Boolean result) {
                editblogButton.setVisible(result);
            }
        });
        
    }
}
