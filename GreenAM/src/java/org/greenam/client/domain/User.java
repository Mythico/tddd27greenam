/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.client.domain;

import com.googlecode.objectify.annotation.Entity;
import java.util.LinkedList;
import java.util.List;
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
    private int money;
    @NotNull
    private List<Record> boughtRecords;

    public User() {
    }

    public User(String federatedId, String name) {
        this.federatedId = federatedId;
        this.name = name;
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
    
    public void addMoney(int diff){
        money += diff;        
    }
    
    public int getMoney(){
        return money;
    }
    
    public List<Record> getBoughtRecords(){
        return boughtRecords;
    }
    
    public void addBoughtRecord(Record record){
        if(boughtRecords == null){
            boughtRecords = new LinkedList<Record>();
        }
        boughtRecords.add(record);
    }
}
