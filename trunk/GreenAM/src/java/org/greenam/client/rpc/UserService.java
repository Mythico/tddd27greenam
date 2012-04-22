/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.LinkObject;
import org.greenam.client.domain.User;

/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("rpc/userservice")
public interface UserService extends RemoteService {

    public boolean isLogin();

    public boolean hasAccess(Long userId);
    
    public boolean isAdmin();

    public long makeArtist(Long userId, String name);

    public void deleteArtist(Artist artist);

    public Artist getArtist(Long artistId);
    
    public List<Artist> getAllArtists();

    public User getCurrentUser();

    public List<LinkObject<String>> getArtistNames(List<Long> ids);

    public Artist getAsArtist(User user);
    
    public User addMoney(int amount);
}