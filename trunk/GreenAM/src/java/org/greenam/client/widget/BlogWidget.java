package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.ArrayList;
import java.util.Date;
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
    private final Button clearblogButton = new Button("Clear your blog");
    private final Button deleteentryButton = new Button("Delete entry");
    private final RichTextArea newentryArea = new RichTextArea();
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
    private final HTML blogArea = new HTML("This blog is currently empty.", true);
    private final ListBox lb = new ListBox();
    private ArrayList<String> blogPosts;
    private Date date = new Date();
    private final DialogBox dialogbox = new DialogBox(false);;
    private Artist artist;
    
    public BlogWidget() {
        
        newentryArea.setEnabled(false);
        newentryArea.setText("Add your new blog entry here!");
        newentryArea.setVisible(false);
        
        lb.setVisible(false);
        lb.setVisibleItemCount(1);
        lb.clear();
        lb.addItem("Entry to delete: ");
                
        add(blogArea);    
        add(newentryArea);
        add(buttonPanel);
        
        buttonPanel.add(newentryButton);
        buttonPanel.add(lb);
        buttonPanel.add(deleteentryButton);
        buttonPanel.add(clearblogButton);
        
        newentryButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if(newentryArea.getText() != "Add your new blog entry here!")
                {
                    save();
                }
            }
        });
        
        clearblogButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                clearblog();
            }
        });
        
        deleteentryButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                int entrytodelete = lb.getSelectedIndex();
                if(entrytodelete != 0)
                {
                    deleteentry(entrytodelete);
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
                newentryArea.setEnabled(result);  
                newentryArea.setVisible(result);
                newentryButton.setVisible(result);
                clearblogButton.setVisible(result);
                deleteentryButton.setVisible(result);
                lb.setVisible(result);
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
                load();
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
                
                //Clear and print the blog
                blogArea.setHTML("");
                for(int i=0;i<blogPosts.size();i++)
                    {
                        blogArea.setHTML(blogArea.getHTML() + "This is entry " + (i+1) + " and was posted "+ date + "<br>" + blogPosts.get(i) + "<br>" + "<br>");
                        newentryArea.setText("Add your new blog entry here!");
                    }
                
                lb.clear();
                lb.addItem("Entry to delete:");
                for(int i=0;i<blogPosts.size();i++)
                {
                    lb.addItem("" + (i+1));
                }
            }
        });
    }
    
    public void setArtist(Artist artist) {
        this.artist = artist;
        load();
    }
    
    private void clearblog() {
                
        blogPosts.clear();
        lb.clear();
        lb.addItem("Entry to delete:");
        artist.setBlogPosts(blogPosts);

        artistInfo.save(artist, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BlogWidget failed RPC on save.\n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                blogArea.setHTML("This blog is currently empty.");
            }
        });
    }
    
    private void deleteentry(int entry) {
        
        blogPosts.remove(entry-1);
        artist.setBlogPosts(blogPosts);
        
        artistInfo.save(artist, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BlogWidget failed RPC on save.\n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                load();
            }
        });
    }
    
}
