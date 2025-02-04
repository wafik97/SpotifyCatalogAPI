package com.example.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    private String id;
    private String name;

    @JsonProperty("followers")
    @JsonDeserialize(using = Followers.class)
    private int followers;
    private List<String> genres;
    private List<Image> images;
    private int popularity;
    private String uri;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;


    public Artist(String id, String name, int followers, List<String> genres, int popularity, String uri) {
        this.id = id;
        this.name = name;
        this.followers = followers;
        this.genres = genres;

        this.popularity = popularity;
        this.uri = uri;
    }

    public Artist(String id, String name, String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
    }


    public Artist() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}