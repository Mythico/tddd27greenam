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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;

/**
 *
 * @author Emil
 */
public class LoginWidget extends HorizontalPanel {

    private final Button loginButton = new Button("Login");
    private final UserServiceAsync async = GWT.create(UserService.class);
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

        async.getCurrentUser(new AsyncCallback<User>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("LoginWidget RPC throwable\n" + caught);
            }

            @Override
            public void onSuccess(User result) {
                if (result == null) { //No user is loggin
                    loginButton.setText("Login");
                    loggedInLabel.setText("");
                } else {
                    loginButton.setText("Logout");
                    loggedInLabel.setText(result.getName());
                }
            }
        });
    }
}
