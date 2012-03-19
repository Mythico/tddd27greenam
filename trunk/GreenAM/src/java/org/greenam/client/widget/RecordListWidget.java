/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import java.util.LinkedList;
import org.greenam.client.rpc.Record;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class RecordListWidget extends Grid {

    private final ViewController viewController;

    public RecordListWidget(ViewController viewController) {
        this.viewController = viewController;

        setStyleName("gam-RecordListWidget");


        resize(1, 6);
        setText(0, 1, "Title");
        setText(0, 2, "Album");
        setText(0, 3, "Artist");
        setText(0, 4, "Genre");
        setText(0, 5, "Buy");
        
    }

    public void setRecords(LinkedList<Record> records) {
        int row = records.size() + 1;
        resize(row, 6);

        int i = 1;
        for (final Record record : records) {
            Image playImg = new Image("img/play.png");
            Image buyImg = new Image("img/buy.png");
            Label title = new Label(record.getTitle());
            Label album = new Label(record.getAlbum());
            Label artist = new Label(record.getArtist());
            Label genre = new Label(genreToString(record.getGenre()));

            playImg.setStyleName("gam-RecordListWidgetLink");
            buyImg.setStyleName("gam-RecordListWidgetLink");
            title.setStyleName("gam-RecordListWidgetLink");
            album.setStyleName("gam-RecordListWidgetLink");
            artist.setStyleName("gam-RecordListWidgetLink");
            genre.setStyleName("gam-RecordListWidgetLink");

            //Click handlers
            title.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event) {
                    viewController.setSearchTitleView(record.getTitleId());
                }
            });

            artist.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event) {
                    viewController.setArtistView(record.getArtistId(),
                            record.getArtist());
                }
            });
            album.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event) {
                    viewController.setSearchAlbumView(record.getAlbumId());
                }
            });
            genre.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event) {
                    viewController.setSearchGenreView(record.getGenre());
                }
            });

            setWidget(i, 0, playImg);
            setWidget(i, 1, title);
            setWidget(i, 2, album);
            setWidget(i, 3, artist);
            setWidget(i, 4, genre);
            setWidget(i, 5, buyImg);
            i++;
        }

    }
    public final AsyncCallback<LinkedList<Record>> callback =
            new AsyncCallback<LinkedList<Record>>() {

                public void onSuccess(LinkedList<Record> result) {
                    setRecords(result);
                }

                public void onFailure(Throwable caught) {
                }
            };
    
    
    
    //TODO: Temp create a real system. HIHIHO
    private String genreToString(int genre){
        switch(genre){
            case 0:
                return "Dance";
        }
        return null;
    }
}
