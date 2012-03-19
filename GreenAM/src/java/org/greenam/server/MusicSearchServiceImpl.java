/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.greenam.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.LinkedList;

import org.greenam.client.rpc.MusicSearchService;
import org.greenam.client.rpc.Record;

/**
 *
 * @author Emil
 */
public class MusicSearchServiceImpl extends RemoteServiceServlet implements MusicSearchService {

    String[] tempTitles = {"Allsong", "Alban", "Berit", "Tycke"};
    Record[] tempRecords = {new Record("Allsong", 0, "s1", 0, "Per", 0, 0, null),
        new Record("Alban", 1, "s1", 0, "Per", 0, 0, null),
        new Record("Berit", 2, "s2", 1, "Agust", 1, 0, null),
        new Record("Tycke", 3, "s3", 2, "Gunnar", 2, 0, null),};

    public LinkedList<Record> search(String s) {
        LinkedList<Record> list = new LinkedList<Record>();

        String[] parts = s.split(",");

        for (String part : parts) {
            //TODO: Add more searchoptions
            for (Record record : tempRecords) {
                String title = record.getTitle().toLowerCase();
                if (title.startsWith(part.trim().toLowerCase())) {
                    list.add(record);
                }
            }
        }

        return list;
    }

    @Override
    public LinkedList<String> searchForTitlesBeginingWith(String s) {

        LinkedList<String> list = new LinkedList<String>();

        //TODO: parse s and the result from the query to the list.
        for (String title : tempTitles) {
            if (title.toLowerCase().startsWith(s.toLowerCase())) {
                list.add(title);
            }
        }

        return list;
    }

    public LinkedList<Record> searchArtist(int artistId) {        
        LinkedList<Record> list = new LinkedList<Record>();
        
        for (Record record : tempRecords) {
            if (record.getArtistId() == artistId){
                list.add(record);
            }
        }
        
        return list;
    }

    public LinkedList<Record> searchArtist(String name) {
        LinkedList<Record> list = new LinkedList<Record>();
        
        for (Record record : tempRecords) {
            if (record.getArtist().equalsIgnoreCase(name)){
                list.add(record);
            }
        }
        
        return list;
    }

    public LinkedList<Record> searchTitle(int titleId) {
        LinkedList<Record> list = new LinkedList<Record>();
        
        for (Record record : tempRecords) {
            if (record.getTitleId() == titleId){
                list.add(record);
            }
        }
        
        return list;
    }

    public LinkedList<Record> searchTitle(String title) {
        LinkedList<Record> list = new LinkedList<Record>();
        
        for (Record record : tempRecords) {
            if (record.getTitle().equalsIgnoreCase(title)){
                list.add(record);
            }
        }
        
        return list;
    }

    public LinkedList<Record> searchAlbum(int albumId) {
        LinkedList<Record> list = new LinkedList<Record>();
        
        for (Record record : tempRecords) {
            if (record.getAlbumId() == albumId){
                list.add(record);
            }
        }
        
        return list;
    }

    public LinkedList<Record> searchAlbum(String album) {
        LinkedList<Record> list = new LinkedList<Record>();
        
        for (Record record : tempRecords) {
            if (record.getAlbum().equalsIgnoreCase(album)){
                list.add(record);
            }
        }
        
        return list;
    }

    public LinkedList<Record> searchGenre(int genre) {
        LinkedList<Record> list = new LinkedList<Record>();
        
        for (Record record : tempRecords) {
            if (record.getGenre() == genre){
                list.add(record);
            }
        }
        
        return list;
    }

    public LinkedList<Record> searchGenre(String genre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
