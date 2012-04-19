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

    public boolean hasAccess();

    public boolean hasAccess(Long userId);

    public long makeArtist(Long userId, String name);

    public void deleteArtist(Long artistId);

    public Artist getArtist(Long artistId);

    public User getCurrentUser();

    public List<LinkObject<String>> getArtistNames(List<Long> ids);

    public Long getAsArtist(User user);
}