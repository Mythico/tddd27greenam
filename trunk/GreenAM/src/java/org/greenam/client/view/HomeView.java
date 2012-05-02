/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author Emil
 */
public class HomeView extends VerticalPanel{

    public HomeView(ViewController viewController) {
        setStyleName("gam-BaseWidget");
        setWidth("600px");
        setHeight("600px");
        add(new Label("Home View"));
    }
    
    
}
