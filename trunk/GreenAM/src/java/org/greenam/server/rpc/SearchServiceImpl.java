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
import javax.jdo.annotations.Transactional;
import org.greenam.client.rpc.SearchService;
import org.greenam.client.rpc.jdo.Album;
import org.greenam.client.rpc.jdo.Artist;
import org.greenam.client.rpc.jdo.Record;

/**
 *
 * @author Emil
 */
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

    @Override
    public List<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();
        List<Artist> alist = searchNonKey("name", s, Artist.class);

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
        return searchNonKey("artistId", id.toString(), Record.class);
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
