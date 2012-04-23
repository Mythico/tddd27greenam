/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DeckPanel;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.User;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;

/**
 *
 * @author Emil
 */
public final class ViewController extends DeckPanel{

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private final int HOME = 0;
    private final int SEARCH = 1;
    private final int ARTIST = 2;
    private final int USER = 3;
    private final int ALBUM = 4;
    private final HomeView homeView = new HomeView(this);
    private final ArtistView artistView = new ArtistView(this);
    private final UserView userView = new UserView(this);
    private final SearchResultView searchResultView = new SearchResultView(this);
    private final AlbumView albumView = new AlbumView(this);
    private Artist artist;
    private User user;
    private boolean isAdmin;

    public ViewController() {
        insert(homeView, HOME);
        insert(searchResultView, SEARCH);
        insert(artistView, ARTIST);
        insert(userView, USER);
        insert(albumView, ALBUM);

        //Set the default view.
        showWidget(HOME);


        userInfo.getCurrentUser(new AsyncCallback<User>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(User result) {
                user = result;
            }
        });

        userInfo.isAdmin(new AsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Boolean result) {
                isAdmin = result;
            }
        });

    }

    public void setArtistView(Long artistId) {

        userInfo.getArtist(artistId, new AsyncCallback<Artist>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Artist result) {
                artist = result;
                showWidget(ARTIST);
            }
        });
    }

    public void setUserView() {
        userInfo.getAsArtist(user, new AsyncCallback<Artist>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Artist result) {
                artist = result;
                showWidget(USER);
            }
        });
    }

    public void setAlbumView(Long albumId) {
        showWidget(ALBUM);
    }

    public void setSearchView(String search) {
        searchResultView.search(search);
        showWidget(SEARCH);
    }

    /**
     * Gets the selected artist.
     *
     * @return Returns an Artist or null if none is selected.
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Gets the current user.
     *
     * @return Returns a User or null if no one is login.
     */
    public User getUser() {
        return user;
    }

    /**
     * Checks if the user has login or is anonymous. This function is used by
     * widgets that requires that you are login, ex to comment on blogs.
     *
     * @return Returns true if the user has login, otherwise false.
     */
    public boolean isLogin() {
        return user != null;
    }

    /**
     * Checks if the user has access to change an artist page or other pages
     * that requires the user to be an artist.
     *
     * @return Returns true if the user is an artist.
     */
    public boolean hasAccess() {
        if (user == null || artist == null) {
            return false;
        }

        return user.getId().equals(artist.getUserId());
    }

    /**
     * Removes all access that requires a user to be login.
     */
    public void logout() {
        user = null;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
