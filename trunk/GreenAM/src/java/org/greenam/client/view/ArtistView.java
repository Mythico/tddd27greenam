/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.domain.Artist;
import org.greenam.client.widget.AlbumListWidget;
import org.greenam.client.widget.BiographyWidget;
import org.greenam.client.widget.BlogWidget;
import org.greenam.client.widget.CalendarWidget;
import org.greenam.client.widget.UploadWidget;

/**
 *
 * @author Emil
 */
public class ArtistView extends VerticalPanel {

    private final ViewController viewController;
    //Constant used by the deckpanel
    private final int ALBUM_LIST = 0;
    private final int BIOGRAPHY = 1;
    private final int BLOG = 2;
    private final int EVENT_CALENDER = 3;
    private final int UPLOAD = 4;
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
    private final BiographyWidget bioPane;
    private final BlogWidget blogPane;
    private final CalendarWidget eventPane;
    private final UploadWidget uploadPane;

    public ArtistView(ViewController viewController) {
        setStyleName("gam-ContentView");

        this.viewController = viewController;

        albumListWidget = new AlbumListWidget(viewController);
        bioPane = new BiographyWidget(viewController);
        eventPane = new CalendarWidget(viewController);
        uploadPane = new UploadWidget(viewController);
        blogPane = new BlogWidget(viewController);
                
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

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible) {
            uploadItem.setVisible(viewController.hasAccess());
            Artist artist = viewController.getArtist();
            artistLabel.setText("Artist: " + artist.getName());
            showMusic.execute();
        }
    }
}
