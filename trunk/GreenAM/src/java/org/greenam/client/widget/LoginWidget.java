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
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class LoginWidget extends HorizontalPanel {

    private final Button loginButton = new Button("Login");
    private final UserServiceAsync async = GWT.create(UserService.class);
    private final Label loginLabel = new Label();
    private final ViewController viewController;

    public LoginWidget(final ViewController viewController) {
        this.viewController = viewController;
        loginButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.Location.replace("/_ah/login_required");
            }
        });
        loginLabel.addStyleName("gam-Link");
        add(loginLabel);
        add(loginButton);


        async.getCurrentUser(new AsyncCallback<User>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("LoginWidget RPC throwable\n" + caught);
            }

            @Override
            public void onSuccess(final User result) {
                if (result == null) { //No user is loggin
                    loginButton.setText("Login");
                    loginLabel.setText("");
                    viewController.logout();
                } else {
                    loginButton.setText("Logout");
                    loginLabel.setText("Logged in as: " + result.getName());
                    loginLabel.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                            viewController.setUserView();
                        }
                    });
                }
            }
        });


    }
}
