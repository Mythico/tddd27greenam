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
import org.greenam.client.view.ViewController;

/**
 *
 * @author Michael
 */
public class BlogWidget extends VerticalPanel {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final Button newentryButton = new Button("Add a blog entry");
    private final Button clearblogButton = new Button("Clear your blog");
    private final Button deleteentryButton = new Button("Delete Entry");
    private final VerticalPanel blogArea = new VerticalPanel();
    private final ScrollPanel scrollArea = new ScrollPanel(blogArea);
    private final RichTextArea newentryArea = new RichTextArea();
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
    private Blog blogtodelete = new Blog();
    private ArrayList<Blog> entireblog = new ArrayList();
    private final ListBox lb = new ListBox();
    private final Blog newblogentry = new Blog();
    private Date date = new Date();
    private Artist artist;
    private final ViewController viewController;

    public BlogWidget(final ViewController viewController) {

        this.viewController = viewController;
        
        newentryArea.setEnabled(false);
        newentryArea.setText("Add your new blog entry here!");
        newentryArea.setVisible(false);
        newentryArea.setStyleName("gam-Textbox");

        add(scrollArea);
        add(newentryArea);
        add(buttonPanel);

        buttonPanel.add(newentryButton);
        buttonPanel.add(clearblogButton);
        buttonPanel.add(lb);
        buttonPanel.add(deleteentryButton);

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
                deleteBlog();
            }
        });
        
        deleteentryButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                deleteEntry();
            }
        });

        if(viewController.hasAccess())
        {
            newentryArea.setEnabled(true);
            newentryArea.setVisible(true);
            newentryButton.setVisible(true);
            clearblogButton.setVisible(true);
            lb.setVisible(true);
            deleteentryButton.setVisible(true);
        }
        else
        {
            newentryArea.setEnabled(false);
            newentryArea.setVisible(false);
            newentryButton.setVisible(false);
            clearblogButton.setVisible(false);
            lb.setVisible(false);
            deleteentryButton.setVisible(false);
        }
    }

    private void save() {
        if (!viewController.hasAccess()) {
            Window.alert("You are trying to add blog entries without"
                    + " having the correct access.");
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
    
    //Gets the blog and shows it
    private void load() {
        blogArea.clear();
        artistInfo.getBlog(artist, new AsyncCallback<ArrayList<Blog>>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(ArrayList<Blog> blog) {
                entireblog = blog;
                //Clear and print the blog
                for (int i = 0; i < blog.size(); i++) {
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
        Date date = blog.getDate();
        VerticalPanel vp = new VerticalPanel();
        vp.setStyleName("gam-Box");
        vp.add(new Label("This is entry " + (i + 1) + " and was posted on " + getDay(date.getDay()) +  ", " + date.getDate() + " " + getMonth(date.getMonth())));
        vp.add(new Label(blog.getEntry()));
        blogArea.add(vp);
        scrollArea.setHeight("400px");
    }

    private String getMonth(int month)
    {
        String monthString;
        switch (month) {
            case 0:  monthString = "January";
                     break;
            case 1:  monthString = "February";
                     break;
            case 2:  monthString = "March";
                     break;
            case 3:  monthString = "April";
                     break;
            case 4:  monthString = "May";
                     break;
            case 5:  monthString = "June";
                     break;
            case 6:  monthString = "July";
                     break;
            case 7:  monthString = "August";
                     break;
            case 8:  monthString = "September";
                     break;
            case 9:  monthString = "October";
                     break;
            case 10: monthString = "November";
                     break;
            case 11: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
        return monthString;
    }
    
    private String getDay(int day)
    {
        String dayString;
        switch (day) {
            case 0:  dayString = "Sunday";
                     break;
            case 1:  dayString = "Monday";
                     break;
            case 2:  dayString = "Tuesday";
                     break;
            case 3:  dayString = "Wednesday";
                     break;
            case 4:  dayString = "Thursday";
                     break;
            case 5:  dayString = "Friday";
                     break;
            case 6:  dayString = "Saturday";
                     break;
            default: dayString = "Invalid day";
                     break;
        }
        return dayString;
    }
    
    private void deleteEntry() {
        
        blogtodelete = entireblog.get((lb.getSelectedIndex()-1));
        
        artistInfo.deleteBlog(blogtodelete, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Void result) {
                load();
            }
        });
    }

    private void deleteBlog() {

        artistInfo.deleteBlog(artist, new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Object result) {
                load();
            }
        });
    }
}
