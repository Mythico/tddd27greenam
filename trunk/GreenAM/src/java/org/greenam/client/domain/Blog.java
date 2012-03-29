/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;

/**
 *
 * @author Emil
 */
@Entity
public class Blog extends DatastoreObject {
     
    private String content;
    
    public Blog() {
    }
}
