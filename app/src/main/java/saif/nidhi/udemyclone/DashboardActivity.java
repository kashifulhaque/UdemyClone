package saif.nidhi.udemyclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import saif.nidhi.udemyclone.models.Course;
import saif.nidhi.udemyclone.models.CourseAdapter;

@SuppressWarnings("FieldCanBeLocal")
public class DashboardActivity extends AppCompatActivity {

    // Widgets
    private Button mLogout;
    private RecyclerView mRecyclerView;
    private CourseAdapter adapter;
    private ProgressBar progressBar;

    // Model Variables
    private ArrayList<String> courseName = new ArrayList<>();
    private ArrayList<String> courseCode = new ArrayList<>();
    private ArrayList<String> courseAuthor = new ArrayList<>();
    private ArrayList<String> courseDifficulty = new ArrayList<>();
    private ArrayList<Long> courseVideos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeWidgets();
        checkAuth();

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getFirebaseData() {

        FirebaseDatabase.getInstance().getReference().child(getString(R.string.dbnode_courses))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            fetchData(singleSnapshot);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void fetchData(DataSnapshot singleSnapshot) {
        Course course = singleSnapshot.getValue(Course.class);

        if (course != null) {
            courseName.add(course.getName());
            courseAuthor.add(course.getAuthor());
            courseDifficulty.add(course.getDifficulty());
            courseCode.add(course.getCourse_code());
            courseVideos.add(course.getVideos());

            initialize();
        }

        progressBar.setVisibility(View.GONE);
    }

    private void initialize() {
        mRecyclerView = findViewById(R.id.rvCourseList);
        adapter = new CourseAdapter(this, courseName, courseAuthor, courseDifficulty, courseCode, courseVideos);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeWidgets() {
        mLogout = findViewById(R.id.btnLogout);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
    }

    private void checkAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getFirebaseData();
        }
    }
}
