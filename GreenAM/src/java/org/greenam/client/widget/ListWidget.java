/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import java.util.List;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public abstract class ListWidget<T> extends ScrollPanel {

    protected final ViewController viewController;
    protected final Grid grid = new Grid();
    public final AsyncCallback<List<T>> callback = new AsyncCallback<List<T>>() {

        @Override
        public void onSuccess(List<T> response) {
            update(response);
        }

        @Override
        public void onFailure(Throwable caught) {
            Window.alert("ListWidget failed callback. " + caught);
        }
    };
    

    public ListWidget(ViewController viewController) {
        this.viewController = viewController;
        setStyleName("gam-RecordListWidget");
        
        setSize("600px", "60%");
        add(grid);
    }

    protected abstract void update(List<T> list);
}
