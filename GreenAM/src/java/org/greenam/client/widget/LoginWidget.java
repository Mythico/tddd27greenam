/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.rpc.AccessService;
import org.greenam.client.rpc.AccessServiceAsync;

/**
 *
 * @author Emil
 */
public class LoginWidget extends HorizontalPanel {

    private final Button loginButton = new Button("Login");
    private final AccessServiceAsync async = GWT.create(AccessService.class);
    private final Label loggedInLabel = new Label();
    
    public LoginWidget() {
        loginButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.Location.replace("/_ah/login_required");
            }
        });
        
        add(loginButton);
        add(loggedInLabel);
        
        //Get the name of the user currently logged in
        async.userLoggedIn(new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Callback för userLoggedIn failade! + \n" + caught);
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(String result) {
                loggedInLabel.setText(result);
            }
        });
        
        async.hasAccess(callback);
        }
    AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

        @Override
        public void onFailure(Throwable caught) {
            //TODO: handle this exception.
            Window.alert("TODO: Handle this exception + \n" + caught);
        }

        @Override
        public void onSuccess(Boolean login) {
            loginButton.setText(login ? "Logout" : "Login");
        }
    };
}
