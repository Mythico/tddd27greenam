/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import org.greenam.client.view.ViewController;

/**
 * The Search Widget provides a small search box.
 * 
 * @author Emil
 * @author Michael
 */
public class SearchWidget extends BaseWidget {

    private final Button searchButton = new Button("Search");
    private final SuggestBox searchBox = new SuggestBox();
    private final HorizontalPanel panel = new HorizontalPanel();

    public SearchWidget(final ViewController viewController) {
        super(viewController, false);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        searchBox.addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {

                    viewController.setSearchView(searchBox.getText());
                }
            }
        });

        searchButton.addClickHandler(
                new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        viewController.setSearchView(searchBox.getText());
                    }
                });


        panel.add(searchBox);
        panel.add(searchButton);
        add(panel);
    }
}
