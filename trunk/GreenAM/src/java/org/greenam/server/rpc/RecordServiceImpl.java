/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import java.util.LinkedList;
import java.util.List;
import org.greenam.client.domain.*;

import org.greenam.client.rpc.RecordService;

/**
 *
 * @author Emil
 */
public class RecordServiceImpl extends RemoteServiceServlet implements RecordService {

    //TODO: Temp for creating starting records.
    private boolean createArtist = true;

    @Override
    public List<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();

        Objectify ofy = ObjectifyService.begin();

        if (createArtist) {
            ofy.delete(User.class, "1");
            ofy.put(new User("1", "1"));
            List<User> userList = ofy.query(User.class).list();
            for (User user : userList) {
                ofy.delete(Artist.class, user.getId());
                ofy.put(new Artist(user.getId(), user.getName()));
                //Note: The artist name dosn't have to be the same as the user name.
            }
            createArtist = false;
        }
        Query<Artist> alist = ofy.query(Artist.class).limit(10);

        for (Artist a : alist) {
            List<Long> al = new LinkedList<Long>();
            al.add(a.getId());
            Record record = new Record("Alban", al, 0, "sound/Rondo_Alla_Turka.ogg");
            ofy.put(record);
            list.add(record);
        }

        return ofy.query(Record.class).limit(20).list();

    }
}
