/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
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
public class RecordServiceImpl extends ServiceImpl implements RecordService {

    @Override
    public List<Record> search(String s) {

        List<Record> records = new LinkedList<Record>();
        String[] queries = s.split(",");
        for (String query : queries) {
            String[] parts = query.trim().split(":", 2);
            if (parts.length == 1) {
                records.addAll(searchTitle(parts[0].trim()));
            } else if (parts[0].trim().equalsIgnoreCase("artistId")) {
                records.addAll(searchArtist(Long.parseLong(parts[1].trim())));
            } else if (parts[0].trim().equalsIgnoreCase("artist")) {
                records.addAll(searchArtist(parts[1].trim()));
            }
        }

        return records;

    }

    private List<Record> searchTitle(String s) {

        Objectify ofy = ObjectifyService.begin();
        return ofy.query(Record.class).filter("title >=", s).filter("title <", s + "\uFFFD").list();
    }

    private List<Record> searchArtist(String s) {

        Objectify ofy = ObjectifyService.begin();
        List<Record> records = new LinkedList<Record>();
        Query<Artist> query = ofy.query(Artist.class);

        query = query.filter("name >=", s).filter("name <", s + "\uFFFD");
        for (Artist artist : query) {
            Query<Record> rq = ofy.query(Record.class);
            List<Record> result = rq.filter("artistIds", artist.getId()).list();
            records.addAll(result);
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
        List<Album> list = ofy.query(Album.class).filter("artistIds", artist.getId()).list();
        return list;
    }
    
    @Override
    public List<Record> getRecords(List<Long> recordIds){        
        Objectify ofy = ObjectifyService.begin();
        List<Record> list = ofy.query(Record.class).filter("id in", recordIds).list();
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

    /**
     * Tries to buy a record for the current user. It will throw an error if the
     * user doesn't have enough money.
     *
     * @param record A record to be bought.
     * @return User An updated user
     */
    @Override
    public User buyRecord(Record record) {
        if (!isLogin()) {
            throw new AccessException("You have to login to be able to buy records.");
        }

        Objectify ofy = ObjectifyService.begin();
        User user = getCurrentUser();
        int price = record.getPrice();
        if (user.getMoney() < price) {
            //TODO: Add an error instead of returning null, not enough money
            return null;
        }
        user.addMoney(-price);
        user.addBoughtRecord(record);
        save(user);

        List<Long> artistIds = record.getArtistIds();
        for (Artist artist : ofy.get(Artist.class, artistIds).values()) {
            user = ofy.get(User.class, artist.getUserId());
            user.addMoney(price / artistIds.size());
            save(user);
        }
        return user;
    }
}
