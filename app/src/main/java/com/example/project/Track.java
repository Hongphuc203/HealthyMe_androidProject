package com.example.project;
public class Track {
    public String name;
    public String artist;
    public String audioUrl;
    public String albumImage;
    // Constructor
    public Track() {}

    // Getter/Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }

    public String getAlbumImage() { return albumImage; }
    public void setAlbumImage(String albumImage) { this.albumImage = albumImage; }
}
