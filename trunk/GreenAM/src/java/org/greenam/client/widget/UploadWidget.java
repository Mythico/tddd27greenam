/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import org.greenam.client.rpc.RecordService;
import org.greenam.client.rpc.RecordServiceAsync;
import org.greenam.client.view.ViewController;


/**
 *
 * @author Emil
 */
public class UploadWidget extends FormPanel {
    private final RecordServiceAsync recordInfo = GWT.create(RecordService.class);
    private FileUpload fileUpload = new FileUpload();
    private TextBox artistsBox = new TextBox();
    private TextBox recordBox = new TextBox();
    private TextBox albumBox = new TextBox();
    private TextBox priceBox = new TextBox();
    private Button uploadButton = new Button("Upload");
    private Grid grid = new Grid(5, 2);
    private final ViewController viewController;

    public UploadWidget(final ViewController viewController) {
        
        this.viewController = viewController;
        
        setEncoding(FormPanel.ENCODING_MULTIPART);
        setMethod(FormPanel.METHOD_POST);



        grid.setHTML(0, 0, "Additional artists:");
        grid.setHTML(1, 0, "Record:");
        grid.setHTML(2, 0, "Album:");
        grid.setHTML(3, 0, "Price:");
        grid.setWidget(4, 0, uploadButton);

        grid.setWidget(0, 1, artistsBox);
        grid.setWidget(1, 1, recordBox);
        grid.setWidget(2, 1, albumBox);
        grid.setWidget(3, 1, priceBox);
        grid.setWidget(5, 1, fileUpload);

        recordBox.setName("recordBox");
        albumBox.setName("albumBox");
        artistsBox.setName("artistBox");
        priceBox.setName("priceBox");
        fileUpload.setName("upload");

        uploadButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                recordInfo.getBlobStoreUploadUrl(new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    @Override
                    public void onSuccess(String result) {
                        String name = viewController.getArtist().getName();
                        String additionalNames = artistsBox.getText();
                        artistsBox.setName(name + "," + additionalNames);
                        setAction(result);
                        submit();
                        reset();
                    }
                });
            }
        });

        add(grid);
    }
}
