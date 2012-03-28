/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 *
 * @author Emil
 */
public class ClientFactory {

    private final ApplicationRequestFactory requestFactory = GWT.create(ApplicationRequestFactory.class);
    private EventBus eventBus;
    private static ClientFactory singelton = null;

    public  ClientFactory() {
        eventBus = new SimpleEventBus();
        requestFactory.initialize(eventBus);
    }
   
    public static ApplicationRequestFactory getRequestFactory() {
        if(singelton == null){
            singelton = new ClientFactory();
        }
        return singelton.requestFactory;
    }
}
