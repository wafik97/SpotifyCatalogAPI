package com.example.catalog.APItest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.catalog.model.Album;
import com.example.catalog.model.Track;
import com.example.catalog.services.JSONDataSourceService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AlbumTest {





    @Test
    public void testUpdateAlbum() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Album newAlbum = new Album("updateId", "Album To Update", Arrays.asList(new Track("track1", "Track 1")));
        jsonDataSourceService.addAlbum(newAlbum);
        Album updatedAlbum = new Album("updateId", "Updated Album", Arrays.asList(new Track("track2", "Track 2")));
        boolean result = jsonDataSourceService.updateAlbum("updateId", updatedAlbum);
        assertTrue(result);
        Album fetchedAlbum = jsonDataSourceService.getAlbumById("updateId");
        assertNotNull(fetchedAlbum);
        assertEquals("Updated Album", fetchedAlbum.getName());
    }

    @Test
    public void testDeleteAlbum() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Album newAlbum = new Album("deleteId", "Album To Delete", Arrays.asList(new Track("track1", "Track 1")));
        jsonDataSourceService.addAlbum(newAlbum);
        boolean result = jsonDataSourceService.deleteAlbum("deleteId");
        assertTrue(result);
        Album deletedAlbum = jsonDataSourceService.getAlbumById("deleteId");
        assertNull(deletedAlbum);
    }

    @Test
    public void testAddAlbumThenUpdate() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Album newAlbum = new Album("updateAlbumId", "Album Before Update", Arrays.asList(new Track("track1", "Track 1")));
        jsonDataSourceService.addAlbum(newAlbum);
        Album updatedAlbum = new Album("updateAlbumId", "Album After Update", Arrays.asList(new Track("track2", "Track 2")));
        boolean result = jsonDataSourceService.updateAlbum("updateAlbumId", updatedAlbum);
        assertTrue(result);
        Album fetchedAlbum = jsonDataSourceService.getAlbumById("updateAlbumId");
        assertNotNull(fetchedAlbum);
        assertEquals("Album After Update", fetchedAlbum.getName());
    }

    @Test
    public void testAddAlbumThenDelete() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Album newAlbum = new Album("deleteAlbumId", "Album To Be Deleted", Arrays.asList(new Track("track1", "Track 1")));
        jsonDataSourceService.addAlbum(newAlbum);
        boolean result = jsonDataSourceService.deleteAlbum("deleteAlbumId");
        assertTrue(result);
        Album deletedAlbum = jsonDataSourceService.getAlbumById("deleteAlbumId");
        assertNull(deletedAlbum);
    }
}
