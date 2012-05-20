package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.NotSaved;

/**
 * The admin request is a datastore object witch is used by the client to send
 * messages to administrators.
 * 
 * @author Emil
 * @author Michael
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

    /**
     * Create an admin recuest with a type, an id of the user that created the
     * request and a message.
     * @param type A type.
     * @param userId An user id.
     * @param message A message
     */
    public AdminRequest(int type, Long userId, String message) {
        this.type = type;
        this.userId = userId;
        this.message = message;
    }

    /**
     * Get the message from the request.
     * @return A message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the type of this request.
     * @return A type.
     */
    public int getType() {
        return type;
    }

    /**
     * Get the user id of the user that created this request.
     * @return A user id.
     */
    public Long getUserId() {
        return userId;
    }
    
    
}
