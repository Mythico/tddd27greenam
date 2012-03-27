/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Collection;
import org.greenam.client.rpc.jdo.Record;

/**
 *
 * @author Emil
 */
public interface SearchServiceAsync {

    
    public void search(String s, AsyncCallback<Collection<Record>> callback);
    
    /*public void search(String name, Package table, 
            AsyncCallback<Collection<Package>> callback);
    public void search(Collection<Long> id, Package table, 
            AsyncCallback<Collection<Package>> callback);
    public void search(Long id, Package table, AsyncCallback<Package> callback);*/
    
    
}
