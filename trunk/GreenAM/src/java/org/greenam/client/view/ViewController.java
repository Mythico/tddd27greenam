/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.user.client.ui.DeckPanel;
import org.greenam.client.domain.Artist;

/**
 *
 * @author Emil
 */
public final class ViewController extends DeckPanel{
    
    public final static int SEARCH_RESULT = 0;
    public final static int ARTIST = 1;
    
    

    private final ArtistView artistView = new ArtistView(this);
    private final SearchResultView searchResultView = new SearchResultView(this);

    public ViewController() {
        insert(searchResultView, SEARCH_RESULT);
        insert(artistView, ARTIST);
        
        //Set the default view.
        showWidget(SEARCH_RESULT);  
    }
    
 

    public void setArtistView(Artist artist) {        
        artistView.setArtist(artist);
        showWidget(ARTIST);        
    }

    public void setSearchView(String search) {
        searchResultView.search(search);
        showWidget(SEARCH_RESULT);    
    }
}
