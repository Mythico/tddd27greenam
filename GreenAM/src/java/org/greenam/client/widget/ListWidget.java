/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import java.util.Collection;
import org.greenam.client.rpc.jdo.Ijdo;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public abstract class ListWidget<Jdo extends Ijdo> extends Grid {

    protected final ViewController viewController;
    public final AsyncCallback<Collection<Jdo>> callback =
            new AsyncCallback<Collection<Jdo>>() {

                @Override
                public void onSuccess(Collection<Jdo> result) {
                    update(result);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.toString());
                }
            };

    public ListWidget(ViewController viewController) {
        this.viewController = viewController;
        setStyleName("gam-RecordListWidget");
    }

    protected abstract void update(Collection<Jdo> list);
}
