package com.example.android.musicalstructureapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.value;


public class SongAdapter extends ArrayAdapter<Song> {

    /**
     * This is a public constructor used to create an object of the song adapter class
     * Here, we initialize the ArrayAdapter's internal storage for the context and the list.
     * the second argument is used when the ArrayAdapter is populating a single TextView.
     * Because this is a custom adapter for Three TextViews and an ImageView, the adapter is not
     * going to use this second argument, so it can be any value. Here, we used 0.
     */

    public SongAdapter(Activity context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The list view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Gets the Word object from the ArrayAdapter at the appropriate position
        Song currentSong = getItem(position);
        //here we initialize the views to be populated
        TextView songNameView = (TextView) listView.findViewById(R.id.sond_name_text_view);
        songNameView.setText(currentSong.getSongName());
        TextView artistNameView = (TextView) listView.findViewById(R.id.artist_name_text_view);
        artistNameView.setText(currentSong.getArtistName());
        ImageView songImageView = (ImageView) listView.findViewById(R.id.song_image_view);
        songImageView.setImageResource(currentSong.getImageId());
        TextView durationView = (TextView) listView.findViewById(R.id.duration_text_view);
        durationView.setText(currentSong.getDuration());
        return listView;

    }
}





