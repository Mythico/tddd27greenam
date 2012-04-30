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
import com.google.gwt.user.client.ui.VerticalPanel;
import javax.swing.ImageIcon;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class LoginWidget extends BaseWidget {

    private final Button loginButton = new Button("Login");
    private final UserServiceAsync async = GWT.create(UserService.class);
    private final Label loginLabel = new Label();
    private final Label moneyLabel = new Label();
    private final ViewController viewController;
    private final HorizontalPanel hp = new HorizontalPanel();

    public LoginWidget(final ViewController viewController) {
        this.viewController = viewController;
        loginButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.Location.replace("/_ah/login_required");
            }
        });
        loginLabel.addStyleName("gam-Link");
        hp.add(loginLabel);
        hp.add(loginButton);
        add(hp);
        add(moneyLabel);
        
        viewController.registerForUserUpdates(this);
        
        async.getCurrentUser(new AsyncCallback<User>() {

            @Override
            public void onFailure(Throwable caught) {
                setError("LoginWidget RPC throwable", caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(final User result) {
                clearStatus();
                if (result == null) { //No user is loggin
                    loginButton.setText("Login");
                    loginLabel.setText("");
                    moneyLabel.setText("");
                    viewController.logout();
                } else {
                    loginButton.setText("Logout");
                    loginLabel.setText("Logged in as: " + result.getName());
                    moneyLabel.setText(" Money: " + viewController.getUser().getMoney());
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

    @Override
    public void update(User user) {
        moneyLabel.setText(" Money: " + user.getMoney());
    }
    
    
}
