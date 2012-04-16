/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 *
 * @author Emil
 */
public class UploadWidget extends FormPanel{

    private FileUpload fileUpload = new FileUpload();
    private TextBox recordBox = new TextBox();
    private TextBox albumBox = new TextBox();
    private TextBox genreBox = new TextBox();
    private TextBox priceBox = new TextBox();
    
    private Button uploadButton = new Button("Upload");
    
    private Grid grid = new Grid(5, 2);
    
    public UploadWidget() {
        setEncoding(FormPanel.ENCODING_MULTIPART);
        setMethod(FormPanel.METHOD_POST);
        
        
        setAction("fileupload");
        
        grid.setHTML(0, 0, "Record:");
        grid.setHTML(1, 0, "Album:");
        grid.setHTML(2, 0, "Genre:");
        grid.setHTML(3, 0, "Price:");
        grid.setWidget(4, 0, uploadButton);
        
        grid.setWidget(0, 1, recordBox);
        grid.setWidget(1, 1, albumBox);
        grid.setWidget(2, 1, genreBox);
        grid.setWidget(3, 1, priceBox);
        grid.setWidget(4, 1, fileUpload);
        
        genreBox.setEnabled(false);
        
        fileUpload.setName("upload");
        
        uploadButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                submit();
            }
        });
        
        addSubmitCompleteHandler(new SubmitCompleteHandler() {

            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                Window.alert("Done " + event.getResults());
            }
        });
        
        addSubmitHandler(new SubmitHandler() {

            @Override
            public void onSubmit(SubmitEvent event) {
                Window.alert("Uploading: " + event.toDebugString());
            }
        });
        
        add(grid);
    }
    
    
    
}
