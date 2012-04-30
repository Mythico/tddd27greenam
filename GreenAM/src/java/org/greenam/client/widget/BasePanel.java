/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * The base panel is a simple panel used by many helper classes in this 
 * application. It has the common functionality that most of them use.
 * This includes using the same css-style and the ability to delete a panel and 
 * its content.
 * @author Emil
 * @author Michael
 */
public abstract class BasePanel extends HorizontalPanel {


    public BasePanel() {
        setStyleName("gam-Box");
        setWidth("580px");
    }


    /**
     * Adds a delete button and connects it to a click handler.
     * @param handler 
     */
    protected void addDeleteHandler(ClickHandler handler) {
        Image remove = new Image("img/cross_script.png");
        remove.addClickHandler(handler);
        super.add(remove);
    }
    /**
     * A callback used by the click handler for removing this panel when an
     * artist has been removed.
     */
    protected final AsyncCallback removeThis = new AsyncCallback() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(Object result) {
            removeFromParent();
        }
    };
}
