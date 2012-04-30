/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Emil
 * @author Michael
 */
public abstract class BaseWidget extends VerticalPanel {

    private Label statusText = new Label();
    private Label errorText = new Label();
    private final VerticalPanel contentPanel = new VerticalPanel();

    public BaseWidget() {
        
        statusText.setStyleName("gam-Status");
        errorText.setStyleName("gam-Error");
        
        super.add(contentPanel);
        super.add(statusText);
        super.add(errorText);
    }

    /**
     * Add widgets directly to the content panel.
     * @param w 
     */
    @Override
    public final void add(Widget w) {
        contentPanel.add(w);
    }
    
    

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        statusText.setVisible(false);
        errorText.setVisible(false);
    }

    /**
     * Sets a status message in the bottom of this widget to a message.
     * @param msg A status message.
     */
    protected void setStatus(String msg) {
        statusText.setVisible(true);
        statusText.setText(msg);
        errorText.setText("");
        errorText.setVisible(false);
    }

    /**
     * Sets an error message in the bottom of this widget to a message.
     * @param msg An error message.
     */
    protected void setError(String msg, String error) {
        statusText.setVisible(true);
        statusText.setText(msg);
        errorText.setVisible(true);
        errorText.setText(error);
    }
    
    /**
     * Clears any status or error messages and hides the label.
     */
    protected void clearStatus(){
        statusText.setText("");
        statusText.setVisible(false);
        errorText.setText("");
        errorText.setVisible(false);
    }
}
