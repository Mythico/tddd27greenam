/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Emil
 */
public interface AccessServiceAsync {

    public void hasAccess(AsyncCallback<Boolean> callback);
    public void hasAccess(Long userId, AsyncCallback<Boolean> callback);
    public void userLoggedIn(AsyncCallback<String> callback);
    
    public void setArtistStatus(String user, boolean b, AsyncCallback c);
    public void setModeratorStatus(String user, boolean b, AsyncCallback c);
}
