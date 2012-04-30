/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.greenam.client.domain.User;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 * @author Michael
 */
public abstract class BaseWidget extends ScrollPanel {

    private Label statusText = new Label();
    private Label errorText = new Label();
    private final VerticalPanel panel = new VerticalPanel();
    private final VerticalPanel contentPanel = new VerticalPanel();
    protected final ViewController viewController;
    private final boolean fixedSize;

    public BaseWidget(ViewController viewController){
        this(viewController, true);
    }
    
    public BaseWidget(ViewController viewController, boolean fixedSize) {
        this.viewController = viewController;
        this.fixedSize = fixedSize;
        
        if(fixedSize){
            setWidth("600px");
        }
        
        statusText.setStyleName("gam-Status");
        errorText.setStyleName("gam-Error");
        
        panel.add(contentPanel);
        panel.add(statusText);
        panel.add(errorText);
        super.add(panel);
    }

    /**
     * Add widgets directly to the content panel.
     * @param w 
     */
    @Override
    public final void add(Widget w) {
        contentPanel.add(w);
        if(fixedSize){
            setHeight("600px");
        }
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
    
    /**
     * An update function that will be called if the class has registered for
     * user updates in the view controller.
     * This function doesn't do anything by default and should therfore be
     * overwritten.
     * @param user An updated user. 
     */
    public void update(User user){
        
    }
}
