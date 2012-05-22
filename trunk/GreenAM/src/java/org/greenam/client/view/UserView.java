/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;
import org.greenam.client.widget.ArtistListWidget;
import org.greenam.client.widget.ArtistRequestListWidget;
import org.greenam.client.widget.BasePanel;
import org.greenam.client.widget.BaseWidget;
import org.greenam.client.widget.RequestArtistWidget;

/**
 * Userview is the view that every user can see. It shows their money, and 
 * gives the option to add more money or request to become an artist.
 * 
 * @author Emil
 * @author Michael
 */
public class UserView extends BaseWidget {

    private final DeckPanel panel = new DeckPanel();
    private final int USER = 0;
    private final int ADMIN = 1;
    private final MenuItem userPage;
    private final MenuItem adminPage;
    private final MenuItem artistPage;

    public UserView(final ViewController viewController) {
        super(viewController);

        final AdminPanel adminPanel = new AdminPanel(viewController);
        final UserPanel userPanel = new UserPanel(viewController);



        final MenuBar menuBar = new MenuBar();
        userPage = new MenuItem("User Page", showUser);
        adminPage = new MenuItem("Admin Page", showAdmin);
        artistPage = new MenuItem("Go to Artist Page", goArtist);
        menuBar.addItem(userPage);
        menuBar.addItem(adminPage);
        menuBar.addItem(artistPage);

        panel.insert(userPanel, USER);
        panel.insert(adminPanel, ADMIN);

        add(menuBar);
        add(panel);
    }
    Command showUser = new Command() {

        @Override
        public void execute() {
            panel.showWidget(USER);
        }
    };
    Command showAdmin = new Command() {

        @Override
        public void execute() {
            panel.showWidget(ADMIN);
        }
    };
    Command goArtist = new Command() {

        @Override
        public void execute() {
            Long id = viewController.getArtist().getId();
            viewController.setArtistView(id);
        }
    };

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        userPage.setVisible(visible);
        adminPage.setVisible(visible && viewController.isAdmin());
        artistPage.setVisible(visible && viewController.isArtist());
        panel.showWidget(USER);

    }
}

class UserPanel extends BasePanel {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final Label userLabel = new Label("Unknown");
    private final Label moneyLabel = new Label("Unknown");
    private final Button addMoneyButton = new Button("Add 100€");
    private final RequestArtistWidget requestWidget;
    private final ViewController viewController;

    public UserPanel(final ViewController viewController) {
        this.viewController = viewController;


        requestWidget = new RequestArtistWidget(viewController);
        add(userLabel);
        add(moneyLabel);
        add(addMoneyButton);
        add(requestWidget);

        //Note: temporary untill a currecy system is implemented.
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
        
        if (viewController.isLogin()) {
            User user = viewController.getUser();
            moneyLabel.setText(user.getMoney() + "€");
            userLabel.setText(user.getName());
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible && viewController.isLogin()) {
            User user = viewController.getUser();
            moneyLabel.setText(user.getMoney() + "€");
            userLabel.setText(user.getName());
        }

    }
}

/**
 * Admin panel is a special panel of the user panel that only administrators
 * can see. It contains administration tools for viewing and removing artists
 * and accepting or denying user request.
 * 
 * @author Emil
 * @author Michael
 */
class AdminPanel extends BasePanel {

    private final ViewController viewController;
    private final ArtistListWidget artistList;
    private final ArtistRequestListWidget requestList;
    private final VerticalPanel panel = new VerticalPanel();

    public AdminPanel(ViewController viewController) {
        this.viewController = viewController;
        artistList = new ArtistListWidget(viewController);
        requestList = new ArtistRequestListWidget(viewController);
        panel.add(artistList);
        panel.add(requestList);
        add(panel);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.isAdmin());

        artistList.setVisible(visible && viewController.isAdmin());
        requestList.setVisible(visible && viewController.isAdmin());

    }
}
