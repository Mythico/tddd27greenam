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
        newEventWidget = new NewEventWidget(viewController, this);
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

        if (visible) {
            artistInfo.getEvents(viewController.getArtist(), loadCallback);
        }
    }


    void addEvent(Event e) {
        VerticalPanel panel = new VerticalPanel();
        Date date = e.getDate();
        Label dateLabel = new Label(getDay(date.getDay()) + ", " + date.getDate() + " " + getMonth(date.getMonth()));
        //Label date = new Label(e.getDate().toString());
        Label msg = new Label(e.getMessage());

        panel.setStyleName("gam-Box");
        panel.add(dateLabel);
        panel.add(msg);
        eventPanel.add(panel);
    }
    
    private String getMonth(int month)
    {
        String monthString;
        switch (month) {
            case 0:  monthString = "January";
                     break;
            case 1:  monthString = "February";
                     break;
            case 2:  monthString = "March";
                     break;
            case 3:  monthString = "April";
                     break;
            case 4:  monthString = "May";
                     break;
            case 5:  monthString = "June";
                     break;
            case 6:  monthString = "July";
                     break;
            case 7:  monthString = "August";
                     break;
            case 8:  monthString = "September";
                     break;
            case 9:  monthString = "October";
                     break;
            case 10: monthString = "November";
                     break;
            case 11: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
        return monthString;
    }
    
    private String getDay(int day)
    {
        String dayString;
        switch (day) {
            case 0:  dayString = "Sunday";
                     break;
            case 1:  dayString = "Monday";
                     break;
            case 2:  dayString = "Tuesday";
                     break;
            case 3:  dayString = "Wednesday";
                     break;
            case 4:  dayString = "Thursday";
                     break;
            case 5:  dayString = "Friday";
                     break;
            case 6:  dayString = "Saturday";
                     break;
            default: dayString = "Invalid day";
                     break;
        }
        return dayString;
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
    private final ViewController viewController;
    private final CalendarWidget parent;

    public NewEventWidget(ViewController viewController, CalendarWidget parent) {
        this.viewController = viewController;        
        this.parent = parent;
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

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible && viewController.hasAccess());
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
                Long artistId = viewController.getArtist().getId();
                final Event evt = new Event(artistId, date, msg);
                artistInfo.postEvent(evt, new AsyncCallback() {

                    @Override
                    public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    public void onSuccess(Object result) {
                        parent.addEvent(evt);
                    }
                });
                errorLabel.setVisible(false);
            }
        }
    };
}
