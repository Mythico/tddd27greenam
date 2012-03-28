/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.shared.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import java.util.Set;
import org.greenam.server.domain.Album;
import org.greenam.server.locator.ObjectifyLocator;

/**
 *
 * @author Emil
 */
@ProxyFor(value = Album.class, locator = ObjectifyLocator.class)
public interface AlbumProxy extends EntityProxy{

    Long getId();
    Set<Long> getArtistIds();
    void setArtistIds(Set<Long> artistIds);
    String getTitle();
    void setTitle(String title);
    Set<Long> getRecordIds();
    void setRecordIds(Set<Long> recordIds);
}
