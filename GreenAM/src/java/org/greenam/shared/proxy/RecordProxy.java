/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.shared.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import java.util.List;
import org.greenam.server.domain.Record;
import org.greenam.server.locator.ObjectifyLocator;

/**
 *
 * @author Emil
 */
@ProxyFor(value = Record.class, locator = ObjectifyLocator.class)
public interface RecordProxy extends EntityProxy{

    Long getId();
    List<Long> getArtistIds();
    int getGenre();
    String getTitle();
}
