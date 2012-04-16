/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Emil
 */
@Entity
public class User extends DatastoreObject {

    @NotNull
    private String federatedId;
    @NotNull
    private String name;
    @NotNull
    private Long money;

    public User() {
    }

    public User(String federatedId, String name) {
        this.federatedId = federatedId;
        this.name = name;
        this.money = 0l;
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
    
    public void addMoney(Long diff){
        money += diff;        
    }
    
    public Long getMoney(){
        return money;
    }
}
