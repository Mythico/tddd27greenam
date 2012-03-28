/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import org.greenam.shared.proxy.ArtistProxy;
import org.greenam.shared.service.ApplicationRequestFactory;
import org.greenam.shared.service.ApplicationRequestFactory.UserRequestContext;
import org.greenam.shared.service.ClientFactory;

/**
 *
 * @author Emil
 */
public class BiographyWidget extends VerticalPanel {

    private final RichTextArea textArea = new RichTextArea();
    private final HorizontalPanel editPane = new HorizontalPanel();
    private final ApplicationRequestFactory rf = ClientFactory.getRequestFactory();

    private ArtistProxy artist;

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

    private void save(){
       //TODO: Mabey add something to the constructor so we can reuse this Widget.

        //Check if current login user has access to edit this page.
        /*accessAsync.hasAccess(artistId, new AsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Boolean result) {
                save(result);
            }
        }); */
        save(true);
    }
    
    private void save(boolean b) {
        if(!b){
            return;
        }
    

        artist.setBiography(textArea.getHTML());
        UserRequestContext reqCtx = rf.userRequest();
        ArtistProxy editArtist = reqCtx.edit(artist);
        
        reqCtx.saveArtist(editArtist).fire(new Receiver<Void>() {

            @Override
            public void onSuccess(Void response) {
            }
        });
    }

    private void load() {
        
        //TODO: Mabey add something to the constructor so we can reuse this Widget.
        
        UserRequestContext reqCtx = rf.userRequest();
        Request<ArtistProxy> bio = reqCtx.getArtist(artist.getId()).with("biography");
        
        bio.fire(new Receiver<ArtistProxy>() {

            @Override
            public void onSuccess(ArtistProxy response) {
                artist = response;
                textArea.setHTML(artist.getBiography());
            }
        });
    }
    
    
    public void setArtist(ArtistProxy artist){
        this.artist = artist;        
        
        //Check if current login user has access to edit this page.
        boolean hasAccess = true;
        editPane.setVisible(hasAccess);
        
        load();
    }
}
