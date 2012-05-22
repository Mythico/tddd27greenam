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
 * Blogwidget shows and handles the blog.
 * 
 * @author Emil
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

        //Save the entry if the New Entry button is pressed
        newentryButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!newentryArea.getText().equals("Add your new blog entry here!")) {
                    save();
                }
            }
        });

        //Clear the blog if clear blog button is pressed.
        clearblogButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                clearEntireBlog();
            }
        });
    }

    /*
     * Saves the latest entry to the blog and updates the blog
     * 
     */
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
                update();
            }
        });
    }

    /*
     * Gets the blog and shows it
     * 
     */
    private void update() {
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
            update();
            boolean hasAccess = viewController.hasAccess();
            newentryArea.setEnabled(hasAccess);
            newentryArea.setVisible(hasAccess);
            newentryButton.setVisible(hasAccess);
            clearblogButton.setVisible(hasAccess);
        }
    }

    /*
     * Show the blog entry and all its comments.
     */
    private void addBlog(final Blog blog, int i) {
        Date date = blog.getDate();
        
        final HorizontalPanel mainEntryPanel = new HorizontalPanel();
        final VerticalPanel entry = new VerticalPanel();
        final VerticalPanel commentvp = new VerticalPanel();
        final DisclosurePanel panel;
        final Button addCommentButton = new Button("Add comment");
        final RichTextArea addComment = new RichTextArea();
        VerticalPanel commentsPanel = new VerticalPanel();
                
        mainEntryPanel.setStyleName("gam-Box");
        addComment.setStyleName("gam-Textbox");
        addComment.setText("Enter comment here!");
        
        addCommentButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!addComment.getText().equals("Enter comment here!")) {
                    saveComment(addComment.getText(), blog, viewController.getUser().getName());
                }
            }
        });
        
        //Print the entry
        DateTimeFormat dtf = DateTimeFormat.getFormat("EEEE, d MMMM");
        Label dateLabel = new Label("This is entry " + (i + 1) +
                " and was posted on " + dtf.format(date));
        entry.add(dateLabel);
        entry.add(new Label(blog.getEntry()));
                
        //Save the entry as the header of mainEntryPanel
        mainEntryPanel.add(entry);
        
        //Add a new disclosurepanel and add the entry
        panel = new DisclosurePanel();
        panel.setHeader(entry);
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
                    deleteBlogEntry(blog);
                }
            });
            mainEntryPanel.add(remove);
        }
                
        blogArea.add(mainEntryPanel);
        scrollArea.setHeight("400px");
    }

    /**
     * Get all the comments and post them on a VerticalPanel
     * @param blog
     * @return Verticalpanel with all the comments
     */
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
                            " and was posted on " + dtf.format(date) + " by " + comment.get(i).getName());
                    panel.add(dateLabel);
                    panel.add(new Label(comment.get(i).getEntry()));  
                }
            }
        });
         return panel;
    }
    
    /*
     * Save the comment to its blog entry.
     */
    private void saveComment(String commentToAdd, Blog blog, String user) {
        
        Comment comment = new Comment(commentToAdd, blog, user);
        artistInfo.postComment(comment, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BlogWidget failed RPC on saving the comments.\n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                update();
            }
        });
    }
    
    /*
     * Deletes the entire blog
     * 
     */
    private void clearEntireBlog() {
        Artist artist = viewController.getArtist();
        artistInfo.deleteBlog(artist, new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Object result) {
                update();
            }
        });
    }
    
    /*
     * Deletes a specific entry
     * 
     */
    private void deleteBlogEntry(Blog blog) {
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
