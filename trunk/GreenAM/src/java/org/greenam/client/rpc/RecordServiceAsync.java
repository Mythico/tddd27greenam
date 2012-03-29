/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import org.greenam.client.domain.Record;

/**
 *
 * @author Emil
 */
public interface RecordServiceAsync {

    void search(String s, AsyncCallback<List<Record>> callback);
}
