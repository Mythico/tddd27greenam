/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.rpc.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author Emil
 */
@PersistenceCapable
public class User {
    
    @PrimaryKey
    @Persistent
    private String federatedId;
    
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    public User(String federatedId) {
        this.federatedId = federatedId;
    }
    
    public String getFederatedId() {
        return federatedId;
    }

    public void setFederatedId(String federatedId) {
        this.federatedId = federatedId;
    }

    public Long getId() {
        return id;
    }

    
    
}
