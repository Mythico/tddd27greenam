/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import org.greenam.client.domain.AdminRequest;
import org.greenam.client.domain.Artist;
import org.greenam.client.rpc.AdminService;
import org.greenam.client.rpc.AdminServiceAsync;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class ArtistRequestListWidget extends VerticalPanel {

    private final AdminServiceAsync adminInfo = GWT.create(AdminService.class);
    private final ViewController viewController;
    private final VerticalPanel requestPanel = new VerticalPanel();

    public ArtistRequestListWidget(ViewController viewController) {
        this.viewController = viewController;
        add(new Label("Artist requests"));
        add(requestPanel);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.isAdmin());

        if (visible) {
            requestPanel.clear();
            adminInfo.listArtistRequests(listArtistRequest);
        }
    }
    AsyncCallback<List<AdminRequest>> listArtistRequest = new AsyncCallback<List<AdminRequest>>() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(List<AdminRequest> result) {
            for (final AdminRequest request : result) {
                RequestPanel panel = new RequestPanel(request);                
                requestPanel.add(panel);
            }
        }
    };
}

/**
 * RequestPanel is a helper class to the ArtistRequestListWidget that creates
 * a panel with two labels describing the request and two buttons for accepting
 * or declining a request for becoming an artist.
 * @author Emil
 */
class RequestPanel extends HorizontalPanel {

    private final AdminServiceAsync adminInfo = GWT.create(AdminService.class);
    final VerticalPanel vp = new VerticalPanel();
    private final AdminRequest request;

    public RequestPanel(AdminRequest request) {
        this.request = request;
        setStyleName("gam-Box");
        Label userLabel = new Label("User: " + request.getUserId());
        Label msgLabel = new Label("Message: " + request.getMessage());
        Button accept = new Button("Yes");
        Button decline = new Button("No");

        accept.addClickHandler(acceptArtist);
        decline.addClickHandler(declineArtist);

        add(accept);
        add(decline);
        vp.add(userLabel);
        vp.add(msgLabel);
        add(vp);
    }
    
    
    /**
     * A callback used by the click handlers for removing this panel when an 
     * users request has been accepted or declined.
     */
    private final AsyncCallback removeThis = new AsyncCallback() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(Object result) {
            removeFromParent();
        }
    };
    
    /**
     * A click handler used for accepting users request for becoming an artist.
     */
    private ClickHandler acceptArtist = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            adminInfo.makeArtist(request, removeThis);
        }
    };
    
    /**
     * A click handler used for declining users request for becoming an artist.
     */
    private ClickHandler declineArtist = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            adminInfo.deleteRequest(request, removeThis);
        }
    };
}
