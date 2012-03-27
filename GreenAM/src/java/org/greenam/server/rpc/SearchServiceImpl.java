/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.greenam.client.rpc.SearchService;
import org.greenam.client.rpc.jdo.Album;
import org.greenam.client.rpc.jdo.Artist;
import org.greenam.client.rpc.jdo.Record;
import org.greenam.client.rpc.jdo.User;

/**
 *
 * @author Emil
 */
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

    private boolean createArtist = true;

    @Override
    public Collection<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();
        PersistenceManager pm = PMF.get().getPersistenceManager();
        if (createArtist) {

            //Add temporary artists
            try {
                User usr1 = pm.makePersistent(new User("1"));
                User usr2 = pm.makePersistent(new User("2"));
                User usr3 = pm.makePersistent(new User("3"));
                User usr4 = pm.makePersistent(new User("4"));
                pm.makePersistent(new Artist(usr1, "Allsong", "", null, null, null));
                pm.makePersistent(new Artist(usr2, "Berit", "", null, null, null));
                pm.makePersistent(new Artist(usr3, "Alban", "", null, null, null));
                pm.makePersistent(new Artist(usr4, "Tycke", "", null, null, null));
                createArtist = false;
            } finally {
                pm.close();
            }
            pm = PMF.get().getPersistenceManager();
        }
        List<Artist> alist = null;
        try {
            Query query = pm.newQuery(Artist.class);
            alist = (List<Artist>) query.execute();
            alist.size(); // To load lasy fetch objects.
        } finally {
            pm.close();
        }
        System.out.println("a size:" + alist.size());
        for (Artist a : alist) {
            list.add(new Record("Alban", new Album(), a, 0));
        }
        return list;
    }
    /*
     * @Override public LinkedList<Package> search(String name, Package table) {
     * PersistenceManager pm = PMF.get().getPersistenceManager(); List<Package>
     * list = null;
     *
     * try { Query query = pm.newQuery(table.get().getClass());
     * query.setFilter("name == value"); query.declareParameters("String
     * value"); list = (List<Package>) query.execute(name); list.size(); // To
     * load lasy fetch objects. } finally { pm.close(); } return new
     * LinkedList<Package>(list); }
     *
     * @Override public Package search( Long id, Package table) {
     * PersistenceManager pm = PMF.get().getPersistenceManager(); Package type =
     * null; try { type = new Package(pm.getObjectById(table.type(), id)); }
     * finally { pm.close(); }
     *
     * return type; }
     *
     * @Override public Collection<Package> search(Collection<Long> ids, Package
     * table){ LinkedList list = new LinkedList(); for(Long id : ids){
     * list.add(search(id, table)); } return list; } *
     */
}
