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
 * A user is a data store object containing a federatedid that is used with 
 * openid, a name, an amount of money and a list of bought records ids.
 * @author Emil
 * @author Michael
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

    /**
     * Create a user with a federaded id and a name.
     * @param federatedId An id used by openid.
     * @param name A name.
     */
    public User(String federatedId, String name) {
        this.federatedId = federatedId;
        this.name = name;
    }

    /**
     * Get the federated id of this user.
     * @return A federated id.
     */
    public String getFederatedId() {
        return federatedId;
    }

    /**
     * Get the name of this user.
     * @return A name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this user.
     * @param name A new name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Add money to the user account.
     * @param diff An amount of money to be added.
     */
    public void addMoney(int diff){
        money += diff;        
    }
    
    /**
     * Get the amount of money the user has.
     * @return An amount of money.
     */
    public int getMoney(){
        return money;
    }
    
    /**
     * Get a list of record ids that this user has bought.
     * @return A list of bought record ids.
     */
    public List<Long> getBoughtRecords(){
        return boughtRecordIds;
    }
    
    /**
     * Add a bought record to the list of bought records.
     * @param record A record.
     */
    public void addBoughtRecord(Record record){
        addBoughtRecord(record.getId());
    }
    
    /**
     * Add a bought record to the list of bought records.
     * @param record A record id.
     */
    public void addBoughtRecord(Long recordId){
        if(boughtRecordIds == null){
            boughtRecordIds = new LinkedList<Long>();
        }
        boughtRecordIds.add(recordId);
    }
}
