package saif.nidhi.udemyclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
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

        initializePlayer();
        getDataOverIntent();
        loadVideo();
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

    private void initializePlayer() {
        playerView = findViewById(R.id.videoPlayer);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
    }
}
