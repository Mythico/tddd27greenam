/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.LinkObject;
import org.greenam.client.domain.User;

/**
 *
 * @author Emil
 */
public interface UserServiceAsync {
    
    public void isLogin(AsyncCallback<Boolean> callback);

    public void hasAccess(Long userId, AsyncCallback<Boolean> callback);
    
    public void isAdmin(AsyncCallback<Boolean> callback);

    public void makeArtist(Long userId, String name, AsyncCallback<Long> callback);

    public void deleteArtist(Artist artist, AsyncCallback callback);

    public void getArtist(Long artistId, AsyncCallback<Artist> callback);
    
    public void getAllArtists(AsyncCallback<List<Artist>> callback);

    public void getCurrentUser(AsyncCallback<User> callback);
    
    public void getArtistNames(List<Long> ids, AsyncCallback<List<LinkObject<String>>> callback);
    
    public void getAsArtist(User user, AsyncCallback<Artist> callback);
    
    public void addMoney(int amount, AsyncCallback<User> callback);
}
