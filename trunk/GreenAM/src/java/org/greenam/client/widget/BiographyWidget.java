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

/**
 *
 * @author Emil
 */
public class BiographyWidget extends VerticalPanel {

    private final RichTextArea textArea = new RichTextArea();
    private final HorizontalPanel editPane = new HorizontalPanel();
    private final ArtistServiceAsync artistInfo = GWT.create(ArtistService.class);
    private final UserServiceAsync userInfo = GWT.create(UserService.class);
    private Artist artist;
    private final Button saveButton = new Button("Edit");

    public BiographyWidget() {
        setSize("100%", "100%");
        textArea.setEnabled(false);

        final Button cancelButton = new Button("Cancel");
        cancelButton.setVisible(false);

        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (cancelButton.isVisible()) {
                    save();
                    saveButton.setText("Edit");
                    textArea.setEnabled(false);
                    cancelButton.setVisible(false);
                } else {
                    saveButton.setText("Save");
                    textArea.setEnabled(true);
                    cancelButton.setVisible(true);
                }
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                load();
                saveButton.setText("Edit");
                textArea.setEnabled(false);
                cancelButton.setVisible(false);
            }
        });


        editPane.add(saveButton);
        editPane.add(cancelButton);

        setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        add(textArea);
        add(editPane);

    }

    private void save() {
        //TODO: Mabey add something to the constructor so we can reuse this Widget.

        //Check if current login user has access to edit this page.
        /*
         * accessAsync.hasAccess(artistId, new AsyncCallback<Boolean>() {
         *
         * @Override public void onFailure(Throwable caught) { throw new
         * UnsupportedOperationException("Not supported yet."); }
         *
         * @Override public void onSuccess(Boolean result) { save(result); } });
         */
        save(true);
    }

    private void save(boolean b) {
        if (!b) {
            return;
        }


        artist.setBiography(textArea.getHTML());

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
    }

    private void load() {


        artistInfo.update(artist, new AsyncCallback<Artist>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("BiographyWidget failed RPC on load.\n" + caught);
            }

            @Override
            public void onSuccess(Artist result) {
                artist = result;
                textArea.setHTML(artist.getBiography());
            }
        });
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        saveButton.setText("Edit");

        //Check if current login user has access to edit this page.
        editPane.setVisible(false);
        userInfo.hasAccess(artist.getId(), new AsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Has access failed in BiographWidget.\n" + caught);
            }

            @Override
            public void onSuccess(Boolean result) {
                editPane.setVisible(result);
            }
        });


        load();
    }
}
