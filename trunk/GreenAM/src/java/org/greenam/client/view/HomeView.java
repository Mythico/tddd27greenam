package org.greenam.client.view;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *  This is the HomeView, the view you see on the homepage when you first
 *  start the site or press the logo button.
 * 
 *  @author Emil
 *  @author Michael
 */
public class HomeView extends VerticalPanel{

    public HomeView(ViewController viewController) {
        setStyleName("gam-BaseWidget");
        setWidth("600px");
        setHeight("600px");
        add(new HTML("<H1>Technical Difficulities</H1>"
                + ""));
    }
    
    
}
