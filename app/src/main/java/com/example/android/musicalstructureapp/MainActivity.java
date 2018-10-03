package com.example.android.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static android.R.attr.value;
import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity {
   public static String NAME ;
    final static String COLOR = "productcolor";
    final static String QUANTITY = "productquantity";
    static int  songListIndex;
    int type = -3;

    ArrayList<Song> songArrayList = new ArrayList<>();
    ArrayList<String> songName = new ArrayList<>();
    ArrayList<String> artistName = new ArrayList<>();
    ArrayList<String> duration = new ArrayList<>();
    ArrayList<Integer> imageId = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //instantiate XmlPullParser
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            pullParserFactory.setNamespaceAware(true);
            XmlPullParser parser = pullParserFactory.newPullParser();


            InputStream in_s = getApplicationContext().getAssets().open("temp.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseXML(parser);
            final SongAdapter adapter = new SongAdapter(this, songArrayList);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    songListIndex = position;
                    String nowPlayingSongName;
                    nowPlayingSongName = songArrayList.get(position).getSongName();

                    String nowPlayingArtistName;
                    nowPlayingArtistName = songArrayList.get(position).getArtistName();
                    String nowPlayingDuration;
                    nowPlayingDuration = songArrayList.get(position).getDuration();
                    Log.v("parseXml", "" + nowPlayingSongName + nowPlayingArtistName + nowPlayingDuration);
                    PlaySong playSong = new PlaySong();
                    playSong.setSongToPlay(songArrayList, position);


                    Intent intent = new Intent(MainActivity.this, PlaySong.class);
                    startActivity(intent);

                }
            });
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public int parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {


        Log.v("parseXml", "parsingXml ");

        int eventType = parser.getEventType();

        while (eventType != parser.END_DOCUMENT) {

            Log.v("parseXml", " entered while loop");
            if (eventType == parser.TEXT && parser.isWhitespace()) {
                eventType = parser.next();

            }

            if (eventType == parser.START_DOCUMENT) {
                Log.v("parseXml", " xml START ");
            }
            if (eventType == XmlPullParser.END_DOCUMENT) {
                Log.v("parseXml", "  END DOCUMENT");
            }
            if (eventType == XmlPullParser.START_TAG) {

                Log.v("parseXml", " xml Start tag" + parser.getName() + "");
                String x = parser.getName();

                if (x.equals("songname")) {
                    type = 1;
                }
                if (x.equals("artistname")) {
                    type = 2;
                }

                if (x.equals("duration")) {
                    type = 3;
                }
                if (x.equals("imageid")) {
                    type = 4;
                }


            }

            if (eventType == XmlPullParser.END_TAG) {
                Log.v("parseXml", " entered end tag");
            }
            if (eventType == XmlPullParser.TEXT) {

                Log.v("parseXml", " entered text");
        if (type == 1) {
                    songName.add(parser.getText());
                    Log.v("parseXml", " productname stored" + parser.getText());
                }

                else if (type == 2) {
                    artistName.add(parser.getText());
                    Log.v("parseXml", " productcolor stored" + parser.getText());
              } else if (type == 3) {

                    duration.add(parser.getText());
                    Log.v("parseXml", " productquantity stored" + parser.getText());
       }

                else if (type == 4) {

                    imageId.add(R.drawable.play_music_icon);
                    Log.v("parseXml", " productquantity stored" + parser.getText());
 }
            }
            Log.v("parseXml", " change event type");

            eventType = parser.next();
        }


        printProducts(songName, artistName,  duration, imageId);
        Log.v("parseXml", " printing" + songName);
        Log.v("parseXml", " printing" + duration);
        Log.v("parseXml", " printing" + artistName);

        return type;

    }
//}


    //
    private void printProducts(ArrayList<String> name, ArrayList<String> artist,
                               ArrayList<String> duration, ArrayList<Integer> imageId) {

        Iterator<String> it = name.iterator();
        Iterator<String> itt = artist.iterator();
        Iterator<String> ittt = duration.iterator();
        int i = 0;
        while (it.hasNext()||itt.hasNext() || ittt.hasNext()) {

            String storeSongName = name.get(i);
            String storeArtistName = artist.get(i);
            String storeDuration = duration.get(i);
            Integer storeImageId = imageId.get(i);

            songArrayList.add(new Song(storeSongName, storeArtistName,  storeDuration, storeImageId));
            it.next();
            itt.next();
            ittt.next();
                i++;
            Log.v("parseXml", "printing xml " +    storeArtistName + storeDuration);
        }


    }

//
}








