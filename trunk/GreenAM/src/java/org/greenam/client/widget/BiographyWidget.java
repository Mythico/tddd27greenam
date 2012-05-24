package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import org.greenam.client.domain.Artist;
import org.greenam.client.rpc.ArtistService;
import org.greenam.client.rpc.ArtistServiceAsync;
import org.greenam.client.view.ViewController;

/**
 * The biography widget is a simple widget that creates a text area
 * that can be edit if you are the owner.
 * 
 * @author Emil
 * @author Michael
 */
public class BiographyWidget extends BaseWidget {

    private final RichTextArea editArea = new RichTextArea();
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final Button saveButton = new Button("Edit");
    private final Button cancelButton = new Button("Cancel");
    private final Label bioLabel = new Label();

    /**
     * Creates a biography widget.
     * @param viewController A link to the view controller.
     */
    public BiographyWidget(final ViewController viewController) {
        super(viewController);
        setSize("100%", "100%");

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

        add(bioLabel);
        add(editArea);
        add(buttonPanel);

    }

    /**
     * Save fetches the data from the edit area and send it to the server.
     */
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
                setError("Could not save biography.", caught.getLocalizedMessage());
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
