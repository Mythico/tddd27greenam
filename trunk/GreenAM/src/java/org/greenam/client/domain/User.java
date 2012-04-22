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
    private List<Long> boughtRecordIds;

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
    
    public List<Long> getBoughtRecords(){
        return boughtRecordIds;
    }
    
    public void addBoughtRecord(Record record){
        addBoughtRecord(record.getId());
    }
    
    public void addBoughtRecord(Long recordId){
        if(boughtRecordIds == null){
            boughtRecordIds = new LinkedList<Long>();
        }
        boughtRecordIds.add(recordId);
    }
}
