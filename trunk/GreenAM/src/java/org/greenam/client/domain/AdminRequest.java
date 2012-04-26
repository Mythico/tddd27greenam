/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.NotSaved;

/**
 *
 * @author Emil
 */
@Entity
public class AdminRequest extends DatastoreObject{
    @NotSaved
    public static final int TYPE_NULL = 0;
    @NotSaved
    public static final int TYPE_ARTIST = 1;
    
    private int type;
    private Long userId;
    private String message;

    public AdminRequest() {
    }

    public AdminRequest(int type, Long userId, String message) {
        this.type = type;
        this.userId = userId;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }
    
    
}
