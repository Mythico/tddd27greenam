/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import org.greenam.client.view.ViewController;
import org.greenam.shared.service.ApplicationRequestFactory;
import org.greenam.shared.service.ClientFactory;

/**
 *
 * @author Emil
 */
public class SearchWidget extends HorizontalPanel {

    private final ApplicationRequestFactory rf = ClientFactory.getRequestFactory();
    private final Button searchButton = new Button("Search");
    private final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    private final SuggestBox searchBox = new SuggestBox(oracle);

    public SearchWidget(final ViewController viewController) {
        searchBox.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
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
