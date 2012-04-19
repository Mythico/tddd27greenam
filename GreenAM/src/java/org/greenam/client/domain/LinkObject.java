/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import java.io.Serializable;

/**
 * 
 * @author Emil
 */
public class LinkObject<T> implements Serializable{
    
    private Long linkId;
    private Long objectId;
    private T object;

    public LinkObject() {
    }

    public LinkObject(Long linkId, Long objectId, T object) {
        this.linkId = linkId;
        this.objectId = objectId;
        this.object = object;
    }
    
    

    public Long getLinkId() {
        return linkId;
    }

    public T getObject() {
        return object;
    }

    public Long getObjectId() {
        return objectId;
    }
    
    
    
}
