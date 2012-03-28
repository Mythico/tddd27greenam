/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.shared.service;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import java.util.List;
import org.greenam.server.locator.DaoServiceLocator;
import org.greenam.server.service.ArtistDao;
import org.greenam.server.service.RecordDao;
import org.greenam.server.service.UserDao;
import org.greenam.shared.proxy.*;

/**
 *
 * @author Emil
 */
public interface ApplicationRequestFactory extends RequestFactory {

    @Service(value = UserDao.class, locator = DaoServiceLocator.class)
    interface UserRequestContext extends RequestContext {

        Request<Boolean> hasAccess();

        Request<Boolean> hasAccess(Long userId);

        Request<Long> makeArtist(Long userId);

        Request<Void> deleteArtist(Long artistId);

        Request<ArtistProxy> getArtist(Long artistId);

        Request<Void> saveArtist(ArtistProxy artist);
        Request<UserProxy> getCurrentUser();

//        Request<Key<Moderator>> makeModerator(Key<User> id);
//        Request<Void> deleteModerator(Key<Moderator> id);
//        Request<ModeratorProxy> getArtist(Key<Moderator> id);
    }

    @Service(value = ArtistDao.class, locator = DaoServiceLocator.class)
    interface ArtistRequestContext extends RequestContext {

        Request<List<EventProxy>> getEvents(ArtistProxy artist, int month);

        Request<List<BlogProxy>> getBlogs(ArtistProxy artist, int page);

        Request<Void> postEvent(ArtistProxy artist, EventProxy event);

        Request<Void> postBlog(ArtistProxy artist, BlogProxy blog);
    }

    @Service(value = RecordDao.class, locator = DaoServiceLocator.class)
    interface RecordRequestContext extends RequestContext {

        Request<List<RecordProxy>> search(String s);
    }

    RecordRequestContext recordRequest();

    ArtistRequestContext artistRequest();

    UserRequestContext userRequest();
}
