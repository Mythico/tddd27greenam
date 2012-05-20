package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.List;

/**
 * A Record is a datastore object containing a title, a list of artists that
 * created the record, a price of the record and a key to finding the music file
 * in the blobstore.
 * 
 * @author Emil
 * @author Michael
 */
@Entity
public class Record extends DatastoreObject {

    private String title;
    private List<Long> artistIds;
    private int price;
    private String blobKey;

    public Record() {
    }

    /**
     * Create a record with a title, a list of artists, a price and a key
     * to the music file.
     * @param title A title.
     * @param artistIds A list of of artist ids that created this record.
     * @param price A price.
     * @param blobKey A key used to find the music file in the blobstore.
     */
    public Record(String title,
            List<Long> artistIds, int price, String blobKey) {
        this.title = title;
        this.artistIds = artistIds;
        this.price = price;
        this.blobKey = blobKey;
    }

    /**
     * Get the list of artist ids.
     * @return A list of artist ids.
     */
    public List<Long> getArtistIds() {
        return artistIds;
    }

    /**
     * Get the price of this record.
     * @return A price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Get the title of the record.
     * @return A title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the blob key.
     * @return A blob key.
     */
    public String getBlobKey() {
        return blobKey;
    }
}
