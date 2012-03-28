/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.ui.Grid;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import java.util.List;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public abstract class ListWidget<T extends EntityProxy> extends Grid {

    protected final ViewController viewController;
    public final Receiver<List<T>> callback = new Receiver<List<T>>() {

        @Override
        public void onSuccess(List<T> response) {
            update(response);
        }
    };
    

    public ListWidget(ViewController viewController) {
        this.viewController = viewController;
        setStyleName("gam-RecordListWidget");
    }

    protected abstract void update(List<T> list);
}
