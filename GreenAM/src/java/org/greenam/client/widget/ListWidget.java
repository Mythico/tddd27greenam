/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public abstract class ListWidget<T> extends Grid {

    protected final ViewController viewController;
    public final AsyncCallback<List<T>> callback =
            new AsyncCallback<List<T>>() {

                public void onSuccess(List<T> result) {
                    update(result);
                }

                public void onFailure(Throwable caught) {
                }
            };
    public final AsyncCallback<T> callbackId =
            new AsyncCallback<T>() {

                public void onSuccess(T result) {
                    LinkedList<T> t = new LinkedList<T>();
                    t.add(result);
                    update(t);
                }

                public void onFailure(Throwable caught) {
                }
            };
    
    
    public ListWidget(ViewController viewController) {
        this.viewController = viewController;
        setStyleName("gam-RecordListWidget");
    }
    
    
    protected abstract void update(List<T> list);
;
}
