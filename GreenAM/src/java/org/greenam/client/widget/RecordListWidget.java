/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import java.util.*;
import org.greenam.client.domain.LinkObject;
import org.greenam.client.domain.Record;
import org.greenam.client.rpc.*;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class RecordListWidget extends ListWidget<Record> {

    private Audio audio;

    public RecordListWidget(ViewController viewController) {
        super(viewController);

        audio = Audio.createIfSupported();

        grid.resize(1, 6);
        grid.setText(0, 1, "Title");
        grid.setText(0, 2, "Album");
        grid.setText(0, 3, "Artist");
        grid.setText(0, 4, "Genre");
        grid.setText(0, 5, "Buy");



    }

    @Override
    protected void update(List<Record> records) {
        int row = records.size() + 1;
        grid.resize(row, 6);

        int i = 1;
        ArtistFetchObject artistFetch = new ArtistFetchObject(viewController);
        AlbumFetchObject albumFetch = new AlbumFetchObject(viewController);

        for (final Record record : records) {
            Image playImg = new Image("img/play.png");
            playImg.setSize("20px", "20px");
            Image buyImg = new Image("img/buy.png");
            buyImg.setSize("20px", "20px");
            Label title = new Label(record.getTitle());

            HorizontalPanel artistsPanel = new HorizontalPanel();
            for (final Long id : record.getArtistIds()) {
                artistFetch.addPanel(id, artistsPanel);
            }

            HorizontalPanel albumPanel = new HorizontalPanel();
            albumFetch.addPanel(record.getId(), albumPanel);


            /*
             * HorizontalPanel albumPanel = new HorizontalPanel(); for (final
             * Long id : record.get()) { final Label album = new
             * Label("Fetching...");
             * album.setStyleName("gam-RecordListWidgetLink");
             * album.addClickHandler(new ClickHandler() {
             *
             * @Override public void onClick(ClickEvent event) {
             * viewController.setArtistView(id);
             *
             * }
             * }); afo.addLabel(id, album); }
             */
//            Label genre = new Label(genreToString(record.getGenre()));

            playImg.setStyleName("gam-RecordListWidgetLink");
            buyImg.setStyleName("gam-RecordListWidgetLink");
            title.setStyleName("gam-RecordListWidgetLink");
            // genre.setStyleName("gam-RecordListWidgetLink");

            //Click handlers
            playImg.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    Window.alert("Playing: " + record.getAudioUrl());
                    audio.setSrc(record.getAudioUrl());
                    audio.play();
                }
            });
            title.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    //viewController.setSearchTitleView(record.titleId);
                }
            });

            /*
             * genre.addClickHandler(new ClickHandler() {
             *
             * @Override public void onClick(ClickEvent event) {
             * //viewController.setSearchGenreView(record.getGenre()); } });
             */

            grid.setWidget(i, 0, playImg);
            grid.setWidget(i, 1, title);
            grid.setWidget(i, 2, albumPanel);
            grid.setWidget(i, 3, artistsPanel);
            //grid.setWidget(i, 4, genre);
            grid.setWidget(i, 5, buyImg);
            i++;
        }

        setSize("600px", "600px");

        artistFetch.fetch();
        albumFetch.fetch();
    }
}

abstract class FetchObject {

    protected final ViewController viewController;
    HashMap<Long, List<HorizontalPanel>> map = new HashMap<Long, List<HorizontalPanel>>();
    protected ViewClickHandler clickHandler;

    public FetchObject(final ViewController viewController) {
        this.viewController = viewController;
    }

    public void addPanel(Long id, HorizontalPanel panel) {
        List<HorizontalPanel> list = map.get(id);
        if (list == null) {
            list = new LinkedList<HorizontalPanel>();
        }
        list.add(panel);
        map.put(id, list);
    }

    public abstract void fetch();
    protected AsyncCallback<List<LinkObject<String>>> callback =
            new AsyncCallback<List<LinkObject<String>>>() {

                @Override
                public void onFailure(Throwable caught) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void onSuccess(List<LinkObject<String>> result) {
                    for (LinkObject<String> lo : result) {
                        List<HorizontalPanel> list = map.get(lo.getLinkId());

                        if (list != null) {
                            for (HorizontalPanel panel : list) {
                                Label label = new Label(lo.getObject());
                                panel.add(label);
                                label.addClickHandler(clickHandler.create(lo.getObjectId()));
                                label.setStyleName("gam-RecordListWidgetLink");
                            }
                        }
                    }
                }
            };

    /**
     * A helper class for wrapping a Click handler to different subclasses. *
     */
    protected abstract class ViewClickHandler {

        public ClickHandler create(final Long id) {
            return new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    clickAction(id, event);
                }
            };
        }

        public abstract void clickAction(Long id, ClickEvent event);
    }
}

class ArtistFetchObject extends FetchObject {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);

    public ArtistFetchObject(final ViewController viewController) {
        super(viewController);
        clickHandler = new ViewClickHandler() {

            @Override
            public void clickAction(Long id, ClickEvent event) {
                viewController.setArtistView(id);
            }
        };
    }

    @Override
    public void fetch() {
        List<Long> keys = new LinkedList<Long>(map.keySet());
        userInfo.getArtistNames(keys, callback);
    }
}

class AlbumFetchObject extends FetchObject {

    private final RecordServiceAsync recordInfo = GWT.create(RecordService.class);

    public AlbumFetchObject(final ViewController viewController) {
        super(viewController);
        clickHandler = new ViewClickHandler() {

            @Override
            public void clickAction(Long id, ClickEvent event) {
                viewController.setAlbumView(id);
            }
        };
    }

    @Override
    public void fetch() {
        List<Long> keys = new LinkedList<Long>(map.keySet());
        recordInfo.getAlbumNamesFromRecords(keys, callback);
    }
}