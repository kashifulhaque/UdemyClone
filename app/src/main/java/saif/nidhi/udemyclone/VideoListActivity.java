package saif.nidhi.udemyclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import saif.nidhi.udemyclone.models.Video;
import saif.nidhi.udemyclone.models.VideoAdapter;

@SuppressWarnings("FieldCanBeLocal")
public class VideoListActivity extends AppCompatActivity {

    // Widgets
    private RecyclerView mRecyclerView;
    private VideoAdapter adapter;

    // Data Models
    private Video video;

    // Model Variables
    private ArrayList<String> downloadURL = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Long> number = new ArrayList<>();
    private ArrayList<String> storageLocation = new ArrayList<>();

    // Variables
    private String courseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        getDataOverIntent();
        checkAuth();
    }

    private void getDataOverIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            courseCode = intent.getExtras().getString("courseCode");
        }
    }

    private void checkAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getFirebaseData();
        }
    }

    private void getFirebaseData() {
        FirebaseDatabase.getInstance().getReference().child(getString(R.string.dbnode_videos))
                .child(courseCode)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            video = singleSnapshot.getValue(Video.class);

                            if (video != null)
                                fetchVideos(video);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void fetchVideos(Video video) {
        downloadURL.add(video.getDownload_url());
        name.add(video.getName());
        number.add(video.getNumber());
        storageLocation.add(video.getStorage_location());

        initialize();
    }

    private void initialize() {
        mRecyclerView = findViewById(R.id.rvVideoList);
        adapter = new VideoAdapter(downloadURL, name, number, storageLocation, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
