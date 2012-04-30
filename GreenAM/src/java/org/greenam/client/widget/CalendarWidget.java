/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import java.util.Date;
import java.util.List;
import org.greenam.client.domain.Event;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.view.ViewController;

/**
 * A calendar for showing and adding upcoming events for a specified artist.
 * @author Emil
 * @author Michael
 */
public class CalendarWidget extends BaseWidget {

    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private VerticalPanel eventPanel = new VerticalPanel();
    private AddEventPanel addEventPanel;

    public CalendarWidget(ViewController viewController) {
        super(viewController);

        addEventPanel = new AddEventPanel(viewController);
        addEventPanel.addClickHandler(addEvent);
        eventPanel.setSize("400px", "100%");
        addEventPanel.setSize("200px", "100%");


        HorizontalPanel panel = new HorizontalPanel();
        panel.add(eventPanel);
        panel.add(addEventPanel);
        add(panel);

    }
    private AsyncCallback<List<Event>> loadCallback = new AsyncCallback<List<Event>>() {

        @Override
        public void onFailure(Throwable caught) {
            setError("Calendar load failed.", caught.getLocalizedMessage());
        }

        @Override
        public void onSuccess(List<Event> events) {
            clearStatus();
            eventPanel.clear();
            for (Event event : events) {
                addEvent(event);
            }
        }
    };

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        addEventPanel.setVisible(visible && viewController.hasAccess());

        if (visible) {
            artistInfo.getEvents(viewController.getArtist(), loadCallback);
        }
    }

    /**
     * Creates an EventPanel containing information of a specified event and
     * adding it to the panel.
     * @param event An event.
     */
    private void addEvent(Event event) {
        EventPanel panel = new EventPanel(event, viewController.hasAccess());
        eventPanel.add(panel);
    }
    private ClickHandler addEvent = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            if (addEventPanel.checkConstraints()) {
                Date date = addEventPanel.getDate();
                String msg = addEventPanel.getText();
                Long artistId = viewController.getArtist().getId();
                final Event evt = new Event(artistId, date, msg);
                artistInfo.postEvent(evt, new AsyncCallback() {

                    @Override
                    public void onFailure(Throwable caught) {
                        setError("Posing event failed.",caught.getLocalizedMessage());                        
                    }

                    @Override
                    public void onSuccess(Object result) {
                        addEvent(evt);
                    }
                });
            }
        }
    };
}

/**
 * A helper class for representing an event in the calender.
 *
 * @author Emil
 * @author Michael
 */
class EventPanel extends HorizontalPanel {

    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final Event event;

    public EventPanel(Event event, boolean hasAccess) {
        this.event = event;
        setStyleName("gam-Box");

        final VerticalPanel vp = new VerticalPanel();
        vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        Date date = event.getDate();
        DateTimeFormat dtf = DateTimeFormat.getFormat("EEEE, d MMMM");
        Label dateLabel = new Label(dtf.format(date));
        Label msg = new Label(event.getMessage());

        vp.add(dateLabel);
        vp.add(msg);
        add(vp);

        if (hasAccess) {
            Image remove = new Image("img/cross_script.png");
            remove.addClickHandler(removeEvent);
            add(remove);
        }
    }
    private final ClickHandler removeEvent = new ClickHandler() {

        @Override
        public void onClick(ClickEvent e) {
            artistInfo.deleteEvent(event, removeThis);
        }
    };
    private AsyncCallback removeThis = new AsyncCallback() {

        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Deletion of event failed.\n\n" + caught);
        }

        @Override
        public void onSuccess(Object result) {
            removeFromParent();
        }
    };
}

class AddEventPanel extends VerticalPanel {

    private final DatePicker datePicker = new DatePicker();
    private final Label messageLabel = new Label("Event text");
    private final Label errorLabel = new Label();
    private final TextBox messageBox = new TextBox();
    private final Button addEventButton = new Button("Add");
    private final HorizontalPanel messagePanel = new HorizontalPanel();
    private final ViewController viewController;

    public AddEventPanel(ViewController viewController) {
        this.viewController = viewController;
        errorLabel.setStyleName("gam-Error");
        setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        errorLabel.setVisible(false);

        messagePanel.add(messageLabel);
        messagePanel.add(messageBox);
        add(datePicker);
        add(messagePanel);
        add(addEventButton);
        add(errorLabel);

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.hasAccess());
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addEventButton.addClickHandler(handler);
    }

    public boolean checkConstraints() {
        if (getDate() == null) {
            errorLabel.setText("You have to select a date:");
            errorLabel.setVisible(true);
            return false;
        } else if (getText().equals("")) {
            errorLabel.setText("You can't post an empty message:");
            errorLabel.setVisible(true);
            return false;
        } else {
            errorLabel.setVisible(false);
            return true;
        }
    }

    public String getText() {
        return messageBox.getText();
    }

    public Date getDate() {
        return datePicker.getValue();
    }
}
