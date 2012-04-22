/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class SearchWidget extends HorizontalPanel {

    private final Button searchButton = new Button("Search");
    private final SuggestBox searchBox = new SuggestBox();

    public SearchWidget(final ViewController viewController) {
        searchBox.addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getSource().equals(KeyCodes.KEY_ENTER)) {
                    viewController.setSearchView(searchBox.getText());
                    searchBox.setText("");
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


        add(searchBox);
        add(searchButton);
    }
}
