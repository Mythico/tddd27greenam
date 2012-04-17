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
import org.greenam.client.domain.Blog;
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
    private final VerticalPanel blogArea = new VerticalPanel();
    private final ScrollPanel scrollArea = new ScrollPanel(blogArea);
    private final RichTextArea newentryArea = new RichTextArea();
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
    private final ListBox lb = new ListBox();
    private final Blog newblogentry = new Blog();
    private ArrayList<Blog> blog;
    private Date date = new Date();
    private Artist artist;

    public BlogWidget() {

        newentryArea.setEnabled(false);
        newentryArea.setText("Add your new blog entry here!");
        newentryArea.setVisible(false);

        lb.setVisible(false);
        lb.setVisibleItemCount(1);
        lb.clear();
        lb.addItem("Entry to delete: ");

        add(scrollArea);
        add(newentryArea);
        add(buttonPanel);

        buttonPanel.add(newentryButton);
        buttonPanel.add(lb);
        buttonPanel.add(deleteentryButton);
        buttonPanel.add(clearblogButton);

        newentryButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!newentryArea.getText().equals("Add your new blog entry here!")) {
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
                if (entrytodelete != 0) {
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

        newblogentry.addEntry(newentryArea.getText(), date);
        newblogentry.artistId = artist.getId();
        artistInfo.postBlog(newblogentry, new AsyncCallback<Void>() {

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
        blogArea.clear();
        artistInfo.getBlog(artist, new AsyncCallback<ArrayList<Blog>>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(ArrayList<Blog> blog) {
                //Clear and print the blog
                //blogArea.setHTML("");
                for (int i = 0; i < blog.size(); i++) {
                    //blogArea.setHTML(blogArea.getHTML() + "This is entry " + (i+1) + " and was posted " + blog.get(i).getDate() + "<br>" + blog.get(i).getEntry() + "<br>" + "<br>");
                    addBlog(blog.get(i), i);
                }
                newentryArea.setText("Add your new blog entry here!");

                lb.clear();
                lb.addItem("Entry to delete:");
                for (int i = 0; i < blog.size(); i++) {
                    lb.addItem("" + (i + 1));
                }
            }
        });
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        load();
    }

    private void addBlog(Blog blog, int i) {
        VerticalPanel vp = new VerticalPanel();
        vp.setStyleName("gam-Box");
        vp.add(new Label("This is entry " + (i + 1) + " and was posted " + blog.getDate()));
        vp.add(new Label(blog.getEntry()));
        blogArea.add(vp);
        scrollArea.setHeight("400px");
    }

    private void clearblog() {

        blog.clear();
        lb.clear();
        lb.addItem("Entry to delete:");
        artist.setBlog(blog);

        artistInfo.save(artist, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BlogWidget failed RPC on save.\n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                //blogArea.setHTML("This blog is currently empty.");
            }
        });
    }

    private void deleteentry(int entry) {

        blog.remove(entry - 1);
        artist.setBlog(blog);

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
