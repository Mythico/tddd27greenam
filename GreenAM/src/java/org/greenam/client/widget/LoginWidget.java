/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 *
 * @author Emil
 */
public class LoginWidget extends VerticalPanel {

    private final Button button = new Button("Login");
    private final DialogBox box = new DialogBox(true);

    public LoginWidget() {
        
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                box.center();
            }
        });
                
        Frame frame = new Frame("_ah/login_required");
        frame.setSize("600px", "600px");
        box.add(frame);
        add(button);
    }
}
