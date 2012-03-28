/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.shared.Receiver;
import org.greenam.shared.proxy.UserProxy;
import org.greenam.shared.service.ApplicationRequestFactory;
import org.greenam.shared.service.ApplicationRequestFactory.UserRequestContext;
import org.greenam.shared.service.ClientFactory;

/**
 *
 * @author Emil
 */
public class LoginWidget extends HorizontalPanel {

    private final Button loginButton = new Button("Login");
    private final ApplicationRequestFactory rf = ClientFactory.getRequestFactory();
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

        UserRequestContext reqCtx = rf.userRequest();
        reqCtx.getCurrentUser().with("name").fire(new Receiver<UserProxy>() {

            @Override
            public void onSuccess(UserProxy response) {
                if (response == null) { //No user is loggin
                    loginButton.setText("Login");
                    loggedInLabel.setText("");
                } else {
                    loginButton.setText("Logout");
                    loggedInLabel.setText(response.getName());
                }
            }
        });
    }
}
