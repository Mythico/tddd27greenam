package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.ArrayList;
import java.util.Date;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Blog;
import org.greenam.client.domain.Comment;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Michael
 */
public class BlogWidget extends VerticalPanel {

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
        
        final HorizontalPanel mainEntryPanel = new HorizontalPanel();
        final VerticalPanel entry = new VerticalPanel();
        VerticalPanel commentsPanel = new VerticalPanel();
        final VerticalPanel commentvp = new VerticalPanel();
        final DisclosurePanel panel;
        final Button addCommentButton = new Button("Add comment");
        final RichTextArea addComment = new RichTextArea();
                
        addComment.setStyleName("gam-Textbox");
        addComment.setText("Enter comment here!");
        
        addCommentButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!addComment.getText().equals("Enter comment here!")) {
                    saveComment(addComment.getText(), blog);
                }
            }
        });
        
        mainEntryPanel.setStyleName("gam-Box");
        
        DateTimeFormat dtf = DateTimeFormat.getFormat("EEEE, d MMMM");
        Label dateLabel = new Label("This is entry " + (i + 1) +
                " and was posted on " + dtf.format(date));
        entry.add(dateLabel);
        entry.add(new Label(blog.getEntry()));
        
        
        mainEntryPanel.add(entry);
        
        panel = new DisclosurePanel(entry);
        panel.setWidth("500px");
        
        //Get and post the comments for this blog entry
        commentsPanel = getComments(blog);
        
        //Add the comment panel, add comment button and the text box where you write your comment
        commentvp.add(commentsPanel);
        commentvp.add(addComment);
        commentvp.add(addCommentButton);
        
        //Add all comment stuff to the disclosure panel
        panel.setContent(commentvp);
        
        //Add disclosure panel to the entry
        mainEntryPanel.add(panel);
        
        //Add button so you can delete your blog entries
        if(viewController.hasAccess()){
            Image remove = new Image("img/cross_script.png");
            remove.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    blogArea.remove(mainEntryPanel);
                    deleteBlog(blog);
                }
            });
            mainEntryPanel.add(remove);
        }
                
        blogArea.add(mainEntryPanel);
        scrollArea.setHeight("400px");
    }

    private VerticalPanel getComments(Blog blog) {
         final VerticalPanel panel = new VerticalPanel();
         panel.setStyleName("gam-Box");
         
         artistInfo.getComment(blog, new AsyncCallback<ArrayList<Comment>>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Get comment failed");
            }

            @Override
            public void onSuccess(ArrayList<Comment> comment) {
                //Clear and print the blog
                for (int i = 0; i < comment.size(); i++) {
                    Date date = comment.get(i).getDate();
                    DateTimeFormat dtf = DateTimeFormat.getFormat("EEEE, d MMMM");
                    Label dateLabel = new Label("This is comment " + (i + 1) +
                            " and was posted on " + dtf.format(date));
                    panel.add(dateLabel);
                    panel.add(new Label(comment.get(i).getEntry()));  
                }
            }
        });
         return panel;
    }
        
    private void saveComment(String commentToAdd, Blog blog) {
        
        Comment comment = new Comment(commentToAdd, blog);
        artistInfo.postComment(comment, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BlogWidget failed RPC on saving the comments.\n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                load();
            }
        });
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
