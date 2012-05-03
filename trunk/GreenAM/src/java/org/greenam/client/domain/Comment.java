/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.Date;

/**
 *
 * @author Emil
 * @author Michael
 */
@Entity
public class Comment extends DatastoreObject {
     
    private String comment;
    private Long blogId;
    private Date date;
    private String name;
    
    public Comment(){
        
    }
    
    public Comment(String comment, Blog blog, String name) {
        this.comment = comment;
        this.date = new Date();
        blogId = blog.getId();
        this.name = name;
    }
    
    public String getEntry() {
        return comment;
    }
    
    public String getName() {
        return name;
    }
    
    public Long getBlogId() {
        return blogId;
    }
    
    public Date getDate() {
        return date;
    }
}