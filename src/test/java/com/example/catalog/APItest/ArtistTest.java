package com.example.catalog.APItest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.catalog.model.Artist;
import com.example.catalog.services.JSONDataSourceService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ArtistTest {

    @Test
    public void testGetAllArtists() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        List<Artist> artists = jsonDataSourceService.getAllArtists();
        assertNotNull(artists);
        assertTrue(artists.size() > 0);
        assertEquals("The Weeknd", artists.get(0).getName());
    }

    @Test
    public void testGetArtistById() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Artist artist = jsonDataSourceService.getArtistById("1Xyo4u8uXC1ZmMpatF05PJ");
        assertNotNull(artist);
        assertEquals("The Weeknd", artist.getName());
        Artist nonExistingArtist = jsonDataSourceService.getArtistById("nonexistentId");
        assertNull(nonExistingArtist);
    }

    @Test
    public void testAddArtist() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        Artist newArtist = new Artist("newId", "New Artist", 5000, List.of("pop"), 75, "spotify:newArtistUri");
        boolean result = jsonDataSourceService.addArtist(newArtist);
        assertTrue(result);
        Artist addedArtist = jsonDataSourceService.getArtistById("newId");
        assertNotNull(addedArtist);
        assertEquals("New Artist", addedArtist.getName());
    }

    @Test
    public void testUpdateArtist() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Artist newArtist = new Artist("updateId", "Artist To Update", 1000, List.of("rock"), 60, "spotify:artistUri");
        jsonDataSourceService.addArtist(newArtist);
        Artist updatedArtist = new Artist("updateId", "Updated Artist", 5000, Arrays.asList("pop", "rock"), 85, "spotify:updatedUri");
        boolean result = jsonDataSourceService.updateArtist("updateId", updatedArtist);
        assertTrue(result);
        Artist fetchedArtist = jsonDataSourceService.getArtistById("updateId");
        assertNotNull(fetchedArtist);
        assertEquals("Updated Artist", fetchedArtist.getName());
    }

    @Test
    public void testDeleteArtist() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Artist newArtist = new Artist("deleteId", "Artist To Delete", 1000, List.of("pop"), 60, "spotify:deleteUri");
        jsonDataSourceService.addArtist(newArtist);
        boolean result = jsonDataSourceService.deleteArtist("deleteId");
        assertTrue(result);
        Artist deletedArtist = jsonDataSourceService.getArtistById("deleteId");
        assertNull(deletedArtist);
    }

    @Test
    public void testAddArtistThenUpdate() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Artist newArtist = new Artist("updateArtistId", "Artist Before Update", 1000, List.of("pop"), 50, "spotify:updateArtistUri");
        jsonDataSourceService.addArtist(newArtist);
        Artist updatedArtist = new Artist("updateArtistId", "Artist After Update", 2500, Arrays.asList("pop", "r&b"), 80, "spotify:updatedArtistUri");
        boolean result = jsonDataSourceService.updateArtist("updateArtistId", updatedArtist);
        assertTrue(result);
        Artist fetchedArtist = jsonDataSourceService.getArtistById("updateArtistId");
        assertNotNull(fetchedArtist);
        assertEquals("Artist After Update", fetchedArtist.getName());
    }

    @Test
    public void testAddArtistThenDelete() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();
        Artist newArtist = new Artist("deleteArtistId", "Artist To Be Deleted", 1500, List.of("hip-hop"), 70, "spotify:deleteArtistUri");
        jsonDataSourceService.addArtist(newArtist);
        boolean result = jsonDataSourceService.deleteArtist("deleteArtistId");
        assertTrue(result);
        Artist deletedArtist = jsonDataSourceService.getArtistById("deleteArtistId");
        assertNull(deletedArtist);
    }
}
