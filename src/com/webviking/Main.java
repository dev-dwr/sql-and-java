package com.webviking;

import com.webviking.model.Artist;
import com.webviking.model.DataSource;
import com.webviking.model.SongArtist;


import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if (!dataSource.open()) {
            System.out.println("Can't open data source");
        }

        List<Artist> artists = dataSource.queryArtist(DataSource.ORDER_BY_DESC);
        if (artists != null) {
            for (Artist artist : artists) {
                System.out.println("ID: " + artist.getId() + ", name: " + artist.getName());
            }
        } else {
            System.out.println("There are no artists");
            return;
        }
        List<String> albumsForArtist = dataSource.queryAlbumsForArtist("Pink Floyd", DataSource.ORDER_BY_ASC);
        for (String album : albumsForArtist) {
            System.out.println(album);
        }

        List<SongArtist> songArtists = dataSource.queryArtistsForSong("Heartless", DataSource.ORDER_BY_ASC);
        if (songArtists == null) {
            System.out.println("Could't find the artist for the song");
        } else {
            for (SongArtist songArtist : songArtists) {
                System.out.println("Artist name: " + songArtist.getArtistName() + ", Album name: " +
                        songArtist.getAlbumName() + ", Track: " + songArtist.getTrack());
            }
        }

        dataSource.querySongsMetadata();

        dataSource.createViewForSongArtist();

        int count = dataSource.getCount(DataSource.TABLE_SONGS);
        System.out.println("Number for songs " + count);


        Scanner sc = new Scanner(System.in);
        System.out.println("Enter song title: ");
        String title = sc.nextLine();

        songArtists = dataSource.querySongInfoView(title);
        if (songArtists.isEmpty()) {
            System.out.println("Colud't find artsits for song");
            return;
        } else {
            for (SongArtist songArtist : songArtists) {
                System.out.println("FROM VIEW - Artists name; " + songArtist.getArtistName() +
                        ", Album name: " + songArtist.getAlbumName() + ", track album: " + songArtist.getTrack());
            }
        }


        dataSource.insertSong("Like A Rolling Stone", "Bob Dylan", "Bob Dylan's Greatest Hits", 5 );
        dataSource.close();

    }
}
