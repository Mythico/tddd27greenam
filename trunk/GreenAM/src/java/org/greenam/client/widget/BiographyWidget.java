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

    private final RichTextArea textArea = new RichTextArea();
    private final HorizontalPanel editPane = new HorizontalPanel();
    private final VerticalPanel BiografyArea = new VerticalPanel();
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final Button saveButton = new Button("Edit");
    private final Button cancelButton = new Button("Cancel");
    private final ViewController viewController;
    private final VerticalPanel vp = new VerticalPanel();

    public BiographyWidget(final ViewController viewController) {
        setSize("100%", "100%");

        this.viewController = viewController;

        vp.setStyleName("gam-Box");
                
        BiografyArea.add(vp);
        textArea.setEnabled(true);
        textArea.setVisible(false);
        textArea.setStyleName("gam-Textbox");
        cancelButton.setVisible(false);

        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (cancelButton.isVisible()) {
                    save();
                    saveButton.setText("Edit");
                    textArea.setVisible(false);
                    cancelButton.setVisible(false);
                } else {
                    saveButton.setText("Save");
                    textArea.setVisible(true);
                    cancelButton.setVisible(true);
                    textArea.setHTML(viewController.getArtist().getBiography());
                }
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                saveButton.setText("Edit");

                textArea.setVisible(false);
                vp.clear();
                vp.add(new Label(viewController.getArtist().getBiography()));
                textArea.setText("");
                cancelButton.setVisible(false);
            }
        });

        editPane.add(saveButton);
        editPane.add(cancelButton);
        
        setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        add(BiografyArea);
        add(textArea);
        add(editPane);

    }

    private void save() {
        if (!viewController.hasAccess()) {
            Window.alert("You are trying to save an artist biography without"
                    + " having the correct access.");
            return;
        }

        Artist artist = viewController.getArtist();
        artist.setBiography(textArea.getText());

        
        artistInfo.save(artist, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BiographyWidget failed RPC on save.\n" + caught);
            }

            @Override
            public void onSuccess(Void result) {
                //Do nothing
            }
        });
        
        vp.clear();
        vp.add(new Label(viewController.getArtist().getBiography()));
        
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            editPane.setVisible(viewController.hasAccess());
            saveButton.setText("Edit");
            vp.clear();
            vp.add(new Label(viewController.getArtist().getBiography()));
            textArea.setText("");
            
            cancelButton.setVisible(false);
            textArea.setHTML(viewController.getArtist().getBiography());
        }
    }
}
