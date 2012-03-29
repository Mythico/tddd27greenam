/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.domain.Artist;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.widget.AlbumListWidget;
import org.greenam.client.widget.BiographyWidget;

/**
 *
 * @author Emil
 */
public class ArtistView extends VerticalPanel {

    private Artist artist;
    //Constant used by the deckpanel
    private final int ALBUM_LIST = 0;
    private final int BIOGRAPHY = 1;
    private final int BLOG = 2;
    private final int EVENT_CALENDER = 3;
    private final int UPLOAD = 4;

    private static class BlogPane extends VerticalPanel {

        public BlogPane() {
            add(new Label("I am a blog."));
        }
    }

    private static class EventCalanderPane extends VerticalPanel {

        public EventCalanderPane() {
            add(new Label("I am a event calander."));
        }
    }

    private static class UploadPane extends VerticalPanel {

        public UploadPane() {
            add(new FileUpload());
        }
    }
    private final DeckPanel deckPanel = new DeckPanel();
    private final ScrollPanel scrollPanel = new ScrollPanel(deckPanel);
    //Commands used when clicking the menu.
    private final Command showMusic = new Command() {

        @Override
        public void execute() {
            deckPanel.showWidget(ALBUM_LIST);

            //TODO: add search for albums


        }
    };
    private final Command showBio = new Command() {

        @Override
        public void execute() {
            bioPane.setArtist(artist);
            deckPanel.showWidget(BIOGRAPHY);
        }
    };
    private final Command showBlog = new Command() {

        @Override
        public void execute() {
            deckPanel.showWidget(BLOG);
        }
    };
    private final Command showEvent = new Command() {

        @Override
        public void execute() {
            deckPanel.showWidget(EVENT_CALENDER);
        }
    };
    private final Command uploadEvent = new Command() {

        @Override
        public void execute() {
            deckPanel.showWidget(UPLOAD);
        }
    };
    private final Label artistLabel = new Label("Unknown");
    private final MenuBar menuBar = new MenuBar();
    private final MenuItem musicItem = new MenuItem("Music", showMusic);
    private final MenuItem bioItem = new MenuItem("Biography", showBio);
    private final MenuItem blogItem = new MenuItem("Blog", showBlog);
    private final MenuItem eventItem = new MenuItem("Event Calender", showEvent);
    private final MenuItem uploadItem = new MenuItem("Upload", uploadEvent);
    private final AlbumListWidget albumListWidget;
    private final BiographyWidget bioPane = new BiographyWidget();
    private final BlogPane blogPane = new BlogPane();
    private final EventCalanderPane eventPane = new EventCalanderPane();
    private final UploadPane uploadPane = new UploadPane();

    public ArtistView(ViewController viewController) {
        setStyleName("gam-ContentView");

        albumListWidget = new AlbumListWidget(viewController);

        menuBar.setStyleName("demo-MenuBar");
        menuBar.addItem(musicItem);
        menuBar.addItem(bioItem);
        menuBar.addItem(blogItem);
        menuBar.addItem(eventItem);
        menuBar.addItem(uploadItem);

        deckPanel.insert(albumListWidget, ALBUM_LIST);
        deckPanel.insert(bioPane, BIOGRAPHY);
        deckPanel.insert(blogPane, BLOG);
        deckPanel.insert(eventPane, EVENT_CALENDER);
        deckPanel.insert(uploadPane, UPLOAD);

        add(artistLabel);
        add(menuBar);
        add(scrollPanel);
    }

    void setArtist(Artist artist) {
        this.artist = artist;
        artistLabel.setText("[" + artist.getId() + "]Artist: " + artist.getName());
        showBio.execute(); //TODO: set showMusic as default
    }
}
