package com.example.android.musicalstructureapp;

/**
 * A class to store each instance of a song
 */
public class Song {
    private String mSongName;
    private String mArtistName;
    private String mDuration;
    private int mImageId;
    //a constructor used to create an object of the song class
    public Song(String songName, String artistName,
                String duration, int imageResId) {
        mSongName = songName;
        mArtistName = artistName;
        mImageId = imageResId;
        mDuration = duration;
    }
    // These are public methods to recieve various atributes of a song
    public String getSongName() {
        return mSongName;
    }
    public String getArtistName() {
        return mArtistName;
    }
    public String getDuration() {
        return mDuration;
    }
    public int getImageId() {
        return mImageId;
    }
}



