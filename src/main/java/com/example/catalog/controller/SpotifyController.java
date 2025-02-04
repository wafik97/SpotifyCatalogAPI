package com.example.catalog.controller;

import com.example.catalog.model.Album;
import com.example.catalog.model.Song;
import com.example.catalog.model.Track;
import com.example.catalog.model.Artist;
import com.example.catalog.services.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    private final DataSourceService dataSourceService;

    @Autowired
    public SpotifyController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    // **Artists**

    @GetMapping("/artists")
    public ResponseEntity<List<Artist>> getAllArtists() {
        try {
            List<Artist> artists = dataSourceService.getAllArtists();
            return ResponseEntity.ok(artists);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable String id) {
        try {
            Artist artist = dataSourceService.getArtistById(id);
            if (artist != null) {
                return ResponseEntity.ok(artist);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/artists")
    public ResponseEntity<Void> addArtist(@RequestBody Artist artist) {
        try {
            dataSourceService.addArtist(artist);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/artists/{id}")
    public ResponseEntity<Void> updateArtist(@PathVariable String id, @RequestBody Artist artist) {
        try {
            dataSourceService.updateArtist(id, artist);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable String id) {
        try {
            dataSourceService.deleteArtist(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // **Albums**

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAllAlbums() {
        try {
            List<Album> albums = dataSourceService.getAllAlbums();
            return ResponseEntity.ok(albums);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable String id) {
        try {
            Album album = dataSourceService.getAlbumById(id);
            if (album != null) {
                return ResponseEntity.ok(album);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/albums")
    public ResponseEntity<Void> addAlbum(@RequestBody Album album) {
        try {
            dataSourceService.addAlbum(album);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/albums/{id}")
    public ResponseEntity<Void> updateAlbum(@PathVariable String id, @RequestBody Album album) {
        try {
            dataSourceService.updateAlbum(id, album);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable String id) {
        try {
            dataSourceService.deleteAlbum(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/albums/{id}/tracks")
    public ResponseEntity<List<Track>> getTracksByAlbumId(@PathVariable String id) {
        try {
            List<Track> tracks = dataSourceService.getTracksByAlbumId(id);
            return ResponseEntity.ok(tracks);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/albums/{id}/tracks")
    public ResponseEntity<Void> addTrackToAlbum(@PathVariable String id, @RequestBody Track track) {
        try {
            dataSourceService.addTrackToAlbum(id, track);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/albums/{id}/tracks/{track_id}")
    public ResponseEntity<Void> updateTrackInAlbum(@PathVariable String id, @PathVariable String track_id, @RequestBody Track track) {
        try {
            dataSourceService.updateTrackInAlbum(id, track_id, track);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/albums/{id}/tracks/{track_id}")
    public ResponseEntity<Void> deleteTrackFromAlbum(@PathVariable String id, @PathVariable String track_id) {
        try {
            dataSourceService.deleteTrackFromAlbum(id, track_id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // **Songs**

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs() {
        try {
            List<Song> songs = dataSourceService.getAllSongs();
            return ResponseEntity.ok(songs);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable String id) {
        try {
            Song song = dataSourceService.getSongById(id);
            if (song != null) {
                return ResponseEntity.ok(song);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/songs")
    public ResponseEntity<Void> addSong(@RequestBody Song song) {
        try {
            dataSourceService.addSong(song);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<Void> updateSong(@PathVariable String id, @RequestBody Song song) {
        try {
            boolean updated = dataSourceService.updateSong(id, song);
            if (updated) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable String id) {
        try {
            boolean deleted = dataSourceService.deleteSong(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
