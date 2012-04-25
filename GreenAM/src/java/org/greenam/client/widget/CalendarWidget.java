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

//TODO: Add some better Date handler.
/**
 *
 * @author Emil
 */
public class CalendarWidget extends HorizontalPanel {

    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    VerticalPanel eventPanel = new VerticalPanel();
    private NewEventWidget newEventWidget;
    private final ViewController viewController;

    public CalendarWidget(ViewController viewController) {
        setStyleName("gam-CalendarWidget");

        this.viewController = viewController;
        newEventWidget = new NewEventWidget(viewController);
        newEventWidget.addClickHandler(addEvent);
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

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        newEventWidget.setVisible(visible && viewController.hasAccess());
        
        if (visible) {
            artistInfo.getEvents(viewController.getArtist(), loadCallback);
        }
    }

    void addEvent(final Event event) {
        final VerticalPanel panel = new VerticalPanel();
        final HorizontalPanel Hpanel = new HorizontalPanel();
        Date date = event.getDate();
        DateTimeFormat dtf = DateTimeFormat.getFormat("EEEE, d MMMM");
        Label dateLabel = new Label(dtf.format(date));
        Label msg = new Label(event.getMessage());

        panel.setStyleName("gam-Box");
        panel.add(dateLabel);
        panel.add(msg);
        Hpanel.add(panel);

        if (viewController.hasAccess()) {
            Button remove = new Button("X");
            final AsyncCallback deleteEvent = new AsyncCallback() {

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Deletion of event failed.\n\n" + caught);
                }

                @Override
                public void onSuccess(Object result) {
                    eventPanel.remove(Hpanel);
                }
            };

            remove.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent e) {
                    artistInfo.deleteEvent(event, deleteEvent);
                }
            });
            Hpanel.add(remove);
        }

        eventPanel.add(Hpanel);
    }
    private ClickHandler addEvent = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            if (newEventWidget.checkConstraints()) {
                Date date = newEventWidget.getDate();
                String msg = newEventWidget.getText();
                Long artistId = viewController.getArtist().getId();
                final Event evt = new Event(artistId, date, msg);
                artistInfo.postEvent(evt, new AsyncCallback() {

                    @Override
                    public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet.");
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

class NewEventWidget extends VerticalPanel {

    private final DatePicker datePicker = new DatePicker();
    private final Label messageLabel = new Label("Event text");
    private final Label errorLabel = new Label();
    private final TextBox messageBox = new TextBox();
    private final Button addEventButton = new Button("Add");
    private final HorizontalPanel messagePanel = new HorizontalPanel();
    private final ViewController viewController;

    public NewEventWidget(ViewController viewController) {
        this.viewController = viewController;
        errorLabel.setStyleName("gam-error-string");
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
