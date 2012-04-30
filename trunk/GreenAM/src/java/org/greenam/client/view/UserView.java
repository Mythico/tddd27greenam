/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.AdminService;
import org.greenam.client.rpc.AdminServiceAsync;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;
import org.greenam.client.widget.ArtistListWidget;
import org.greenam.client.widget.ArtistRequestListWidget;
import org.greenam.client.widget.RequestArtistWidget;

/**
 *
 * @author Emil
 */
public class UserView extends VerticalPanel {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final Label userLabel = new Label("Unknown");
    private final Label moneyLabel = new Label("Unknown");
    private final Button artistPageButton = new Button("Go to artist Page");
    private final Button addMoneyButton = new Button("Add 100$");
    private final AdminPanel adminPanel;
    private final RequestArtistWidget requestWidget = new RequestArtistWidget();
    private final ViewController viewController;

    public UserView(final ViewController viewController) {
        setStyleName("gam-ContentView");

        this.viewController = viewController;
        adminPanel = new AdminPanel(viewController);

        add(userLabel);
        add(moneyLabel);
        add(artistPageButton);

        artistPageButton.setVisible(false);
        artistPageButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Artist artist = viewController.getArtist();
                if (artist != null) {
                    viewController.setArtistView(artist.getId());
                }
            }
        });

        addMoneyButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                userInfo.addMoney(100, new AsyncCallback<User>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    public void onSuccess(User result) {
                        User user = viewController.getUser();
                        user.addMoney(100);
                        viewController.updateUser(user);

                    }
                });
            }
        });
        add(addMoneyButton);
        add(requestWidget);
        add(adminPanel);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        adminPanel.setVisible(visible);

        if (visible) {
            User user = viewController.getUser();
            moneyLabel.setText("$" + user.getMoney());
            artistPageButton.setVisible(viewController.getArtist() != null);
            userLabel.setText(user.getName());
        }

    }
}


class AdminPanel extends HorizontalPanel {

    private final ViewController viewController;
    private final ArtistListWidget artistList;
    private final ArtistRequestListWidget requestList;

    public AdminPanel(ViewController viewController) {
        this.viewController = viewController;
        artistList = new ArtistListWidget(viewController);
        requestList = new ArtistRequestListWidget(viewController);
        add(artistList);
        add(requestList);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.isAdmin());

        artistList.setVisible(visible && viewController.isAdmin());
        requestList.setVisible(visible && viewController.isAdmin());

    }
}
