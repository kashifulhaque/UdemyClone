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
import saif.nidhi.udemyclone.VideoActivity;

@SuppressWarnings("FieldCanBeLocal")
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    // Model Variables
    private ArrayList<String> downloadURL;
    private ArrayList<String> name;
    private ArrayList<Long> number;
    private ArrayList<String> storageLocation;
    private Context mContext;

    // Constructors
    public VideoAdapter(ArrayList<String> downloadURL, ArrayList<String> name,
                        ArrayList<Long> number, ArrayList<String> storageLocation,
                        Context mContext) {
        this.downloadURL = downloadURL;
        this.name = name;
        this.number = number;
        this.storageLocation = storageLocation;
        this.mContext = mContext;
    }

    // This method is used to create the view and inflate it with values
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_course_videos, parent, false);

        return new VideoViewHolder(view);
    }

    // This method binds those controls inside the view to their respective controls
    @Override
    public void onBindViewHolder(@NonNull final VideoViewHolder holder, int position) {

        holder.mName.setText(name.get(position));
        holder.mNumber.setText(number.get(position).toString());

        // onClickListener for every item in the RecyclerView
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchVideo(downloadURL.get(holder.getAdapterPosition()));
            }
        });

    }

    // This method is used to launch a new Activity which displays the profile of the Course to the manager
    private void launchVideo(String url) {
        Intent intent = new Intent(mContext, VideoActivity.class);
        intent.putExtra("downloadURL", url);
        mContext.startActivity(intent);
    }

    // This method returns the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return name.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        // Widgets
        private TextView mNumber, mName;
        private CardView mParent;

        public VideoViewHolder(View itemView) {
            super(itemView);

            mNumber = itemView.findViewById(R.id.tvVideoNumber);
            mName = itemView.findViewById(R.id.tvVideoName);
            mParent = itemView.findViewById(R.id.cvVideos);
        }

    }

}
