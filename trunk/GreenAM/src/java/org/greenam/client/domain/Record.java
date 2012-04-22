/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import java.util.List;

/**
 *
 * @author Emil
 */
@Entity
public class Record extends DatastoreObject {

    private String title;
    private List<Long> artistIds;
    private int price;
    private String blobKey;

    public Record() {
    }

    public Record(String title,
            List<Long> artistIds, int price, String blobKey) {
        this.title = title;
        this.artistIds = artistIds;
        this.price = price;
        this.blobKey = blobKey;
    }

    public List<Long> getArtistIds() {
        return artistIds;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getBlobKey() {
        return blobKey;
    }
}
