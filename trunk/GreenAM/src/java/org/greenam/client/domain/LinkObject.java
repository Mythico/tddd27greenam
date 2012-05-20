package org.greenam.client.domain;

import java.io.Serializable;

/**
 * A link object is an object that links an id with an id of another object and
 * a part of that object.
 * 
 * Example: Artist id links to and album id and that albums title.
 * 
 * @author Emil
 * @author Michael
 */
public class LinkObject<T> implements Serializable{
    
    private Long linkId;
    private Long objectId;
    private T object;

    public LinkObject() {
    }

    /**
     * Create a link object with a link id, object id and an object.
     * @param linkId A link id.
     * @param objectId An object id.
     * @param object An object.
     */
    public LinkObject(Long linkId, Long objectId, T object) {
        this.linkId = linkId;
        this.objectId = objectId;
        this.object = object;
    }
    
    /**
     * Get the link id.
     * @return A link id.
     */
    public Long getLinkId() {
        return linkId;
    }

    /**
     * Get the object that is being linked.
     * @return An object.
     */
    public T getObject() {
        return object;
    }

    /**
     * Get the id that is being linked.
     * @return An object id.
     */
    public Long getObjectId() {
        return objectId;
    }
    
}
