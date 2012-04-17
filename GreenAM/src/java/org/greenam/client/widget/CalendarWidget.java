/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.sourceforge.htmlunit.corejs.javascript.ast.LabeledStatement;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Event;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;

//TODO: Add some better Date handler.
/**
 *
 * @author Emil
 */
public class CalendarWidget extends HorizontalPanel {

    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    VerticalPanel eventPanel = new VerticalPanel();
    
    private NewEventWidget newEventWidget = new NewEventWidget();

    public CalendarWidget() {
        setStyleName("gam-CalendarWidget");
        eventPanel.setSize("400px", "100%");
        newEventWidget.setSize("200px", "100%");
        add(eventPanel);
        add(newEventWidget);
    }
    private AsyncCallback<List<Event>> loadCallback = new AsyncCallback<List<Event>>() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(List<Event> events) {
            eventPanel.clear();
            for (Event event : events) {
                addEvent(event);
            }
        }
    };

    public void setArtist(Artist artist, boolean hasAccess) {
        artistInfo.getEvents(artist, loadCallback);
        newEventWidget.setArtist(artist, hasAccess);
    }
    
    private void addEvent(Event e){
        VerticalPanel panel = new VerticalPanel();
        Label date = new Label(e.getDate().toString());
        Label msg = new Label(e.getMessage());
        
        panel.setStyleName("gam-Box");
        panel.add(date);
        panel.add(msg);
        eventPanel.add(panel);
    }
}

class NewEventWidget extends VerticalPanel {

    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final DatePicker datePicker = new DatePicker();
    private final Label messageLabel = new Label("Event text");
    private final Label errorLabel = new Label();
    private final TextBox messageBox = new TextBox();
    private final Button addEventButton = new Button("Add");
    private final HorizontalPanel messagePanel = new HorizontalPanel();
    private Artist artist;

    public NewEventWidget() {

        addEventButton.addClickHandler(addEvent);
        errorLabel.setStyleName("gam-error-string");
        errorLabel.setVisible(false);

        messagePanel.add(messageLabel);
        messagePanel.add(messageBox);
        add(datePicker);
        add(messagePanel);
        add(addEventButton);
        add(errorLabel);

    }

    public void setArtist(Artist artist, boolean hasAccess) {
        this.artist = artist;
        setVisible(hasAccess);
    }
    private ClickHandler addEvent = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            Date date = datePicker.getValue();
            String msg = messageBox.getText();
            if (date == null) {
                errorLabel.setText("You have to select a date:");
                errorLabel.setVisible(true);
            } else if (msg.equals("")) {
                errorLabel.setText("You can't post an empty message:");
                errorLabel.setVisible(true);
            } else {
                artistInfo.postEvent(new Event(artist.getId(), date, msg), postCallback);
                errorLabel.setVisible(false);
            }
        }
    };
    private AsyncCallback postCallback = new AsyncCallback() {

        @Override
        public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onSuccess(Object result) {
        }
    };
}
