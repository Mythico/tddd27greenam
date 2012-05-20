package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;

/**
 * Artist is a datastore object that stores a user id, a name and a biography.
 * @author Emil
 * @author Michael
 */
@Entity
public class Artist extends DatastoreObject{

    private Long userId;
    private String name;
    private String biography;
    
    public Artist() {
    }

    /**
     * Creates an artist from a user.
     * @param userId A user id.
     */
    public Artist(Long userId) {
        this.userId = userId;
        this.name = "Artist [" + getId() + "]";
        this.biography = "";
    }

    /**
     * Gets the user id of this artist.
     * @return A user id.
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * Gets this artist biography.
     * @return A biography.
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets this artist biography.
     * @param biography A biography.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Gets the artists name.
     * @return A name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the artists name.
     * @param name A name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
