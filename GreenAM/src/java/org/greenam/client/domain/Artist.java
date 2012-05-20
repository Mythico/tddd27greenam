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
     * Create an artist from a user.
     * @param userId A user id.
     */
    public Artist(Long userId) {
        this.userId = userId;
        this.name = "Artist [" + getId() + "]";
        this.biography = "";
    }

    /**
     * Get the user id of this artist.
     * @return A user id.
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * Get this artist biography.
     * @return A biography.
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Set this artist biography.
     * @param biography A biography.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Get the artists name.
     * @return A name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the artists name.
     * @param name A name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
