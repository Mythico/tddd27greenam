package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import org.greenam.client.domain.AdminRequest;
import org.greenam.client.rpc.AdminService;
import org.greenam.client.rpc.AdminServiceAsync;
import org.greenam.client.view.ViewController;

/**
 * ArtistRequistListWidget is used to show the admins a list of all the users
 * requesting to become an artist, and gives the admins the ability to accept
 * or decline these.
 * 
 * @author Emil
 * @author Michael
 */
public class ArtistRequestListWidget extends BaseWidget {

    private final AdminServiceAsync adminInfo = GWT.create(AdminService.class);
    private final VerticalPanel requestPanel = new VerticalPanel();

    public ArtistRequestListWidget(ViewController viewController) {
        super(viewController, false);
        add(new Label("Artist requests"));
        add(requestPanel);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.isAdmin());

        if (visible) {
            requestPanel.clear();
            setStatus("Searching for requests...");
            adminInfo.listArtistRequests(listArtistRequest);
        }
    }
    AsyncCallback<List<AdminRequest>> listArtistRequest = new AsyncCallback<List<AdminRequest>>() {

        @Override
        public void onFailure(Throwable caught) {
            setError("Listing artist failed.", caught.getLocalizedMessage());
        }

        @Override
        public void onSuccess(List<AdminRequest> result) {
            for (final AdminRequest request : result) {
                RequestPanel panel = new RequestPanel(request);                
                requestPanel.add(panel);
            }
            clearStatus();
        }
    };
}

/**
 * RequestPanel is a helper class to the ArtistRequestListWidget that creates
 * a panel with two labels describing the request and two buttons for accepting
 * or declining a request for becoming an artist.
 * 
 * @author Emil
 * @author Michael
 */
class RequestPanel extends BasePanel {

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
