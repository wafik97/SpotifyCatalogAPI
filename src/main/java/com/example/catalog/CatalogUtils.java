package com.example.catalog;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CatalogUtils {

    /**
     * Sorts the list of songs by their name in alphabetical order.
     *
     * @param songs List of songs (JsonNode objects).
     * @return Sorted list of songs.
     */
    public List<JsonNode> sortSongsByName(List<JsonNode> songs) {
        songs.sort(Comparator.comparing(song -> song.get("name").asText()));
        return songs;
    }

    /**
     * Filters the list of songs by popularity.
     *
     * @param songs List of songs (JsonNode objects).
     * @param minPopularity The minimal popularity to filter
     * @return List of songs filtered by popularises great or equal to minPopularity.
     */
    public List<JsonNode> filterSongsByPopularity(List<JsonNode> songs, int minPopularity) {
        return songs.stream()
                .filter(song -> song.get("popularity").asInt() >= minPopularity)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a song exists by its title.
     *
     * @param songs List of songs (JsonNode objects).
     * @param title The title of the song to search for.
     * @return true if the song exists, false otherwise.
     */
    public boolean doesSongExistByTitle(List<JsonNode> songs, String title) {
        return songs.stream()
                .anyMatch(song -> song.get("title").asText().equalsIgnoreCase(title));
    }

    /**
     * Counts the number of songs belonging to a specific genre.
     *
     * @param songs List of songs (JsonNode objects).
     * @param genre The genre to count songs by.
     * @return The number of songs in the specified genre.
     */
    public long countSongsByGenre(List<JsonNode> songs, String genre) {
        return songs.stream()
                .filter(song -> song.get("genre").asText().equalsIgnoreCase(genre))
                .count();
    }

    /**
     * Returns the song with the longest title.
     *
     * @param songs List of songs (JsonNode objects).
     * @return The song with the longest title.
     */
    public JsonNode getSongWithLongestTitle(List<JsonNode> songs) {
        return songs.stream()
                .max(Comparator.comparingInt(song -> song.get("title").asText().length()))
                .orElse(null);
    }

    /**
     * Finds the song released in the specified year.
     *
     * @param songs List of songs (JsonNode objects).
     * @param year  The release year to search for.
     * @return The first song released in the specified year.
     */
    public JsonNode getSongByYear(List<JsonNode> songs, int year) {
        return songs.stream()
                .filter(song -> song.get("year").asInt() == year)
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the most recent song (latest release year).
     *
     * @param songs List of songs (JsonNode objects).
     * @return The most recent song.
     */
    public JsonNode getMostRecentSong(List<JsonNode> songs) {
        return songs.stream()
                .max(Comparator.comparing(song -> song.get("album").get("release_date").asText()))
                .orElse(null);
    }
}
