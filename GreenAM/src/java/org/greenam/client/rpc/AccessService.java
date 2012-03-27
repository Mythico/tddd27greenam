/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.greenam.client.rpc.exception.AccessException;

/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("rpc/accessservice")
public interface AccessService extends RemoteService {

    public boolean hasAccess();
    public boolean hasAccess(Long userId);
    public String userLoggedIn();
    
    public void setArtistStatus(String user, boolean b) throws AccessException;
    public void setModeratorStatus(String user, boolean b) throws AccessException;
}
