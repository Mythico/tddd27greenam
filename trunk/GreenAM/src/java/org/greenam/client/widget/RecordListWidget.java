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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import java.util.*;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Record;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class RecordListWidget extends ListWidget<Record> {

    private Audio audio;
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final HashMap<Long, Artist> artists = new HashMap<Long, Artist>();

    public RecordListWidget(ViewController viewController) {
        super(viewController);

        audio = Audio.createIfSupported();
        audio.addSource("sound/Rondo_Alla_Turka.ogg");

        resize(1, 6);
        setText(0, 1, "Title");
        setText(0, 2, "Album");
        setText(0, 3, "Artist");
        setText(0, 4, "Genre");
        setText(0, 5, "Buy");

    }

    private String genreToString(int genre) { //TODO: temp
        switch (genre) {
            case 0:
                return "Dance";
        }
        return null;
    }

    @Override
    protected void update(List<Record> records) {
        int row = records.size() + 1;
        resize(row, 6);

        int i = 1;
        ArtistFetchObject afo = new ArtistFetchObject();

        for (final Record record : records) {
            Image playImg = new Image("img/play.png");
            playImg.setSize("20px", "20px");
            Image buyImg = new Image("img/buy.png");
            buyImg.setSize("20px", "20px");
            Label title = new Label(record.getTitle());
            Label album = new Label("Fetching...");
            Label artist = new Label("Fetching...");

            for (Long id : record.getArtistIds()) {
                afo.addLabel(id, artist);
            }
//            Label genre = new Label(genreToString(record.getGenre()));

            playImg.setStyleName("gam-RecordListWidgetLink");
            buyImg.setStyleName("gam-RecordListWidgetLink");
            title.setStyleName("gam-RecordListWidgetLink");
            album.setStyleName("gam-RecordListWidgetLink");
            artist.setStyleName("gam-RecordListWidgetLink");
            // genre.setStyleName("gam-RecordListWidgetLink");

            //Click handlers
            playImg.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    audio.play();
                }
            });
            title.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    //viewController.setSearchTitleView(record.titleId);
                }
            });

            artist.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (record.getArtistIds().size() == 1) {
                        Long id = record.getArtistIds().get(0);
                        Artist artist = artists.get(id);
                        viewController.setArtistView(artist);
                    } else {
                        //TODO: add a popup with choises for selecting an artist.
                    }

                }
            });
            album.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    //viewController.setSearchAlbumView(record.albumId);
                }
            });
            /*
             * genre.addClickHandler(new ClickHandler() {
             *
             * @Override public void onClick(ClickEvent event) {
             * //viewController.setSearchGenreView(record.getGenre()); } });
             */

            setWidget(i, 0, playImg);
            setWidget(i, 1, title);
            setWidget(i, 2, album);
            setWidget(i, 3, artist);
            //setWidget(i, 4, genre);
            setWidget(i, 5, buyImg);
            i++;
        }

        afo.clear();
        afo.fetch();

    }
}

abstract class FetchObject {

    HashMap<Long, List<Label>> map = new HashMap<Long, List<Label>>();

    public void addLabel(Long id, Label label) {
        List<Label> list = map.get(id);
        if(list == null){
            list = new LinkedList<Label>();
        }
        list.add(label);
        map.put(id, list);
    }

    public abstract void fetch();

    public void clear() {
        for (List<Label> l : map.values()) {
            for (Label la : l) {
                la.setText("");
            }
        }
    }
}

class ArtistFetchObject extends FetchObject {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);

    @Override
    public void fetch() {
        HashSet<Long> set = new HashSet<Long>(map.keySet());
        userInfo.getArtists(set, new AsyncCallback<HashMap<Long, Artist>>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch artists.\n" + caught);
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(HashMap<Long, Artist> result) {
                for (Long id : result.keySet()) {
                    List<Label> list = map.get(id);

                    for (Label label : list) {
                        label.setText(label.getText() + result.get(id).getName() + ", ");
                    }
                }
            }
        });
    }
}