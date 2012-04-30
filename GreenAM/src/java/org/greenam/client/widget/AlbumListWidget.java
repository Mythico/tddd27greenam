/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import java.util.List;
import org.greenam.client.domain.Album;
import org.greenam.client.domain.Artist;
import org.greenam.client.rpc.AdminService;
import org.greenam.client.rpc.AdminServiceAsync;
import org.greenam.client.rpc.RecordService;
import org.greenam.client.rpc.RecordServiceAsync;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class AlbumListWidget extends BaseWidget {

    private final RecordServiceAsync recordInfo = GWT.create(RecordService.class);

    public AlbumListWidget(ViewController viewController) {
        super(viewController);
    }

    private void load() {
        Artist artist = viewController.getArtist();
        recordInfo.getAlbums(artist, new AsyncCallback<List<Album>>() {

            @Override
            public void onFailure(Throwable caught) {
                setError("Unable to fetch albums.", caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<Album> result) {
                for (Album album : result) {
                    add(new AlbumPanel(album, viewController));
                }
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible) {
            load();
        }
    }
}

class AlbumPanel extends BasePanel {

    private final RecordServiceAsync recordInfo = GWT.create(RecordService.class);
    private final AdminServiceAsync adminAsync = GWT.create(AdminService.class);
    private DisclosurePanel panel;
    private Album album;

    public AlbumPanel(Album album, ViewController viewController) {
        setStyleName("gam-Box");
        this.album = album;
        panel = new DisclosurePanel(album.getTitle());
        panel.setWidth("560px");

        RecordListWidget rlw = new RecordListWidget(viewController);
        recordInfo.getRecords(album.getRecordIds(), rlw.callback);
        panel.add(rlw);

        add(panel);
        if (viewController.hasAccess()) {
            addDeleteHandler(deleteAlbum);
        }
    }
    private final ClickHandler deleteAlbum = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            adminAsync.deleteAlbum(album, removeThis);
        }
    };
}