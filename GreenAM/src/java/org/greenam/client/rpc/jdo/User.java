/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author Emil
 */
@PersistenceCapable
public class User implements Ijdo, Serializable{
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent
    private String federatedId;

    public User() {
    }    
    

    public User(String federatedId) {
        this.federatedId = federatedId;
    }
    
    public String getFederatedId() {
        return federatedId;
    }

    public Long getId() {
        return id;
    }

    
    
}
