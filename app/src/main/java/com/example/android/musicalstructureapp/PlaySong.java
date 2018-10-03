package com.example.android.musicalstructureapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class PlaySong extends AppCompatActivity {
    private static String mSongName;
    private static String mArtistName;
    private static String mDuration;
    private static boolean playButtonState = true;
    private static boolean favouriteButtonState = true;
    static String durationElapsed;
    static double durationInSeconds;
    static double newDurationInSeconds;
    static int currentSongIndex;
    static ArrayList<Song> msongArrayList = new ArrayList<>();

    public PlaySong() {

    }


    public void setSongToPlay(ArrayList<Song> songArrayList, int index) {
        mSongName = songArrayList.get(index).getSongName();
        mArtistName = songArrayList.get(index).getArtistName();
        mDuration = songArrayList.get(index).getDuration();
        currentSongIndex = index;
        msongArrayList = songArrayList;

        Log.v("parseXml", "" + mSongName + mArtistName + mDuration);

    }


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_song_layout);

        covertToSeconds();

        display(mSongName, mArtistName, mDuration);


        convertDuration(newDurationInSeconds);


        final SeekBar seekSong = (SeekBar) findViewById(R.id.seek_song);


        seekSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.v("parseXml", "" + durationInSeconds + progress);
                covertToSeconds();
                newDurationInSeconds = progress * durationInSeconds / 100;
                convertDuration(newDurationInSeconds);
                Log.v("parseXml", "" + durationInSeconds + " " + progress + " " + durationElapsed);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });
        final Button fowardButton = (Button) findViewById(R.id.foward_button);
        fowardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msongArrayList.size() > currentSongIndex + 1) {
                    currentSongIndex++;
                    String s = msongArrayList.get(currentSongIndex).getSongName();
                    String a = msongArrayList.get(currentSongIndex).getArtistName();
                    String d = msongArrayList.get(currentSongIndex).getDuration();
                    seekSong.setProgress(0);
                    covertToSeconds();
                    display(s, a, d);

                }
            }
        });
        final Button rewindButton = (Button) findViewById(R.id.rewind_button);
        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSongIndex != 0) {
                    currentSongIndex--;
                    String s = msongArrayList.get(currentSongIndex).getSongName();
                    String a = msongArrayList.get(currentSongIndex).getArtistName();
                    String d = msongArrayList.get(currentSongIndex).getDuration();
                    seekSong.setProgress(0);
                    covertToSeconds();
                    display(s, a, d);
                }
            }
        });
        final Button favouriteButton = (Button) findViewById(R.id.favourite_button);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                if (favouriteButtonState == true) {
                    message = msongArrayList.get(currentSongIndex)
                            .getSongName() + " added to favouriteButton";
                    favouriteButtonState = false;
                } else {
                    message = msongArrayList.get(currentSongIndex)
                            .getSongName() + " removed from favourite Button";
                    favouriteButtonState = true;
                }
                Toast toast = Toast.makeText(PlaySong.this, message, Toast.LENGTH_LONG);
                toast.show();


            }
        });
        final Button playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playButtonState == true) {
                    playButton.setBackgroundResource(R.drawable.ic_pause_icon);
                    seekSong.setKeyProgressIncrement(seekSong.getProgress() + 10);
                    playButtonState = false;
                    updatePlay();
                } else {
                    playButton.setBackgroundResource(R.drawable.ic_play_icon);
                    playButtonState = true;
                }
            }
        });


    }

    private void display(String s, String a, String d) {
        TextView songName = (TextView) findViewById(R.id.song_name);

        songName.setText(s);
        final TextView artistName = (TextView) findViewById(R.id.artist_name);
        artistName.setText(a);

        TextView duration = (TextView) findViewById(R.id.duration_text_view);
        duration.setText(d);
    }


    private String convertDuration(double a) {
        final TextView durationPlayed = (TextView) findViewById(R.id.duration_played_text_view);

        int x = (int) a / 60;
        int y = (int) a % 60;
        durationElapsed = (Integer.toString(x) + ":" + Integer.toString(y));
        durationPlayed.setText("" + durationElapsed);
        return durationElapsed;
    }

    private void updatePlay() {


//          durationInSeconds =durationInSeconds+1;
//        convertDuration(durationInSeconds);


        Log.v("parseXml", "" + durationInSeconds + " ");
    }

    private double covertToSeconds() {
        String str[] = mDuration.split(":");
        durationInSeconds = Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
        return durationInSeconds;
    }
}
