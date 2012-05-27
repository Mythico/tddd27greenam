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
import org.greenam.client.domain.Album;
import org.greenam.client.domain.Artist;
import org.greenam.client.domain.Record;

/**
 * Fileupload is a httpservlet that will upload music files to the blobstore
 * or serve them from it.
 * 
 * @author Emil
 * @author Michael
 */
public class FileUpload extends HttpServlet {

    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    /**
     * Fetches the data that is being uploaded from the upload widget. It will
     * create a new record, a new album if one didn't exists and store the
     * music file in the blobstore.
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        Objectify ofy = ObjectifyService.begin();

        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("upload");

        //Get the paramters from the request to populate the Record object
        String recordTitle = req.getParameter("recordBox");
        String albumTitle = req.getParameter("albumBox");
        List<Long> artists = parseStringToArtists(req.getParameter("artistBox"));
        int price = Integer.parseInt(req.getParameter("priceBox"));
        String blobKeyString = blobKey.getKeyString();
        
        //Create a record from the specified parameters
        Record record = new Record(recordTitle, artists, price, blobKeyString);
        Long recordId = ofy.put(record).getId();

        //Find the album that matches a specified title. Create one if none 
        //existed. Finnaly, add the recordid to the album.
        Album album = ofy.query(Album.class).filter("title", albumTitle).get();
        if (album == null) {
            album = new Album(albumTitle, recordId);
        } else {
            album.addRecord(recordId);
        }
        ofy.put(album);

        //Redirect back to the main page
        res.sendRedirect("/");
    }

    /**
     * Serves a blob from the blobstore that is coupled with a blob-key that
     * is send in the request.
     * @param req
     * @param res
     * @throws IOException 
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        if(hasAccess(blobKey)){
            blobstoreService.serve(blobKey, res);
        } else {
            res.sendError(403, "You have to buy the record to have access to it.");
        }
    }

    /**
     * All all the artists that belongs to a record that is being uploaded are
     * send in one string that has to be parsed. This function parse that string
     * and returns a list of artist ids that can be added to a record.
     * @param artists A string of artists name.
     * @return A list of artist ids.
     */
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
    
    /**
     * Checks if the current user has access to download this record.
     * @param key
     * @return True if the user has access, otherwise false.
     */
    private boolean hasAccess(BlobKey key){
        return true; //TODO: Implement this function.
    }
}