package com.example.catalog;

import com.example.catalog.utils.SpotifyUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.example.catalog.utils.SpotifyUtils.*;
import static org.junit.jupiter.api.Assertions.*;


//@Disabled("Should be enabled for Junit exercises")
public class SpotifyUtilsTest {

    @Test
    public void testValidId() {
        assertTrue(isValidId("6rqhFgbbKwnb9MLmUQDhG6")); // valid Spotify ID
        assertTrue(isValidId("1a2B3c4D5e6F7g8H9iJkL0mN")); // valid 22 character ID
        assertTrue(isValidId("a1b2C3d4E5f6G7h8I9jK0L1m2N")); // valid 30 character ID
    }

    @Test
    public void testInvalidId() {
        assertFalse(isValidId(null)); // null ID
        assertFalse(isValidId("")); // empty ID
        assertFalse(isValidId("shortID")); // too short ID (less than 15 characters)
        assertFalse(isValidId("thisIDiswaytoolongtobevalid0000000000000000000000000000000000000000000000000000000000000000000")); // too long ID (more than 30 characters)
        assertFalse(isValidId("!@#$$%^&*()_+")); // invalid characters
        assertFalse(isValidId("1234567890abcdefGHIJKLMNO!@#")); // includes invalid characters
    }


    @Test
    public void testValidURI() {
        assertTrue(isValidURI("spotify:track:6rqhFgbbKwnb9MLmUQDhG6")); // ValidURI
        assertTrue(isValidURI("spotify:artist:sdgsdf545MLmUQDhG6")); // ValidURI
        assertTrue(isValidURI("spotify:album:6rqhFgbfsd2f15sd1f5DhG6")); // ValidURI
        assertTrue(isValidURI("spotify:playlist:sds5f15fsfsQDhG6")); // ValidURI

    }


    @Test
    public void testInvalidURI() {
        assertFalse(isValidURI(null)); // null ID
        assertFalse(isValidURI("")); // empty ID
        assertFalse(isValidURI("shortID")); // too short URI
        assertFalse(isValidURI("bibo:playlist:sds5f15fsfsQDhG6")); // too long ID (more than 30 characters)
        assertFalse(isValidURI("spotify:pablo:sds5f15fsfsQDhG6")); // invalid characters
        assertFalse(isValidURI("spotify:playlist:sds5f15fsfs#QDhG6")); //
        assertFalse(isValidURI("spotify:track:6rhG6")); //
        assertFalse(isValidURI("spotify:artist:sdgsdf54sgsdgsdgsdgsdgdfgdsfhfhfgshhshshshshshshshshshshdfhdfhrsthrth5MLmUQDhG6")); //

    }

    /*
        @Test
        public void testValidSpotifyClient() {
            assertTrue(getSpotifyClient("spotify:track:6rqhFgbbKwnb9MLmUQDhG6")); // null ID
            assertTrue(getSpotifyClient("spotify:artist:sdgsdf545MLmUQDhG6")); // null ID
            assertTrue(getSpotifyClient("spotify:album:6rqhFgbfsd2f15sd1f5DhG6")); // null ID
            assertTrue(getSpotifyClient("spotify:playlist:sds5f15fsfsQDhG6")); // null ID

        }
        */
    @Test
    public void testInvalidSpotifyClient() {


        assertThrows(IllegalArgumentException.class, () -> {
            SpotifyUtils.getSpotifyClient(null, "null");
        }, "Should throw exception when client ID is null");
        assertThrows(IllegalArgumentException.class, () -> {
            SpotifyUtils.getSpotifyClient("null", null);
        }, "Should throw exception when client ID is null");
        assertThrows(IllegalArgumentException.class, () -> {
            SpotifyUtils.getSpotifyClient("", "null");
        }, "Should throw exception when client ID is empty");
        assertThrows(IllegalArgumentException.class, () -> {
            SpotifyUtils.getSpotifyClient("null", "");
        }, "Should throw exception when client ID is empty");


    }

}






