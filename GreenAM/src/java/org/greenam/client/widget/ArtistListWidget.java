/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import org.greenam.client.domain.Artist;
import org.greenam.client.rpc.AdminService;
import org.greenam.client.rpc.AdminServiceAsync;
import org.greenam.client.view.ViewController;

/**
 * ArtistListWidget is a widget that will list all the artist on the service.
 * It can also be used to remove a specific artist.
 * @author Emil
 * @author Michael
 */
public class ArtistListWidget extends VerticalPanel {

    private final AdminServiceAsync adminInfo = GWT.create(AdminService.class);
    private final ViewController viewController;
    private final VerticalPanel artistPanel = new VerticalPanel();

    public ArtistListWidget(ViewController viewController) {
        this.viewController = viewController;
        add(new Label("Artists"));
        add(artistPanel);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.isAdmin());

        if (visible) {
            artistPanel.clear();
            adminInfo.listArtists(listArtists);
        }
    }
    AsyncCallback<List<Artist>> listArtists = new AsyncCallback<List<Artist>>() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(List<Artist> result) {
            for (final Artist artist : result) {
                final ArtistPanel panel = new ArtistPanel(artist);
                artistPanel.add(panel);
            }
        }
    };
}

/**
 * ArtistPanel is a helperclass for ArtistListWidget that have a label for
 * describing an artist and a button used for removing an artist.
 * @author Emil
 * @author Michael
 */
class ArtistPanel extends HorizontalPanel {

    private final AdminServiceAsync adminInfo = GWT.create(AdminService.class);
    private final Artist artist;

    public ArtistPanel(Artist artist) {
        this.artist = artist;
        setStyleName("gam-Box");

        Label l = new Label("[" + artist.getId() + "] " + artist.getName());
        Button remove = new Button("X");
        remove.addClickHandler(removeArtist);
        add(remove);
        add(l);
    }
    /**
     * A callback used by the click handler for removing this panel when an
     * artist has been removed.
     */
    private final AsyncCallback removeThis = new AsyncCallback() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(Object result) {
            removeFromParent();
        }
    };
    
    /**
     * A click handler used for deleting an artist.
     */
    private final ClickHandler removeArtist = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            adminInfo.deleteArtist(artist, removeThis);
        }
    };

    
}