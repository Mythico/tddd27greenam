/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.user.client.ui.DeckPanel;
import org.greenam.client.domain.User;

/**
 *
 * @author Emil
 */
public final class ViewController extends DeckPanel{
    
    public final int SEARCH_RESULT = 0;
    public final int ARTIST = 1;
    public final int USER = 2;
    public final int ALBUM = 3;
    
    

    private final ArtistView artistView = new ArtistView(this);
    private final UserView userView = new UserView(this);
    private final SearchResultView searchResultView = new SearchResultView(this);
    private final AlbumView albumView = new AlbumView(this);

    public ViewController() {
        insert(searchResultView, SEARCH_RESULT);
        insert(artistView, ARTIST);
        insert(userView, USER);
        insert(albumView, ALBUM);
        
        //Set the default view.
        showWidget(SEARCH_RESULT);  
    }
    
 

    public void setArtistView(Long artistId) {        
        artistView.setArtist(artistId);
        showWidget(ARTIST);        
    }
    
    public void setUserView(User user) {        
        userView.setUser(user);
        showWidget(USER);        
    }
    
    public void setAlbumView(Long albumId){
        showWidget(ALBUM);
    }

    public void setSearchView(String search) {
        searchResultView.search(search);
        showWidget(SEARCH_RESULT);    
    }

}
