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
import java.util.List;
import org.greenam.client.domain.Album;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Record;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;

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
                        viewController.getUser().addMoney(100);
                    }
                });
            }
        });
        add(addMoneyButton);


        //TODO: Temp until a real admin gui is implemented.
        Button b = new Button("TEMP: Make me an artist, don't click me if im an artist.");
        b.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                User user = viewController.getUser();
                userInfo.makeArtist(user.getId(), user.getName(), new AsyncCallback<Long>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    public void onSuccess(Long result) {
                        viewController.setArtistView(result);
                    }
                });
            }
        });
        add(b);
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

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final ViewController viewController;
    private final VerticalPanel artistPanel = new VerticalPanel();

    public AdminPanel(ViewController viewController) {
        this.viewController = viewController;
        add(artistPanel);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.isAdmin());

        if (visible && viewController.isAdmin()) {
            artistPanel.clear();
            artistPanel.add(new Label("Artists"));
            userInfo.getAllArtists(getArtistList);
        }
    }
    AsyncCallback<List<Artist>> getArtistList = new AsyncCallback<List<Artist>>() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(List<Artist> result) {
            for (final Artist artist : result) {
                final HorizontalPanel hp = new HorizontalPanel();
                Label l = new Label("[" + artist.getId() + "] " + artist.getName());
                Button remove = new Button("X");
                remove.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        userInfo.deleteArtist(artist, new AsyncCallback() {

                            @Override
                            public void onFailure(Throwable caught) {
                                throw new UnsupportedOperationException("Not supported yet.");
                            }

                            @Override
                            public void onSuccess(Object result) {
                                artistPanel.remove(hp);
                            }
                        });
                    }
                });
                hp.add(remove);
                hp.add(l);
                artistPanel.add(hp);
            }
        }
    };
    
    AsyncCallback<List<Record>> getRecordList;
    AsyncCallback<List<Album>> getAlbumList;
}
