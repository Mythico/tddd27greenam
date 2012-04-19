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
import java.util.HashMap;
import java.util.HashSet;
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

    @Override
    public List<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();

        Objectify ofy = ObjectifyService.begin();

        List<Record> records = new LinkedList<Record>();
        String[] queries = s.split(",");
        for (String query : queries) {
            String[] parts = query.trim().split(":", 2);
            if (parts.length == 1) {
                records.addAll(searchTitle(parts[0].trim()));
            } else if (parts[0].equalsIgnoreCase("artistId")) {
                records.addAll(searchArtist(Long.parseLong(parts[1].trim())));
            } else if (parts[0].equalsIgnoreCase("artist")) {
                records.addAll(searchArtist(parts[1].trim()));
            }
        }

        return records;

    }

    private List<Record> searchTitle(String s) {

        Objectify ofy = ObjectifyService.begin();
        return ofy.query(Record.class).filter("title", s).list();
    }

    private List<Record> searchArtist(String s) {

        Objectify ofy = ObjectifyService.begin();
        List<Record> records = new LinkedList<Record>();
        for (Artist artist : ofy.query(Artist.class).filter("name", s)) {
            records.addAll(ofy.query(Record.class).filter("artistId", artist).list());
        }
        return records;
    }

    private List<Record> searchArtist(Long artistId) {

        Objectify ofy = ObjectifyService.begin();
        List<Record> records = new LinkedList<Record>();
        records.addAll(ofy.query(Record.class).filter("artistIds", artistId).list());
        return records;
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
    public List<Album> getAlbums(Artist artist) {
        Objectify ofy = ObjectifyService.begin();
        List<Album> list = ofy.query(Album.class).filter("artistIds in", artist.getId()).list();
        return list;
    }

    @Override
    public List<LinkObject<String>> getAlbumNamesFromRecords(List<Long> recordIds) {

        Objectify ofy = ObjectifyService.begin();
        List<LinkObject<String>> list = new LinkedList<LinkObject<String>>();

        for (Long id : recordIds) {
            for (Album album : ofy.query(Album.class).filter("recordIds", id)) {
                list.add(new LinkObject<String>(id, album.getId(), album.getTitle()));
            }
        }
        return list;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));

        blobstoreService.serve(blobKey, resp);
    }
}
