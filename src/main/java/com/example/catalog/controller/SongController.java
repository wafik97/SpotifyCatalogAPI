package com.example.catalog.controller;

import com.example.catalog.model.Song;
import com.example.catalog.services.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final DataSourceService dataSourceService;

    @Autowired
    public SongController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() throws IOException {
        List<Song> songs = dataSourceService.getAllSongs();
        if (songs == null || songs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable String id) throws IOException {
        if (!isValidId(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Song song = dataSourceService.getSongById(id);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<Void> addSong(@RequestBody Song song) throws IOException {
        if (song == null || song.getName() == null || song.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            dataSourceService.addSong(song);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSong(@PathVariable String id, @RequestBody Song updatedSong) throws IOException {
        if (!isValidId(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Song existingSong = dataSourceService.getSongById(id);
        if (existingSong == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            dataSourceService.updateSong(id, updatedSong);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable String id) throws IOException {
        if (!isValidId(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Song song = dataSourceService.getSongById(id);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            dataSourceService.deleteSong(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private boolean isValidId(String id) {
        return id != null && !id.isEmpty();
    }
}
