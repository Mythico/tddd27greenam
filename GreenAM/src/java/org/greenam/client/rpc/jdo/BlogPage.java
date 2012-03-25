/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import java.io.Serializable;
import java.util.List;

/**
 * A wrapper class for receiving a page with a couple of blogs from the server.
 * @author Emil
 */
public class BlogPage implements Serializable{

    public int SIZE = 4;
    public List<Blog> blogs;
    
    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
    
}
