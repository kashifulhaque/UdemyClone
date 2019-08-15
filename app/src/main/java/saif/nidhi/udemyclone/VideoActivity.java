package saif.nidhi.udemyclone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import saif.nidhi.udemyclone.models.Video;

@SuppressWarnings("FieldCanBeLocal")
public class VideoActivity extends AppCompatActivity {

    // Widgets
    private PlayerView playerView;
    private SimpleExoPlayer player;

    // Variables
    private boolean playWhenReady = true;
    private long playbackPosition;
    private int currentWindow;
    private String courseCode;

    // Data Models
    private Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getDataOverIntent();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            loadVideo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            loadVideo();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void loadVideo() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child(getString(R.string.dbnode_videos))
                    .child(courseCode)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                video = singleSnapshot.getValue(Video.class);

                                if (video != null)
                                    initializePlayer(video);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }
    }

    private void getDataOverIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            courseCode = intent.getExtras().getString("courseCode");
        }
    }

    private void initializePlayer(Video video) {
        playerView = findViewById(R.id.videoPlayer);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(video.getDownload_url());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }
}
