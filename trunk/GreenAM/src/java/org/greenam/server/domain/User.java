/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.domain;

import com.googlecode.objectify.annotation.Entity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Emil
 */
@Entity
public class User extends DatastoreObject{
    
        
    @NotNull
    private String federatedId;
    private String name;

    public User(String federatedId, String name) {
        this.federatedId = federatedId;
    }
    
    public String getFederatedId() {
        return federatedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
