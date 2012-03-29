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

/**
 *
 * @author Emil
 */
public class BiographyWidget extends VerticalPanel {

    private final RichTextArea textArea = new RichTextArea();
    private final HorizontalPanel editPane = new HorizontalPanel();
    private final ArtistServiceAsync async = GWT.create(ArtistService.class);
    private Artist artist;

    public BiographyWidget() {
        setSize("100%", "100%");
        textArea.setEnabled(false);

        final Button saveButton = new Button("Edit");
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

        async.save(artist, new AsyncCallback<Void>() {

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


        async.update(artist, new AsyncCallback<Artist>() {

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

        //Check if current login user has access to edit this page.
        boolean hasAccess = true;
        editPane.setVisible(hasAccess);

        load();
    }
}
