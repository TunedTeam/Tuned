package com.example.tuned.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adamratzman.spotify.SpotifyAppApi;
import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.tuned.R;
import com.example.tuned.spotify.Spotify;
import com.example.tuned.models.Album;
import com.example.tuned.models.Track;
import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;

public class StreamingActivity extends AppCompatActivity {

    public static final String TAG = "StreamingActivity";
    Button btnplay, btnnext, btnprev, btnff, btnfr;
    TextView txtsname, txtartist, txtsstart, txtsstop;
    SeekBar seekmusic;
    ImageView trackPhoto;
    BarVisualizer visualizer;
    TextView tvBack;

    String sname;
    private Context trackContext;
    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;
    static ArrayList<Track> tracks;

    private ArrayList<Track> track = new ArrayList<>();
    private ArrayList<Album> albums = new ArrayList<>();
    Thread updateseekbar;

    private static final String YOUTUBE_API_KEY = "AIzaSyDuYKMkpL9wfSp9Vd5rn6l3MeZQOm4PMaQ";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (visualizer != null) {
            visualizer.release();
        }
        super.onDestroy();
    }

    static MediaPlayer mediaPlayer;
    int position;
    String uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);

        //getSupportActionBar().setTitle("Now playing");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnprev = findViewById(R.id.btnprev);
        btnnext = findViewById(R.id.btnnext);
        btnplay = findViewById(R.id.playbtn);
        btnff = findViewById(R.id.btnff);
        btnfr = findViewById(R.id.btnfr);
        txtsname = findViewById(R.id.txtsn);
        txtsstart = findViewById(R.id.txtsstart);
        txtsstop = findViewById(R.id.txtsstop);
        seekmusic = findViewById(R.id.seekbar);
        visualizer = findViewById(R.id.blast);
        trackPhoto = findViewById(R.id.trackPhoto);
        tvBack = findViewById(R.id.tvBack);
        txtartist = findViewById(R.id.txtartist);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Bundle bundle = getIntent().getExtras();
        String trackId = bundle.getString("trackId");
        position = bundle.getInt("position", 0);
        String trackAlbumId = bundle.getString("trackAlbumId");

        //Log.d(TAG, "THE ALBUM ID" + trackAlbumId);
        tracks = spotify.getAlbumTracks(api, trackAlbumId);

        String albumImage = spotify.getAlbumImage(api, trackAlbumId);
        //String albumImage = null;
        //albumImage = getIntent().getStringExtra(albumImage);
        // position = getIntent().getIntExtra("position",-1);
        // String trackImage = bundle.getString("trackImage");
        String trackName = bundle.getString("trackName");
        String trackArtist = bundle.getString("trackArtist");
        txtsname.setSelected(true);
        txtartist.setSelected(true);
        String url = bundle.getString("url");
        String trackPreview = bundle.getString("trackPreview");
        //String trackPreview = tracks.get(position).trackDeezerPreview;
        Log.i(TAG, "Deezer previewURL: " + trackPreview);
        txtsname.setText(trackName);
        txtartist.setText(trackArtist);


        Glide.with(this)
                .asBitmap()
                .load(albumImage)
                .into(trackPhoto);


        String trackISRC = tracks.get(position).trackISRC;

        String deezerUrl = "https://api.deezer.com/2.0/track/isrc:" + trackISRC;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(deezerUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;

                try {
                    String previewURL = jsonObject.getString("preview");

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(previewURL));

                    mediaPlayer.start();

                    updateseekbar = new Thread() {
                        @Override
                        public void run() {
                            int totalDuration = mediaPlayer.getDuration();
                            int currentposition = 0;

                            while (currentposition < totalDuration) {
                                try {
                                    sleep(500);
                                    currentposition = mediaPlayer.getCurrentPosition();
                                    seekmusic.setProgress(currentposition);
                                } catch (InterruptedException | IllegalStateException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    };

                    seekmusic.setMax(mediaPlayer.getDuration());
                    updateseekbar.start();
                    seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.dark_purple), PorterDuff.Mode.MULTIPLY);
                    seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.dark_purple), PorterDuff.Mode.SRC_IN);

                    seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            mediaPlayer.seekTo(seekBar.getProgress());

                        }
                    });

                    String endTime = createTime(mediaPlayer.getDuration());
                    txtsstop.setText(endTime);

                    final Handler handler = new Handler();
                    final int delay = 1000;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String currentTime = createTime(mediaPlayer.getCurrentPosition());
                            txtsstart.setText(currentTime);
                            handler.postDelayed(this,delay);
                        }
                    }, delay);

                    btnplay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mediaPlayer.isPlaying()) {
                                btnplay.setBackgroundResource(R.drawable.ic_play);
                                mediaPlayer.pause();
                            } else {
                                btnplay.setBackgroundResource(R.drawable.ic_pause);
                                mediaPlayer.start();
                            }
                        }
                    });
                    //next listener

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            btnnext.performClick();
                        }
                    });

        /*
        int audiosessionId = mediaPlayer.getAudioSessionId();
        if(audiosessionId != -1)
        {
            visualizer.setAudioSessionId(audiosessionId);
        }
*/
                    btnnext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            Log.d(TAG, "THE TOTAL TRACKS" + tracks.size());
                            position = ((position + 1) % tracks.size());
//                String uri = tracks.get(position).trackPreviewUrl;
                            String isrc = tracks.get(position).trackISRC;

                            String deezerPrev = "https://api.deezer.com/2.0/track/isrc:" + isrc;
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.get(deezerPrev, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Headers headers, JSON json) {
                                    JSONObject jsonObject = json.jsonObject;

                                    try {
                                        String previewURL = jsonObject.getString("preview");
                                        Log.i(TAG, "next preview: " + previewURL);
                                        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(previewURL));
                                        sname = tracks.get(position).trackName;
                                        Log.d(TAG, "TRACK NAME" + sname);
                                        txtsname.setText(sname);
                                        mediaPlayer.start();
                                        btnplay.setBackgroundResource(R.drawable.ic_pause);
                                        startAnimation(trackPhoto);
                                    } catch (JSONException e) {
                                        Log.e(TAG, "Hit JSON exception", e);
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                                    Log.d(TAG, "onFailure");
                                }
                            });

                /*
                int audiosessionId = mediaPlayer.getAudioSessionId();
                if(audiosessionId != -1)
                {
                    visualizer.setAudioSessionId(audiosessionId);
                }

                 */
                        }
                    });

                    btnprev.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            position = ((position - 1) < 0) ? (tracks.size() - 1) : (position - 1);
//                String uri = tracks.get(position).trackPreviewUrl;

                            String isrc = tracks.get(position).trackISRC;

                            String deezerPrev = "https://api.deezer.com/2.0/track/isrc:" + isrc;
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.get(deezerPrev, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Headers headers, JSON json) {
                                    JSONObject jsonObject = json.jsonObject;

                                    try {
                                        String previewURL = jsonObject.getString("preview");
                                        Log.i(TAG, "prev preview: " + previewURL);
                                        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(previewURL));
                                        sname = tracks.get(position).trackName;
                                        Log.d(TAG, "TRACK NAME" + sname);
                                        txtsname.setText(sname);
                                        mediaPlayer.start();
                                        btnplay.setBackgroundResource(R.drawable.ic_pause);
                                        startAnimation(trackPhoto);
                                    } catch (JSONException e) {
                                        Log.e(TAG, "Hit JSON exception", e);
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                                    Log.d(TAG, "onFailure");
                                }
                            });

                /*
                int audiosessionId = mediaPlayer.getAudioSessionId();
                if(audiosessionId != -1)
                {
                    visualizer.setAudioSessionId(audiosessionId);
                }

                 */
                        }
                    });

                    btnff.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                            }
                        }
                    });

                    btnfr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                            }
                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });


        // String url = "https://p.scdn.co/mp3-preview/92d40a2ae211cceb264e9ee1e67fe05f4d788200?cid=774b29d4f13844c495f206cafdad9c86";

//        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(url));
//        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(trackPreview));
//
//        mediaPlayer.start();
//
//        updateseekbar = new Thread() {
//            @Override
//            public void run() {
//                int totalDuration = mediaPlayer.getDuration();
//                int currentposition = 0;
//
//                while (currentposition < totalDuration) {
//                    try {
//                        sleep(500);
//                        currentposition = mediaPlayer.getCurrentPosition();
//                        seekmusic.setProgress(currentposition);
//                    } catch (InterruptedException | IllegalStateException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        };
//
//        seekmusic.setMax(mediaPlayer.getDuration());
//        updateseekbar.start();
//        seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.dark_purple), PorterDuff.Mode.MULTIPLY);
//        seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.dark_purple), PorterDuff.Mode.SRC_IN);
//
//        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//
//            }
//        });
//
//        String endTime = createTime(mediaPlayer.getDuration());
//        txtsstop.setText(endTime);
//
//        final Handler handler = new Handler();
//        final int delay = 1000;
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    String currentTime = createTime(mediaPlayer.getCurrentPosition());
//                    txtsstart.setText(currentTime);
//                    handler.postDelayed(this, delay);
//                } catch (final Exception e) {
//                    e.printStackTrace();
//                    if (e instanceof IllegalStateException) { // bypass IllegalStateException
//                        // You can again call the method and make a counter for deadlock situation or implement your own code according to your situation
//                        boolean checkAgain = true;
//                        int counter = 0;
//                        for (int i = 0; i < 2; i++) {
//                            if (checkAgain) {
//                                if (mediaPlayer != null & mediaPlayer.isPlaying()) {
//                                    mediaPlayer.reset();
//                                    String currentTime = createTime(mediaPlayer.getCurrentPosition());
//                                    txtsstart.setText(currentTime);
//                                    handler.postDelayed(this, delay);
//                                } else {
//                                    String currentTime = createTime(0);
//                                    txtsstart.setText(currentTime);
//                                    handler.postDelayed(this, delay);
//                                }
//                                if (mediaPlayer.getCurrentPosition() > 0) {
//                                    checkAgain = false;
//                                    counter++;
//                                }
//                            } else {
//                                if (counter == 0) {
//                                    throw e;
//                                }
//                            }
//                        }
//
//
//                    }
//                }
//            }
//        }, delay);
//
//        btnplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mediaPlayer.isPlaying()) {
//                    btnplay.setBackgroundResource(R.drawable.ic_play);
//                    mediaPlayer.pause();
//                } else {
//                    btnplay.setBackgroundResource(R.drawable.ic_pause);
//                    mediaPlayer.start();
//                }
//            }
//        });
//        //next listener
//
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                btnnext.performClick();
//            }
//        });
//
//        /*
//        int audiosessionId = mediaPlayer.getAudioSessionId();
//        if(audiosessionId != -1)
//        {
//            visualizer.setAudioSessionId(audiosessionId);
//        }
//*/
//        btnnext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                Log.d(TAG, "THE TOTAL TRACKS" + tracks.size());
//                position = ((position + 1) % tracks.size());
////                String uri = tracks.get(position).trackPreviewUrl;
//
//                String previewURL = tracks.get(position).trackDeezerPreview;
//
//                Log.i(TAG, "next preview: " + previewURL);
//
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(previewURL));
//                sname = tracks.get(position).trackName;
//                Log.d(TAG, "TRACK NAME" + sname);
//                txtsname.setText(sname);
//                mediaPlayer.start();
//                btnplay.setBackgroundResource(R.drawable.ic_pause);
//                startAnimation(trackPhoto);
//
//                /*
//                int audiosessionId = mediaPlayer.getAudioSessionId();
//                if(audiosessionId != -1)
//                {
//                    visualizer.setAudioSessionId(audiosessionId);
//                }
//
//                 */
//            }
//        });
//
//        btnprev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                position = ((position - 1) < 0) ? (tracks.size() - 1) : (position - 1);
////                String uri = tracks.get(position).trackPreviewUrl;
//
//                String previewURL = tracks.get(position).trackDeezerPreview;
//
//                Log.i(TAG, "next preview: " + previewURL);
//
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(previewURL));
//                sname = tracks.get(position).trackName;
//                Log.d(TAG, "TRACK NAME" + sname);
//                txtsname.setText(sname);
//                mediaPlayer.start();
//                btnplay.setBackgroundResource(R.drawable.ic_pause);
//                startAnimation(trackPhoto);
//
//                /*
//                int audiosessionId = mediaPlayer.getAudioSessionId();
//                if(audiosessionId != -1)
//                {
//                    visualizer.setAudioSessionId(audiosessionId);
//                }
//
//                 */
//            }
//        });
//
//        btnff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
//                }
//            }
//        });
//
//        btnfr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
//                }
//            }
//        });
//    }
    }

    public void startAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(trackPhoto, "rotation", 0f, 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    public String createTime(int duration) {
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time += min + ":";

        if (sec < 10) {
            time += "0";
        }
        time += sec;

        return time;
    }

}
