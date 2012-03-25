/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import org.greenam.client.rpc.SearchService;
import org.greenam.client.rpc.jdo.Album;
import org.greenam.client.rpc.jdo.Artist;
import org.greenam.client.rpc.jdo.Record;

/**
 *
 * @author Emil
 */
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

    String[] tempTitles = {"Allsong", "Alban", "Berit", "Tycke"};
    
    
    
    Record[] tempRecords = {new Record(0,"Allsong", "s1", 0L, 0, null),
        new Record(1,"Alban", "s1", 0l, 0, null),
        new Record(2,"Berit", "s2", 1l, 0, null),
        new Record(3,"Tycke", "s3", 2l, 0, null),};

    @Override
    public List<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();

        String[] parts = s.split(",");

        for (String part : parts) {
            //TODO: Add more searchoptions
            for (Record record : tempRecords) {
                String title = record.title.toLowerCase();
                if (title.startsWith(part.trim().toLowerCase())) {
                    list.add(record);
                }
            }
        }

        return list;
    }

    @Override
    public List<String> searchForTitlesBeginingWith(String s) {

        LinkedList<String> list = new LinkedList<String>();

        //TODO: parse s and the result from the query to the list.
        for (String title : tempTitles) {
            if (title.toLowerCase().startsWith(s.toLowerCase())) {
                list.add(title);
            }
        }

        return list;
    }

    @Override
    public Record searchArtist(Long id) {      
        return searchKey(id, Record.class);
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
        return searchNonKey("genre", ""+genre, Record.class);
    }

    
    
    private <T> T searchKey(Long id, Class<T> cls){
        PersistenceManager pm = PMF.get().getPersistenceManager();
        T type = null;
        try{
            type = pm.getObjectById(cls, id);
            
        } finally{
            pm.close();
        }
        return type;
    }
    
    private <T> List<T> searchNonKey(String property, String name, Class<T> cls){
        PersistenceManager pm = PMF.get().getPersistenceManager();
        List<T> list = null;
        try{
            Query query = pm.newQuery(cls, property + " = " + name);
            list = (List<T>) query.execute();            
        } finally{
            pm.close();
        }
        return list;
    }
   
}
