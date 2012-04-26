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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.greenam.client.domain.AdminRequest;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;

/**
 * The RequestArtistWidget is a simple form for creating an AdminRequest that
 * will request artist status for the current user.
 *
 * @author Emil
 * @author Michael
 */
public class RequestArtistWidget extends VerticalPanel {

    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private TextArea textArea = new TextArea();
    private Label statusText = new Label();

    public RequestArtistWidget() {

        Button send = new Button("Send");
        send.addClickHandler(sendRequest);
        statusText.setVisible(false);

        add(new Label("Request artist status"));
        add(new Label("Message:"));
        add(textArea);
        add(send);
        add(statusText);

    }
    private final ClickHandler sendRequest = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            setStatus("Sending the request...");
            String msg = textArea.getText();

            int type = AdminRequest.TYPE_ARTIST;
            userInfo.sendRequest(msg, type, callback);
        }
    };
    private AsyncCallback callback = new AsyncCallback() {

        @Override
        public void onFailure(Throwable caught) {
            setError("An error has occured while sending the request.\n\n" 
                    + caught);
        }

        @Override
        public void onSuccess(Object result) {
            setStatus("The request has been send.");
        }
    };

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        statusText.setVisible(false);
    }

    private void setStatus(String msg) {
        statusText.setStyleName("gam-Status");
        statusText.setVisible(true);
        statusText.setText(msg);
    }

    private void setError(String msg) {
        statusText.setStyleName("gam-Error");
        statusText.setVisible(true);
        statusText.setText(msg);
    }
}
