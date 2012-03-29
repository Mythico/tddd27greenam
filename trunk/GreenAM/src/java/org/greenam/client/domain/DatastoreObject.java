package org.greenam.client.domain;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.PrePersist;

/**
 * 
 * @author turbomange
 */
public class DatastoreObject implements Serializable
{
        
	@Id
	private Long id;
	private Integer version = 0;

	/**
	 * Auto-increment version # whenever persisted
	 */
	@PrePersist
	void onPersist()
	{
		this.version++;
	}
	// Getters and setters omitted

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }
        
        
}