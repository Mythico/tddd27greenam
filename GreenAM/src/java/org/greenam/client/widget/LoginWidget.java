/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 *
 * @author Emil
 */
public class LoginWidget extends FormPanel {

    private final DisclosurePanel loginPanel = new DisclosurePanel("Login");
    private final FlexTable loginTable = new FlexTable();
    private final PasswordTextBox usernameBox = new PasswordTextBox();
    private final PasswordTextBox passwordBox = new PasswordTextBox();
    private final Button loginButton = new Button("Login with OpenId");

    public LoginWidget() {
        loginPanel.setStyleName("gam-LoginWidget");
        usernameBox.setName("username");

        passwordBox.setName("password");
        loginButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                submit();
            }
        });
        loginTable.setWidget(0, 0, new Label("Username:"));
        loginTable.setWidget(0, 1, usernameBox);
        loginTable.setWidget(1, 0, new Label("Password:"));
        loginTable.setWidget(1, 1, passwordBox);
        loginTable.setWidget(2, 1, loginButton);
        loginPanel.setContent(loginTable);

        setEncoding(FormPanel.ENCODING_MULTIPART);
        setMethod(FormPanel.METHOD_POST);
        addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                Window.alert(event.getResults());
            }
        });
        String url = "";
        setAction(url);
        add(loginPanel);
    }
}
