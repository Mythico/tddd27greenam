/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.User;

/**
 *
 * @author Emil
 */
public interface UserServiceAsync {
    
    public void hasAccess(AsyncCallback<Boolean> callback);

    public void hasAccess(Long userId, AsyncCallback<Boolean> callback);

    public void makeArtist(Long userId, AsyncCallback<Long> callback);

    public void deleteArtist(Long artistId, AsyncCallback callback);

    public void getArtist(Long artistId, AsyncCallback<Artist> callback);

    public void getCurrentUser(AsyncCallback<User> callback);
}
