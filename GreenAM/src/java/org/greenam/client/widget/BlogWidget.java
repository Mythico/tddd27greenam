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
    private final VerticalPanel blogArea = new VerticalPanel();
    private final ScrollPanel scrollArea = new ScrollPanel(blogArea);
    private final RichTextArea newentryArea = new RichTextArea();
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
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
                clearBlog();
            }
        });
    }

    private void save() {
        if (!viewController.hasAccess()) {
            Window.alert("You are trying to add blog entries without"
                    + " having the correct access.");
            return;
        }

        Artist artist = viewController.getArtist();
        Blog blog = new Blog(newentryArea.getText(), artist);
        artistInfo.postBlog(blog, new AsyncCallback<Void>() {

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
        Artist artist = viewController.getArtist();
        artistInfo.getBlog(artist, new AsyncCallback<ArrayList<Blog>>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(ArrayList<Blog> blog) {
                //Clear and print the blog
                for (int i = 0; i < blog.size(); i++) {
                    addBlog(blog.get(i), i);
                }
                newentryArea.setText("Add your new blog entry here!");
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible) {
            load();
            boolean hasAccess = viewController.hasAccess();
            newentryArea.setEnabled(hasAccess);
            newentryArea.setVisible(hasAccess);
            newentryButton.setVisible(hasAccess);
            clearblogButton.setVisible(hasAccess);
        }
    }

    private void addBlog(final Blog blog, int i) {
        Date date = blog.getDate();
        final HorizontalPanel hp = new HorizontalPanel();
        VerticalPanel vp = new VerticalPanel();
        vp.setStyleName("gam-Box");
        vp.add(new Label("This is entry " + (i + 1) + " and was posted on " + getDay(date.getDay()) + ", " + date.getDate() + " " + getMonth(date.getMonth())));
        vp.add(new Label(blog.getEntry()));
        hp.add(vp);
        
        if(viewController.hasAccess()){
            Button remove = new Button("X");
            remove.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    blogArea.remove(hp);
                    deleteBlog(blog);
                }
            });
            hp.add(remove);
        }
        blogArea.add(hp);
        scrollArea.setHeight("400px");
    }

    private String getMonth(int month) {
        String monthString;
        switch (month) {
            case 0:
                monthString = "January";
                break;
            case 1:
                monthString = "February";
                break;
            case 2:
                monthString = "March";
                break;
            case 3:
                monthString = "April";
                break;
            case 4:
                monthString = "May";
                break;
            case 5:
                monthString = "June";
                break;
            case 6:
                monthString = "July";
                break;
            case 7:
                monthString = "August";
                break;
            case 8:
                monthString = "September";
                break;
            case 9:
                monthString = "October";
                break;
            case 10:
                monthString = "November";
                break;
            case 11:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    private String getDay(int day) {
        String dayString;
        switch (day) {
            case 0:
                dayString = "Sunday";
                break;
            case 1:
                dayString = "Monday";
                break;
            case 2:
                dayString = "Tuesday";
                break;
            case 3:
                dayString = "Wednesday";
                break;
            case 4:
                dayString = "Thursday";
                break;
            case 5:
                dayString = "Friday";
                break;
            case 6:
                dayString = "Saturday";
                break;
            default:
                dayString = "Invalid day";
                break;
        }
        return dayString;
    }

    private void clearBlog() {
        Artist artist = viewController.getArtist();
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
    private void deleteBlog(Blog blog) {
        artistInfo.deleteBlog(blog, new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Object result) {
            }
        });
    }
}
