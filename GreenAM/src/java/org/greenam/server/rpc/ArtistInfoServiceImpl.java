/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import org.greenam.client.rpc.jdo.BlogPage;
import org.greenam.client.rpc.jdo.Artist;
import org.greenam.client.rpc.jdo.Blog;
import org.greenam.client.rpc.jdo.Event;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.rpc.server.Pair;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import org.greenam.client.rpc.ArtistInfoService;
import org.greenam.client.rpc.exception.AccessException;
import org.greenam.client.rpc.exception.DataNotFoundException;
import org.greenam.client.rpc.jdo.*;

/**
 *
 * @author Emil
 */
public class ArtistInfoServiceImpl extends RemoteServiceServlet implements ArtistInfoService {

    private UserService userService = UserServiceFactory.getUserService();

    @Override
    public String getBiogarphy(Long artistId) throws DataNotFoundException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String bio = null;
        
        try {
            Artist artist = getArtist(artistId, pm);
            bio = artist.getBiography();
        } finally {
            pm.close();
        }
        return bio;
    }

    @Override
    public BlogPage getBlog(Long artistId, int page)
            throws DataNotFoundException {
        BlogPage blogPage = new BlogPage();
        PersistenceManager pm = PMF.get().getPersistenceManager();
         ArrayList<Blog> blogs = null;
        try {
            Artist artist = getArtist(artistId, pm);
            blogs = artist.getBlogPosts();
        } finally {
            pm.close();
        }

        //Logic for adding only the correct blogs.
        int from = blogPage.SIZE * page;
        int to = blogPage.SIZE * page + blogPage.SIZE;

        if (from >= blogs.size()) {
            throw new RuntimeException("Blogpage dosn't exist"); //TODO: Add better exception?
        }

        to = (to < blogs.size()) ? to : blogs.size() - 1;

        blogPage.setBlogs(blogs.subList(from, to));
        return blogPage;
    }

    @Override
    public List<Event> getEventCalender(Long artistId, int month)
            throws DataNotFoundException {

        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<Event> events = null;
        try{
            Artist artist = getArtist(artistId, pm);
            events = artist.getEvents();
        } finally{
            pm.close();
        }
        
        return events;
    }

    @Override
    public void postBlogComment(Long blogId, String content)
            throws DataNotFoundException, AccessException {

        if (!userService.isUserLoggedIn()) {
            throw new AccessException("You have to be login to post "
                    + "a blog comment");
        }
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try{              
            String fid = userService.getCurrentUser().getFederatedIdentity();
            Long userId = pm.getObjectById(User.class, fid).getId();
            
            Blog blog = pm.getObjectById(Blog.class, blogId);
            blog.getComments().add(new Comment(userId, content));
            pm.setUserObject(blog);
        } finally{
            pm.close();
        }
        
    }

    @Override
    public void postBlog(Long artistId, Blog blog) throws DataNotFoundException,
            AccessException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try{
            checkLogin(artistId, pm);                
            Artist artist = getArtist(artistId, pm);
            artist.getBlogPosts().add(blog);
        } finally{
            pm.close();
        }
    }

    @Override
    public void postEvent(Long artistId, Event event)
            throws DataNotFoundException, AccessException {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            checkLogin(artistId, pm);            
            Artist artist = getArtist(artistId, pm);
            artist.getEvents().add(event);
        } finally {
            pm.close();
        }

    }

    @Override
    public void postBiography(Long artistId, String bio)
            throws DataNotFoundException, AccessException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        
        try{
            checkLogin(artistId, pm);
            Artist artist = getArtist(artistId, pm);
            artist.setBiography(bio);
        } finally{
            pm.close();
        }
    }

    private void checkLogin(Long artistId, PersistenceManager pm)
            throws AccessException {
        String fid = userService.getCurrentUser().getFederatedIdentity();
        Long id = pm.getObjectById(User.class, fid).getId();

        if (id != artistId) {
            throw new AccessException("User " + artistId
                    + " is not the owner of the calender an can "
                    + "therefor not post any events.");
        }
    }

    /**
     * A helper function for fetching artist information
     *
     * @param artistId
     * @return
     * @throws EntityNotFoundException
     */
    private Artist getArtist(Long artistId, PersistenceManager pm)
            throws DataNotFoundException {
        Artist artist = null;
        artist = pm.getObjectById(Artist.class, artistId);

        if (artist == null) {
            throw new DataNotFoundException("Could not find Artist with id: "
                    + artistId);
        }

        return artist;
    }
}
