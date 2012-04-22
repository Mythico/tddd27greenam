/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.http;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.Key;
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
import org.greenam.client.domain.Album;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Record;

public class FileUpload extends HttpServlet {

    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Objectify ofy = ObjectifyService.begin();

        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("upload");

        //Get the paramters from the request to populate the Record object
        String recordTitle = req.getParameter("albumBox");
        String albumTitle = req.getParameter("albumBox");
        List<Long> artists = parseStringToArtists(req.getParameter("artistBox"));
        int price = Integer.parseInt(req.getParameter("priceBox"));
        String blobKeyString = blobKey.getKeyString();
        Record record = new Record(recordTitle, artists, price, blobKeyString);
        Long recordId = ofy.put(record).getId();


        Album album = ofy.query(Album.class).filter("title", albumTitle).get();
        if (album == null) {
            album = new Album(albumTitle, artists);
        }
        album.addRecord(recordId);


        ofy.put(album);

        //Redirect recursively to this servlet (calls doGet)
        //res.sendRedirect("/http/fileupload?id=" + record.getId());
        if (blobKey == null) {
            res.sendRedirect("/");
        } else {
            res.sendRedirect("/http/fileupload?blob-key=" + blobKey.getKeyString());
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        blobstoreService.serve(blobKey, res);
    }

    private List<Long> parseStringToArtists(String artists) {
        Objectify ofy = ObjectifyService.begin();

        List<Long> list = new LinkedList<Long>();
        for (String artist : artists.split(",")) {
            Artist get = ofy.query(Artist.class).filter("name", artist.trim()).get();
            //TODO: throw error if no artist was found.
            list.add(get.getId());
        }

        return list;
    }
}