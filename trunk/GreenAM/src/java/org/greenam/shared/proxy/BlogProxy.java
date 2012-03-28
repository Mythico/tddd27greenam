/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.shared.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import org.greenam.server.domain.Blog;
import org.greenam.server.locator.ObjectifyLocator;

/**
 *
 * @author Emil
 */
@ProxyFor(value = Blog.class, locator = ObjectifyLocator.class)
public interface BlogProxy extends EntityProxy{

    Long getId();
}
