/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.Collection;
import org.greenam.client.rpc.jdo.Record;

/**
 *
 * @author Emil
 */
@RemoteServiceRelativePath("searchservice")
public interface SearchService extends RemoteService {

    public Collection<Record> search(String s);

    //public Collection<Package> search(String name, Package table);

    //public Package search(Long id, Package table);

    //public Collection<Package> search(Collection<Long> id);
}
