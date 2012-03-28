/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.util.LinkedList;
import java.util.List;
import org.greenam.server.domain.Artist;
import org.greenam.server.domain.Record;
import org.greenam.server.domain.User;

/**
 *
 * @author Emil
 */
public class RecordDao extends ObjectifyDao <Record>{

    
    //TODO: Temp for creating starting records.
    private boolean createArtist = true;

    public List<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();

        Objectify ofy = ObjectifyService.begin();

        if (createArtist) {
            ofy.put(new User("1","1"));
            ofy.put(new User("2","2"));
            ofy.put(new User("3","3"));
            ofy.put(new User("4","4"));
            createArtist = false;
        }
        List<Key<Artist>> alist = ofy.query(Artist.class).listKeys();
        for (Key<Artist> a : alist) {
            List<Long> al = new LinkedList<Long>();
            al.add(a.getId());
            list.add(new Record("Alban", al, 0));
        }
        return list;
    }
}
