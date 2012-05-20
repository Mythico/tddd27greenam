package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Date;

/**
 * A Comment is a datastore object that contains a posting date, a name of the
 * poster, a blog id for blog this Comment belongs to and an entry.
 * @author Emil
 * @author Michael
 */
@Entity
public class Comment extends DatastoreObject {
     
    private String entry;
    private Long blogId;
    private Date date;
    private String name;
    
    public Comment(){
        
    }
    
    /**
     * Creates a Comment with an entry, blog this comment was posted on and
     * the name of the poster.
     * @param entry An entry.
     * @param blog A blog.
     * @param name A name.
     */
    public Comment(String entry, Blog blog, String name) {
        this.entry = entry;
        this.date = new Date();
        blogId = blog.getId();
        this.name = name;
    }
    
    /**
     * Gets the entry.
     * @return An entry.
     */
    public String getEntry() {
        return entry;
    }
    
    /**
     * Gets the name of the user that posted the comment.
     * @return A name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the blog id this comment was posted on.
     * @return A blog id.
     */
    public Long getBlogId() {
        return blogId;
    }
    
    /**
     * Gets the date this comment was posted on.
     * @return A date.
     */
    public Date getDate() {
        return date;
    }
}