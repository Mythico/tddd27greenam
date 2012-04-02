package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;

/**
 *
 * @author Michael
 */
public class BlogWidget extends VerticalPanel {
    
    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final Button newentryButton = new Button("Add a blog entry");
    private final RichTextArea newentryArea = new RichTextArea();
    private final Label blogArea = new Label();
    private ArrayList<String> blogPosts;
    private Artist artist;
    
    public BlogWidget() {
        
        newentryArea.setEnabled(false);
        newentryArea.setText("Add your new blog entry here!");
        newentryArea.setVisible(false);
                    
        add(blogArea);    
        add(newentryArea);
        add(newentryButton);
        
       // load(); TODO: FIXXA!
        
        newentryButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if(newentryArea.getText() != null)
                {
                    save();
                }
            }
        });
        
        userInfo.hasAccess(new AsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Has access failed in BlogWidget.\n" + caught);
            }

            @Override
            public void onSuccess(Boolean result) {
                newentryButton.setVisible(result);
                newentryArea.setEnabled(result);  
                newentryArea.setVisible(result);
            }
        });
    }

    private void save() {
        save(true);
    }

    private void save(boolean b) {
        if (!b) {
            return;
        }
                
        blogPosts.add(newentryArea.getText());
        artist.setBlogPosts(blogPosts);

        artistInfo.save(artist, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BlogWidget failed RPC on save.\n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                //Do nothing, it was saved successfully :)
            }
        });
    }
    
    private void load() {
        artistInfo.update(artist, new AsyncCallback<Artist>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BlogWidget failed RPC on load.\n" + caught);
            }

            @Override
            public void onSuccess(Artist result) {
                artist = result;
                blogPosts = artist.getBlogPosts();          
            }
        });     
    }
}
