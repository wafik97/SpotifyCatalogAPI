package com.example.catalog.controller;

import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.services.DataSourceService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final DataSourceService dataSourceService;

    @Autowired
    public ArtistController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable String id) throws IOException {
        if (!isValidId(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Artist artist = dataSourceService.getArtistById(id);
        if (artist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() throws IOException {
        List<Artist> artists = dataSourceService.getAllArtists();
        if (artists == null || artists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(artists);
    }

    @PostMapping
    public ResponseEntity<Void> addArtist(@RequestBody Artist artist) {
        try {
            if (artist == null || artist.getName() == null || artist.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            dataSourceService.addArtist(artist);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArtist(@PathVariable String id, @RequestBody Artist artist) {
        try {
            if (!isValidId(id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Artist existingArtist = dataSourceService.getArtistById(id);
            if (existingArtist == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            dataSourceService.updateArtist(id, artist);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable String id) {
        try {
            if (!isValidId(id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Artist artist = dataSourceService.getArtistById(id);
            if (artist == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            dataSourceService.deleteArtist(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<Album>> getAlbumsByArtist(@PathVariable String id) throws IOException {
        if (!isValidId(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<Album> albums = dataSourceService.getAlbumsByArtist(id);
        if (albums.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<Song>> getSongsByArtist(@PathVariable String id) throws IOException {
        if (!isValidId(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<Song> songs = dataSourceService.getSongsByArtist(id);
        if (songs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(songs);
    }

    private boolean isValidId(String id) {
        return id != null && !id.isEmpty();
    }
}
