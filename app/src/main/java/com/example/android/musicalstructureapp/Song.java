package com.example.android.musicalstructureapp;

import static android.R.attr.duration;

/**
 A class to store each instance of a song
 */

public class Song {

    private String mSongName;
    private String mArtistName;
    private String mDuration;
    private int mImageId;


    public Song(String songName, String artistName, String duration, int imageResId) {
        mSongName = songName;
        mArtistName = artistName;
        mImageId = imageResId;
        mDuration = duration;
    }


    public String getSongName(){
        return  mSongName;
    }
    public String getArtistName(){
        return  mArtistName;
    }
    public String getDuration(){return  mDuration;  }
    public int getImageId(){return  mImageId;  }
}



