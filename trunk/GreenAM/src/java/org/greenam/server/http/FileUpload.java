/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.http;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Record;

public class FileUpload extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        Objectify ofy = ObjectifyService.begin();
        
        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("upload");

        //Get the paramters from the request to populate the Record object
        String title = req.getParameter("titleBox");
        List<Long> artists = parseStringToArtists(req.getParameter("artistBox"));
        int genre = -1; //TODO: Add genre
        String url = "/rpc/recordservice?blob-key=" + blobKey.getKeyString();
        Record record = new Record(title, artists, genre, url);
        

        ofy.put(record);

        //Redirect recursively to this servlet (calls doGet)
        res.sendRedirect("/http/fileupload?id=" + record.getId());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //Send the meta-data id back to the client in the HttpServletResponse response
        String id = req.getParameter("id");
        resp.setHeader("Content-Type", "text/html");
        resp.getWriter().println(id);

    }
    
    private List<Long> parseStringToArtists(String artists){
        
        Objectify ofy = ObjectifyService.begin();
        //TODO: add support for more then one artist.
        Artist get = ofy.query(Artist.class).filter("name", artists).get();
        List<Long> list = new LinkedList<Long>();
        list.add(get.getId());
        return list;
    }
}