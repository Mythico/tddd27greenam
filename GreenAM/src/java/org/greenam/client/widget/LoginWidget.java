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
 * Login Widget act as a login button if the user isn't login or as a logout
 * button and user status if the us login. You can also click on the user name
 * to go the user page.
 *
 * @author Emil
 * @author Michael
 */
public class LoginWidget extends BaseWidget {

    private final Button loginButton = new Button("Login");
    private final UserServiceAsync async = GWT.create(UserService.class);
    private final Label loginLabel = new Label();
    private final Label moneyLabel = new Label();
    private final HorizontalPanel hp = new HorizontalPanel();

    public LoginWidget(final ViewController viewController) {
        super(viewController, false);
        loginButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String query = Window.Location.getQueryString();
                Window.Location.replace("/_ah/login_required" + query);
            }
        });
        loginLabel.addStyleName("gam-Link");
        loginLabel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                viewController.setUserView();
            }
        });

        hp.add(loginLabel);
        hp.add(loginButton);
        add(hp);
        add(moneyLabel);

        async.getCurrentUser(getUser);


    }

    @Override
    public void update(User user) {
        moneyLabel.setText(" Money: " + user.getMoney());
    }
    /**
     * Get user is an asynchronous call for getting the current user and set the
     * shape of the login widget depending on the result. If no user is login
     * the result will be null.
     */
    private final AsyncCallback<User> getUser = new AsyncCallback<User>() {

        @Override
        public void onFailure(Throwable caught) {
            setError("LoginWidget RPC throwable", caught.getLocalizedMessage());
        }

        @Override
        public void onSuccess(final User result) {
            clearStatus();
            register();
            if (result == null) { //No user is loggin
                setLoginView();
            } else {
                setLogoutView(result.getName(), result.getMoney());
            }
        }
    };

    /**
     * A helper function for getUser to be able to register this widget 
     * for user updates.
     */
    private void register() {
        viewController.registerForUserUpdates(this);
    }

    /**
     * Set login view will show a button with the name login and will logout any
     * local user that is login.
     */
    private void setLoginView() {
        loginButton.setText("Login");
        loginLabel.setText("");
        moneyLabel.setText("");
        viewController.logout();
    }

    /**
     * Set logout view shows a logout button and the name and money of the
     * current user.
     *
     * @param name Name of the current user.
     * @param money Amount of money the current user has.
     */
    private void setLogoutView(String name, int money) {
        loginButton.setText("Logout");
        loginLabel.setText("Logged in as: " + name);
        moneyLabel.setText(" Money: " + money);
    }
}
