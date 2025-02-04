package com.example.catalog.APItest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.model.Album;
import com.example.catalog.services.JSONDataSourceService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class artistTest {

    @Test
    public void testGetAllArtists() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        // Call the method
        List<Artist> artists = jsonDataSourceService.getAllArtists();

        // Verify the result
        assertNotNull(artists);
        assertTrue(artists.size() > 0);
        assertEquals("The Weeknd", artists.get(0).getName()); // Example check, adjust for your data
    }

    @Test
    public void testGetArtistById() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        // Test with an existing artist
        Artist artist = jsonDataSourceService.getArtistById("1Xyo4u8uXC1ZmMpatF05PJ");

        // Verify the result
        assertNotNull(artist);
        assertEquals("The Weeknd", artist.getName());

        // Test with a non-existing artist
        Artist nonExistingArtist = jsonDataSourceService.getArtistById("nonexistentId");
        assertNull(nonExistingArtist);
    }

    @Test
    public void testAddArtist() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        Artist newArtist = new Artist("newId", "New Artist", 5000, List.of("pop"), 75, "spotify:newArtistUri");

        // Call the method
        boolean result = jsonDataSourceService.addArtist(newArtist);

        // Verify the result
        assertTrue(result);

        // Verify that the artist is now added
        Artist addedArtist = jsonDataSourceService.getArtistById("newId");
        assertNotNull(addedArtist);
        assertEquals("New Artist", addedArtist.getName());
    }

    @Test
    public void testUpdateArtist() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        // First, add the artist to update
        Artist newArtist = new Artist("updateId", "Artist To Update", 1000, List.of("rock"), 60, "spotify:artistUri");
        jsonDataSourceService.addArtist(newArtist);

        // Create an updated version of the artist
        Artist updatedArtist = new Artist("updateId", "Updated Artist", 5000, Arrays.asList("pop", "rock"), 85, "spotify:updatedUri");

        // Call the update method
        boolean result = jsonDataSourceService.updateArtist("updateId", updatedArtist);

        // Verify the result
        assertTrue(result);

        // Verify that the artist is updated
        Artist fetchedArtist = jsonDataSourceService.getArtistById("updateId");
        assertNotNull(fetchedArtist);
        assertEquals("Updated Artist", fetchedArtist.getName());
    }

    @Test
    public void testDeleteArtist() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        // First, add the artist to delete
        Artist newArtist = new Artist("deleteId", "Artist To Delete", 1000, List.of("pop"), 60, "spotify:deleteUri");
        jsonDataSourceService.addArtist(newArtist);

        // Call the delete method
        boolean result = jsonDataSourceService.deleteArtist("deleteId");

        // Verify the result
        assertTrue(result);

        // Verify that the artist is deleted
        Artist deletedArtist = jsonDataSourceService.getArtistById("deleteId");
        assertNull(deletedArtist);
    }

    @Test
    public void testAddArtistThenUpdate() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        // Add the artist to be updated
        Artist newArtist = new Artist("updateArtistId", "Artist Before Update", 1000, List.of("pop"), 50, "spotify:updateArtistUri");
        jsonDataSourceService.addArtist(newArtist);

        // Now, update the artist object we just added
        Artist updatedArtist = new Artist("updateArtistId", "Artist After Update", 2500, Arrays.asList("pop", "r&b"), 80, "spotify:updatedArtistUri");

        // Call the update method
        boolean result = jsonDataSourceService.updateArtist("updateArtistId", updatedArtist);

        // Verify the result
        assertTrue(result);

        // Verify the artist was updated correctly
        Artist fetchedArtist = jsonDataSourceService.getArtistById("updateArtistId");
        assertNotNull(fetchedArtist);
        assertEquals("Artist After Update", fetchedArtist.getName());
    }

    @Test
    public void testAddArtistThenDelete() throws IOException {
        JSONDataSourceService jsonDataSourceService = new JSONDataSourceService();

        // Add the artist to be deleted
        Artist newArtist = new Artist("deleteArtistId", "Artist To Be Deleted", 1500, List.of("hip-hop"), 70, "spotify:deleteArtistUri");
        jsonDataSourceService.addArtist(newArtist);

        // Call the delete method
        boolean result = jsonDataSourceService.deleteArtist("deleteArtistId");

        // Verify the result
        assertTrue(result);

        // Verify that the artist was deleted
        Artist deletedArtist = jsonDataSourceService.getArtistById("deleteArtistId");
        assertNull(deletedArtist);
    }
}
