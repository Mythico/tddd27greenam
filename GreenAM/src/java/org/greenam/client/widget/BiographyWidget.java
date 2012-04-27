/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.greenam.client.domain.Artist;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.rpc.UserService;
import org.greenam.client.rpc.UserServiceAsync;
import org.greenam.client.view.ViewController;

/**
 *
 * @author Emil
 */
public class BiographyWidget extends VerticalPanel {

    private final RichTextArea editArea = new RichTextArea();
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final Button saveButton = new Button("Edit");
    private final Button cancelButton = new Button("Cancel");
    private final ViewController viewController;
    private final Label bioLabel = new Label();

    public BiographyWidget(final ViewController viewController) {
        setSize("100%", "100%");

        this.viewController = viewController;

        bioLabel.setStyleName("gam-Box");
        bioLabel.setWidth("600px");

        editArea.setEnabled(true);
        editArea.setVisible(false);
        editArea.setStyleName("gam-Textbox");
        cancelButton.setVisible(false);

        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (cancelButton.isVisible()) {
                    save();
                    exitEdit();
                } else {
                    enterEdit();
                }
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                exitEdit();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        add(bioLabel);
        add(editArea);
        add(buttonPanel);

    }

    private void save() {
        if (!viewController.hasAccess()) {
            Window.alert("You are trying to save an artist biography without"
                    + " having the correct access.");
            return;
        }

        Long id = viewController.getArtist().getId();
        artistInfo.editBiography(editArea.getText(), id, new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Object result) {
                Artist artist = viewController.getArtist();
                artist.setBiography(editArea.getText());
                bioLabel.setText(viewController.getArtist().getBiography());
            }
        });


    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        buttonPanel.setVisible(visible && viewController.hasAccess());
        if (visible) {
            exitEdit();
        }
    }

    /**
     * Exiting editing mode
     */
    private void exitEdit() {
        saveButton.setText("Edit");
        bioLabel.setText(viewController.getArtist().getBiography());
        editArea.setVisible(false);
        cancelButton.setVisible(false);
    }

    /**
     * Enter editing mode.
     */
    private void enterEdit() {
        saveButton.setText("Save");
        editArea.setVisible(true);
        cancelButton.setVisible(true);
        editArea.setText("");
    }
}
