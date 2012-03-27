/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
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
    public List<Record> search(String s) {
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

        for (Artist a : alist) {
            list.add(new Record("Alban", "Ballen", a.getId(), 0));
        }
        return list;
    }

    @Override
    public List<String> searchForTitlesBeginingWith(String s) {

        LinkedList<String> list = new LinkedList<String>();



        return list;
    }

    @Override
    public List<Record> searchArtist(Long id) {
        return searchNonKey("artistId", id.longValue() + "", Record.class);
    }

    @Override
    public List<Record> searchArtist(String name) {
        return searchNonKey("name", name, Record.class);
    }

    @Override
    public Record searchTitle(Long id) {
        return searchKey(id, Record.class);
    }

    @Override
    public List<Record> searchTitle(String name) {
        return searchNonKey("name", name, Record.class);
    }

    @Override
    public Album searchAlbum(Long id) {
        return searchKey(id, Album.class);
    }

    @Override
    public List<Album> searchAlbum(String name) {
        return searchNonKey("name", name, Album.class);
    }

    @Override
    public List<Record> searchGenre(int genre) {
        return searchNonKey("genre", "" + genre, Record.class);
    }

    private <T> T searchKey(Long id, Class<T> cls) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        T type = null;
        try {
            type = pm.getObjectById(cls, id);

        } finally {
            pm.close();
        }
        return type;
    }

    private <T> List<T> searchNonKey(String property, String name, Class<T> cls) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<T> list = null;
        try {
            Query query = pm.newQuery(cls, property + " == '" + name + "'");
            list = (List<T>) query.execute();
            list.size(); // To load lasy fetch objects.
        } finally {
            pm.close();
        }
        return list;
    }
}