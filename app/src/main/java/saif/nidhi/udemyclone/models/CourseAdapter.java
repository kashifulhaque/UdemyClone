package saif.nidhi.udemyclone.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import saif.nidhi.udemyclone.R;
import saif.nidhi.udemyclone.VideoListActivity;

@SuppressWarnings("FieldCanBeLocal")
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    // Model Variables
    private ArrayList<String> courseName;
    private ArrayList<String> courseCode;
    private ArrayList<String> courseAuthor;
    private ArrayList<String> courseDifficulty;
    private ArrayList<Long> courseVideos;
    private Context mContext;

    // Constructors
    public CourseAdapter(Context mContext, ArrayList<String> courseName, ArrayList<String> courseAuthor,
                         ArrayList<String> courseDifficulty, ArrayList<String> courseCode,
                         ArrayList<Long> courseVideos) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseAuthor = courseAuthor;
        this.courseDifficulty = courseDifficulty;
        this.courseVideos = courseVideos;
        this.mContext = mContext;
    }

    // This method is used to create the view and inflate it with values
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_course_item, parent, false);

        return new CourseViewHolder(view);
    }

    // This method binds those controls inside the view to their respective controls
    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, int position) {

        holder.mCourseName.setText(courseName.get(position));
        holder.mCourseAuthor.setText(courseAuthor.get(position));
        holder.mCourseDifficulty.setText(courseDifficulty.get(position));
        holder.mCourseVideos.setText(courseVideos.get(position).toString());

        // onClickListener for every item in the RecyclerView
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCourse(courseCode.get(holder.getAdapterPosition()));
            }
        });

    }

    // This method is used to launch a new Activity which displays the profile of the Course to the manager
    private void launchCourse(String course_code) {
        Intent intent = new Intent(mContext, VideoListActivity.class);
        intent.putExtra("courseCode", course_code);
        mContext.startActivity(intent);
    }

    // This method returns the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return courseName.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        // Widgets
        private TextView mCourseName, mCourseAuthor, mCourseDifficulty, mCourseVideos;
        private CardView mParent;

        public CourseViewHolder(View itemView) {
            super(itemView);

            mCourseName = itemView.findViewById(R.id.tvCourseName);
            mCourseAuthor = itemView.findViewById(R.id.tvCourseAuthor);
            mCourseDifficulty = itemView.findViewById(R.id.tvCourseDifficulty);
            mCourseVideos = itemView.findViewById(R.id.tvCourseVideos);
            mParent = itemView.findViewById(R.id.cvCourses);
        }

    }

}