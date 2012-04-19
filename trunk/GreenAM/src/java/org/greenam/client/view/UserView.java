/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;

/**
 *
 * @author Emil
 */
public class UserView extends VerticalPanel {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
        
    private final Label userLabel = new Label("Unknown");
    private final Label moneyLabel = new Label("Unknown");
    private final Button artistPageButton = new Button("Go to artist Page");
    private User user;
    private Long artistId = null;

    public UserView(final ViewController viewController) {
        setStyleName("gam-ContentView");


        add(userLabel);
        add(moneyLabel);
        add(artistPageButton);
        
        artistPageButton.setVisible(false);
        artistPageButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if(artistId != null){
                    viewController.setArtistView(artistId);
                }
            }
        });
    }
    
    public void setUser(User user){
        this.user = user;
        
        userLabel.setText(user.getName());
        moneyLabel.setText("$" + user.getMoney());
        
        userInfo.getAsArtist(user, new AsyncCallback<Long>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Long result) {
                artistId = result;
                artistPageButton.setVisible(artistId != null);
            }
        });
        
    }

    
}
