package com.example.catalog.services;

import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.model.Track;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class SpotifyAPIDataSources implements DataSourceService {
    private final RestTemplate restTemplate;
    private final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";

    public SpotifyAPIDataSources(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer YOUR_ACCESS_TOKEN");
        return headers;
    }

    @Override
    public Artist getArtistById(String id) throws IOException {
        String url = SPOTIFY_API_BASE_URL + "/artists/" + id;
        ResponseEntity<Artist> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders()), Artist.class);
        return response.getBody();
    }

    @Override
    public List<Artist> getAllArtists() throws IOException {
        return Collections.emptyList();
    }

    @Override
    public boolean addArtist(Artist artist) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support adding artists.");
    }

    @Override
    public boolean updateArtist(String id, Artist artist) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support updating artists.");
    }

    @Override
    public boolean deleteArtist(String id) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support deleting artists.");
    }

    @Override
    public List<Album> getAllAlbums() throws IOException {
        return Collections.emptyList();
    }

    @Override
    public Album getAlbumById(String id) throws IOException {
        String url = SPOTIFY_API_BASE_URL + "/albums/" + id;
        ResponseEntity<Album> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders()), Album.class);
        return response.getBody();
    }

    @Override
    public boolean addAlbum(Album album) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support adding albums.");
    }

    @Override
    public boolean updateAlbum(String id, Album updatedAlbum) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support updating albums.");
    }

    @Override
    public boolean deleteAlbum(String id) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support deleting albums.");
    }

    @Override
    public List<Track> getTracksByAlbumId(String albumId) throws IOException {
        String url = SPOTIFY_API_BASE_URL + "/albums/" + albumId + "/tracks";
        ResponseEntity<Track[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders()), Track[].class);
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public boolean addTrackToAlbum(String albumId, Track track) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support adding tracks to albums.");
    }

    @Override
    public boolean updateTrackInAlbum(String albumId, String trackId, Track updatedTrack) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support updating tracks.");
    }

    @Override
    public boolean deleteTrackFromAlbum(String albumId, String trackId) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support deleting tracks.");
    }

    @Override
    public List<Song> getAllSongs() throws IOException {
        return Collections.emptyList();
    }

    @Override
    public Song getSongById(String id) throws IOException {
        String url = SPOTIFY_API_BASE_URL + "/tracks/" + id;
        ResponseEntity<Song> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders()), Song.class);
        return response.getBody();
    }

    @Override
    public boolean addSong(Song song) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support adding songs.");
    }

    @Override
    public boolean updateSong(String id, Song updatedSong) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support updating songs.");
    }

    @Override
    public boolean deleteSong(String id) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not support deleting songs.");
    }

    @Override
    public List<Album> getAlbumsByArtist(String artistId) throws IOException {
        String url = SPOTIFY_API_BASE_URL + "/artists/" + artistId + "/albums";
        ResponseEntity<Album[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders()), Album[].class);
        return List.of(response.getBody());
    }

    @Override
    public List<Song> getSongsByArtist(String artistId) throws IOException {
        throw new UnsupportedOperationException("Spotify API does not provide a direct way to fetch all songs by an artist.");
    }
}
