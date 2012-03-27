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
import org.greenam.client.rpc.AccessService;
import org.greenam.client.rpc.AccessServiceAsync;
import org.greenam.client.rpc.ArtistInfoService;
import org.greenam.client.rpc.ArtistInfoServiceAsync;

/**
 *
 * @author Emil
 */
public class TextWidget extends VerticalPanel {

    private final RichTextArea textArea = new RichTextArea();
    private final HorizontalPanel editPane = new HorizontalPanel();
    private final ArtistInfoServiceAsync async = GWT.create(ArtistInfoService.class);
    private final AccessServiceAsync accessAsync = GWT.create(AccessService.class);
    private Long artistId;

    public TextWidget() {
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
                saveButton.setText("Edit");
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
        accessAsync.hasAccess(new AsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Boolean result) {
                if (result = true)
                    save(result);
                else
                    Window.alert("You do not have permission to do that!");
            }
        });
    }
    
    private void save(boolean b) {
        if(!b){
            return;
        }
    

        String content = textArea.getHTML();

        //Mabey change artistId for something else like UserInfo?
        async.postBiography(artistId, content, new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
                //Access denied
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Object result) {/* Do nothing*/}
        });

    }

    private void load() {
        
        //TODO: Mabey add something to the constructor so we can reuse this Widget.
        
        async.getBiogarphy(artistId, new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.toString());
            }

            @Override
            public void onSuccess(String result) {
                textArea.setHTML(result);
            }
        });
    }
    
    
    public void setArtist(Long id){
        artistId = id;        
        
        //Check if current login user has access to edit this page.
        boolean hasAccess = true;
        editPane.setVisible(hasAccess);
        
        load();
    }
}
