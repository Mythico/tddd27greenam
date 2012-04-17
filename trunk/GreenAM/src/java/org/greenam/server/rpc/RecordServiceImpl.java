/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.RecordService;

/**
 *
 * @author Emil
 */
public class RecordServiceImpl extends RemoteServiceServlet implements RecordService {

    //TODO: Temp for creating starting records.
    private boolean createArtist = false;

    @Override
    public List<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();

        Objectify ofy = ObjectifyService.begin();

        if (createArtist) {
            ofy.delete(User.class, "1");
            ofy.put(new User("1", "1"));
            List<User> userList = ofy.query(User.class).list();
            for (User user : userList) {
                ofy.delete(Artist.class, user.getId());
                ofy.put(new Artist(user.getId(), user.getName()));
                //Note: The artist name dosn't have to be the same as the user name.
            }
            Query<Artist> alist = ofy.query(Artist.class).limit(10);

            for (Artist a : alist) {
                List<Long> al = new LinkedList<Long>();
                al.add(a.getId());
                list.add(new Record("Alban", al, 0, "sound/Rondo_Alla_Turka.ogg"));
            }
            createArtist = false;
        }
        return ofy.query(Record.class).list();
        
    }

    //Generate a Blobstore Upload URL from the GAE BlobstoreService
    @Override
    public String getBlobStoreUploadUrl() {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        //Map the UploadURL to the uploadservice which will be called by
        //submitting the FormPanel
        return blobstoreService.createUploadUrl("/http/fileupload");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));

        blobstoreService.serve(blobKey, resp);
    }
}
