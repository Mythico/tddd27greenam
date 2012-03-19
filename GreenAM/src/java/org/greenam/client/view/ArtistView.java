/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.rpc.MusicSearchService;
import org.greenam.client.rpc.MusicSearchServiceAsync;
import org.greenam.client.widget.RecordListWidget;

/**
 *
 * @author Emil
 */
public class ArtistView extends VerticalPanel{

    private final ViewController viewController;
    private final MusicSearchServiceAsync async = GWT.create(MusicSearchService.class);
    
    private int artistId = -1;
    
    private static class BiographyPane extends RichTextArea{

        public BiographyPane() {
            setHTML("This is a short bioagraphy of <b>ArtistName</b>");
        }
    }

    private static class BlogPane extends VerticalPanel{

        public BlogPane() {
            add(new Label("I am a blog."));
        }
    }

    private static class EventCalanderPane extends VerticalPanel{

        public EventCalanderPane() {
            add(new Label("I am a event calander."));
        }
    }
    
    //A scrollPanel that will hold the diffrent panes from above.
    private final ScrollPanel scrollPanel;
    
    //Commands used when clicking the menu.
    private final Command showMusic = new Command() {

        @Override
        public void execute() {
            scrollPanel.setWidget(recordListWidget);
            async.searchArtist(artistId, recordListWidget.callback);
        }
    };
    private final Command showBio = new Command() {

        @Override
        public void execute() {
            scrollPanel.setWidget(bioPane);
        }
    };
    private final Command showBlog = new Command() {

        @Override
        public void execute() {
            scrollPanel.setWidget(blogPane);
        }
    };
    private final Command showEvent = new Command() {

        @Override
        public void execute() {            
            scrollPanel.setWidget(eventPane);
        }
    };
    
    private final Label artistLabel = new Label("Unknown");
    private final MenuBar menuBar = new MenuBar();
    private final MenuItem musicItem = new MenuItem("Music", showMusic);
    private final MenuItem bioItem = new MenuItem("Biography", showBio);
    private final MenuItem blogItem = new MenuItem("Blog", showBlog);
    private final MenuItem eventItem = new MenuItem("Event Calender", showEvent);
    
    
    private final RecordListWidget recordListWidget;
    private final BiographyPane bioPane = new BiographyPane();
    private final BlogPane blogPane = new BlogPane();
    private final EventCalanderPane eventPane = new EventCalanderPane();
    
    
    public ArtistView(ViewController viewController) {
        setStyleName("gam-ContentView");
        
        this.viewController = viewController;
        recordListWidget = new RecordListWidget(viewController);
        scrollPanel = new ScrollPanel(recordListWidget);
        
        menuBar.setStyleName("demo-MenuBar");
        menuBar.addItem(musicItem);
        menuBar.addItem(bioItem);
        menuBar.addItem(blogItem);
        menuBar.addItem(eventItem);
        
        add(artistLabel);
        add(menuBar);
        add(scrollPanel);
    }
    
    
    void setArtist(int artistId, String artistName) {
        this.artistId = artistId;
        artistLabel.setText(artistName);
        showMusic.execute();
                
    }

    
}
