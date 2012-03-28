/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.shared.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import java.util.Set;
import org.greenam.server.domain.Artist;
import org.greenam.server.locator.ObjectifyLocator;

/**
 *
 * @author Emil
 */
@ProxyFor(value = Artist.class, locator = ObjectifyLocator.class)
public interface ArtistProxy extends EntityProxy{

    Long getId();
    Long getUserId();
    Set<Long> getAlbums();
    String getBiography();
    void setBiography(String biography);

    String getName();
    void setName(String name);
}
