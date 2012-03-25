/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.view;

import com.google.gwt.user.client.ui.DeckPanel;

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
    
 

    public void setArtistView(Long id, String name) {        
        artistView.setArtist(id, name);
        showWidget(ARTIST);        
    }

    public void setSearchView(String search) {
        searchResultView.search(search);
        showWidget(SEARCH_RESULT);    
    }
    
    public void setSearchAlbumView(Long id) {
        searchResultView.searchAlbum(id);
        showWidget(SEARCH_RESULT);    
    }
    
    public void setSearchAlbumView(String name) {
        searchResultView.searchAlbum(name);
        showWidget(SEARCH_RESULT);    
    }

    public void setSearchTitleView(Long id) {
        searchResultView.searchTitle(id);
        showWidget(SEARCH_RESULT);    
    }
    
    public void setSearchTitleView(String name) {
        searchResultView.searchTitle(name);
        showWidget(SEARCH_RESULT);    
    }

    public void setSearchGenreView(int genre) {
        searchResultView.searchGenre(genre);
        showWidget(SEARCH_RESULT);    
    }
    
    public void setSearchGenreView(String genre) {
        searchResultView.searchGenre(genre);
        showWidget(SEARCH_RESULT);    
    }

}
