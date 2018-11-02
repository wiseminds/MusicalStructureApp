package com.example.android.musicalstructureapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


/**
  *This is a Class manages the details activity for each song, displaying an image view, several buttons,
 * and text views showing song name, artist name, duration, then it uses a seek bar to show progress played
 *
 */
public class PlaySongActivity extends AppCompatActivity implements View.OnClickListener {
    private static String mSongName;
    private static String mArtistName;
    private static String mDuration;
    private static boolean playButtonState = true;  //determines if the play button is on play or pause
    private static boolean favouriteButtonState = true; //determines if we add to favourite or remove from favourite
    static String durationElapsed;
    static double durationInSeconds;
    static double newDurationInSeconds;
    static int currentSongIndex;                //the index of a song instance in the song array list
    SeekBar seekSong;
    Button playButton;

    //an array list of the song type that stores each instance of a song
    static ArrayList<Song> mSongArrayList = new ArrayList<>();

    public void setSongToPlay(ArrayList<Song> songArrayList, int index) {
        mSongName = songArrayList.get(index).getSongName();
        mArtistName = songArrayList.get(index).getArtistName();
        mDuration = songArrayList.get(index).getDuration();
        currentSongIndex = index;
        mSongArrayList = songArrayList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_song_layout);
        covertToSeconds();
        display(mSongName, mArtistName, mDuration);
        convertDuration(newDurationInSeconds);
        seekSong = (SeekBar) findViewById(R.id.seek_song);
        seekSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                covertToSeconds();
                newDurationInSeconds = progress * durationInSeconds / 100;
                convertDuration(newDurationInSeconds);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            } });
        final Button nextSongButton = (Button) findViewById(R.id.foward_button);
        nextSongButton.setOnClickListener(this);
        final Button previousSongButton = (Button) findViewById(R.id.rewind_button);
        previousSongButton.setOnClickListener(this);
        final Button addToFavouriteButton = (Button) findViewById(R.id.favourite_button);
        addToFavouriteButton.setOnClickListener(this);
         playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(this);
    }
    //This method updates display for each instance of a song
    private void display(String s, String a, String d) {
        TextView songName = (TextView) findViewById(R.id.song_name);
        songName.setText(s);
        final TextView artistName = (TextView) findViewById(R.id.artist_name);
        artistName.setText(a);
        TextView duration = (TextView) findViewById(R.id.duration_text_view);
        duration.setText(d);
    }
    //This converts the duration string to a number that can be used for calculation
    private double covertToSeconds() {
        String str[] = mDuration.split(":");
        durationInSeconds = Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
        return durationInSeconds;
    }
    //this converts the duration string so that it can display same format that was recieved initially
    private String convertDuration(double a) {
        final TextView durationPlayed = (TextView) findViewById(R.id.duration_played_text_view);
        int x = (int) a / 60;
        int y = (int) a % 60;
        durationElapsed = (Integer.toString(x) + ":" + Integer.toString(y));
        durationPlayed.setText("" + durationElapsed);
        return durationElapsed;
    }
    //implemnts the onclick method here
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.foward_button:
                if (mSongArrayList.size() > currentSongIndex + 1) {
                    currentSongIndex++;
                    String s = mSongArrayList.get(currentSongIndex).getSongName();
                    String a = mSongArrayList.get(currentSongIndex).getArtistName();
                    String d = mSongArrayList.get(currentSongIndex).getDuration();
                    seekSong.setProgress(0);
                    covertToSeconds();
                    display(s, a, d);
                    break;
                }
            case R.id.favourite_button:
                String message;
                if (favouriteButtonState == true) {
                    message = mSongArrayList.get(currentSongIndex)
                            .getSongName() + " added to Favourite";
                    favouriteButtonState = false;
                } else {
                    message = mSongArrayList.get(currentSongIndex)
                            .getSongName() + " removed from Favourite";
                    favouriteButtonState = true;
                }
                Toast toast = Toast.makeText(PlaySongActivity.this, message, Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.rewind_button:
                if (currentSongIndex != 0) {
                    currentSongIndex--;
                    String s = mSongArrayList.get(currentSongIndex).getSongName();
                    String a = mSongArrayList.get(currentSongIndex).getArtistName();
                    String d = mSongArrayList.get(currentSongIndex).getDuration();
                    seekSong.setProgress(0);
                    covertToSeconds();
                    display(s, a, d);
                    break;
                }
            case R.id.play_button:

                if (playButtonState == true) {
                    playButton.setBackgroundResource(R.drawable.ic_pause_icon);
                    seekSong.setKeyProgressIncrement(seekSong.getProgress() + 10);
                    playButtonState = false;

                } else {
                    playButton.setBackgroundResource(R.drawable.ic_play_icon);
                    playButtonState = true;
                }
                break;
        }
    }
}