package com.example.catalog.services;

import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.model.Track;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyAPIDataSourceService implements DataSourceService {

    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/";

    private final String accessToken = "BQBkc8dprXPRWXD2UE_6Ct1iE1ffLAJGCZ_c7zj9gIturKQWVtS1-Wvi5frHdR3bBjvGiNHfxxNyjBWuQ2zR1AHd8896_nQRmPemwC3ELgvjszwoiL0tbhp90xnvN89m2cJZQ0mSs50";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SpotifyAPIDataSourceService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private JsonNode makeSpotifyRequest(String endpoint) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(SPOTIFY_API_BASE_URL + endpoint, HttpMethod.GET, entity, String.class);

        return objectMapper.readTree(response.getBody());
    }

    @Override
    public Artist getArtistById(String id) throws IOException {
        try {
            JsonNode artistNode = makeSpotifyRequest("artists/" + id);
            return objectMapper.treeToValue(artistNode, Artist.class);
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                return null;
            }
            throw new IOException("Error fetching artist: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Artist> getAllArtists() throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot retrieve all artists from Spotify.");
    }

    @Override
    public boolean addArtist(Artist artist) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot add a new artist to Spotify.");
    }

    @Override
    public boolean updateArtist(String id, Artist artist) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot update an existing artist on Spotify.");
    }

    @Override
    public boolean deleteArtist(String id) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot delete an artist from Spotify.");
    }

    @Override
    public List<Album> getAllAlbums() throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot retrieve all albums from Spotify.");
    }

    @Override
    public Album getAlbumById(String id) throws IOException {
        try {
            JsonNode albumNode = makeSpotifyRequest("albums/" + id);
            return objectMapper.treeToValue(albumNode, Album.class);
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                return null;
            }
            throw new IOException("Error fetching album: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean addAlbum(Album album) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot add a new album to Spotify.");
    }

    @Override
    public boolean updateAlbum(String id, Album updatedAlbum) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot update an existing album on Spotify.");
    }

    @Override
    public boolean deleteAlbum(String id) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot delete an album from Spotify.");
    }

    @Override
    public List<Track> getTracksByAlbumId(String albumId) throws IOException {
        try {
            JsonNode tracksNode = makeSpotifyRequest("albums/" + albumId + "/tracks");

            if (tracksNode == null || !tracksNode.has("items")) {
                throw new IOException("No tracks found for album ID: " + albumId);
            }

            return Arrays.asList(objectMapper.treeToValue(tracksNode.get("items"), Track[].class));

        } catch (IOException e) {
            throw new IOException("Failed to retrieve tracks: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean addTrackToAlbum(String albumId, Track track) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot add a new track to Spotify.");
    }

    @Override
    public boolean updateTrackInAlbum(String albumId, String trackId, Track updatedTrack) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot update a track in Spotify.");
    }

    @Override
    public boolean deleteTrackFromAlbum(String albumId, String trackId) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot delete a track from Spotify.");
    }

    @Override
    public List<Song> getAllSongs() throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot retrieve all songs from Spotify.");
    }

    @Override
    public Song getSongById(String id) throws IOException {
        try {
            JsonNode trackNode = makeSpotifyRequest("tracks/" + id);
            return objectMapper.treeToValue(trackNode, Song.class);
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                return null;
            }
            throw new IOException("Error fetching song: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean addSong(Song song) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot add a new song to Spotify.");
    }

    @Override
    public boolean updateSong(String id, Song updatedSong) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot update a song in Spotify.");
    }

    @Override
    public boolean deleteSong(String id) throws IOException {
        throw new UnsupportedOperationException("Operation not supported: Cannot delete a song from Spotify.");
    }

    @Override
    public List<Album> getAlbumsByArtist(String artistId) throws IOException {
        if (artistId == null || artistId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid artist ID: Artist ID cannot be null or empty.");
        }

        try {
            JsonNode albumsNode = makeSpotifyRequest("artists/" + artistId + "/albums");

            if (albumsNode == null || !albumsNode.has("items")) {
                throw new IOException("Failed to retrieve albums: No data returned from Spotify API.");
            }

            List<Album> albums = Arrays.asList(objectMapper.treeToValue(albumsNode.get("items"), Album[].class));

            if (albums.isEmpty()) {
                throw new IOException("No albums found for artist with ID: " + artistId);
            }

            return albums;

        } catch (IOException e) {
            if (e.getMessage().contains("404")) {
                throw new IOException("Artist not found: No artist exists with ID: " + artistId);
            }
            throw new IOException("Failed to retrieve albums: Spotify API error.");
        }
    }

    @Override
    public List<Song> getSongsByArtist(String artistId) throws IOException {
        if (artistId == null || artistId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid artist ID: Artist ID cannot be null or empty.");
        }

        try {
            List<Album> albums = getAlbumsByArtist(artistId);

            if (albums.isEmpty()) {
                throw new IOException("No albums found for artist with ID: " + artistId);
            }

            List<Song> songs = new ArrayList<>();

            for (Album album : albums) {
                try {
                    JsonNode tracksNode = makeSpotifyRequest("albums/" + album.getId() + "/tracks");

                    if (tracksNode == null || !tracksNode.has("items")) {
                        continue;
                    }

                    List<Song> albumSongs = Arrays.asList(objectMapper.treeToValue(tracksNode.get("items"), Song[].class));
                    songs.addAll(albumSongs);

                } catch (IOException e) {
                    // Log error but continue processing other albums
                    System.err.println("Failed to retrieve tracks for album ID: " + album.getId());
                }
            }

            if (songs.isEmpty()) {
                throw new IOException("No songs found for artist with ID: " + artistId);
            }

            return songs;

        } catch (IOException e) {
            if (e.getMessage().contains("404")) {
                throw new IOException("Artist not found: No artist exists with ID: " + artistId);
            }
            throw new IOException("Failed to retrieve songs: Spotify API error.");
        }
    }
}
