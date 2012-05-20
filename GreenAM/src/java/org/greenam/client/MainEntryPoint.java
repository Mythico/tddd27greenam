package org.greenam.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.view.ViewController;
import org.greenam.client.widget.LoginWidget;
import org.greenam.client.widget.SearchWidget;

/**
 * Main entry point.
 *
 * @author Emil
 * @author Michael
 */
public class MainEntryPoint implements EntryPoint {

    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     * 
     * Creates a basic grid layout with login button, logo and searchbar at the
     * top and a content panel in the center.
     */
    @Override
    public void onModuleLoad() {
        RootLayoutPanel.get().setStyleName("gam-Page");
        final FlexTable grid = new FlexTable();
        final ViewController viewController = new ViewController();

        final Image logo = new Image("img/logo.png");
        logo.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                viewController.setHomeView();
            }
        });
        grid.setWidget(0, 0, new LoginWidget(viewController));
        grid.setWidget(0, 1, logo);
        
        grid.setWidget(0, 2, new SearchWidget(viewController));
        grid.setWidget(1, 1, viewController);
        
        grid.setSize("100%", "100%");
        grid.getColumnFormatter().setWidth(0, "25%");
        grid.getColumnFormatter().setWidth(1, "50%");
        grid.getColumnFormatter().setWidth(2, "25%");
        grid.getFlexCellFormatter().setHeight(0, 1, "250px");
        
        grid.getFlexCellFormatter().setAlignment(0, 0, 
                HasHorizontalAlignment.ALIGN_LEFT, 
                HasVerticalAlignment.ALIGN_TOP);
        grid.getFlexCellFormatter().setAlignment(0, 1, 
                HasHorizontalAlignment.ALIGN_CENTER, 
                HasVerticalAlignment.ALIGN_TOP);
        grid.getFlexCellFormatter().setAlignment(0, 2, 
                HasHorizontalAlignment.ALIGN_RIGHT, 
                HasVerticalAlignment.ALIGN_TOP);
        grid.getFlexCellFormatter().setAlignment(1, 1, 
                HasHorizontalAlignment.ALIGN_CENTER, 
                HasVerticalAlignment.ALIGN_TOP);
        
        RootLayoutPanel.get().add(grid);
    }    
    
}
