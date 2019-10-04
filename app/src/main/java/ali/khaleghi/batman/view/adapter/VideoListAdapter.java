package ali.khaleghi.batman.view.adapter;

import ali.khaleghi.batman.R;
import ali.khaleghi.batman.service.model.video_list.VideoListItem;
import ali.khaleghi.batman.view.ui.activity.MainActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

public class VideoListAdapter extends PagedListAdapter<VideoListItem, VideoListAdapter.VideoListViewHolder> {

    MainActivity activity;

    public VideoListAdapter(MainActivity activity) {
        super(VideoListItem.Companion.getCALLBACK());
        this.activity = activity;
    }

    @NonNull
    @Override
    public VideoListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_video_list, viewGroup, false);
        return new VideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListViewHolder holder, int i) {

        Glide.with(holder.poster)
                .load(getItem(i).getPoster())
                .into(holder.poster);

        holder.name.setText(getItem(i).getTitle());

        holder.year.setText(getItem(i).getYear());

        holder.background.setOnClickListener(v -> activity.loadDetailFragment(getItem(i).getImdbID()));

        if (getItem(i) != null && getItem(i).getType() != null && getItem(i).getType().equals("series")) {
            holder.seriesIcon.setImageResource(R.drawable.ic_series);
            holder.seriesIcon.setVisibility(View.VISIBLE);
        } else if (getItem(i) != null && getItem(i).getType() != null && getItem(i).getType().equals("game")) {
            holder.seriesIcon.setImageResource(R.drawable.ic_game);
            holder.seriesIcon.setVisibility(View.VISIBLE);
        } else holder.seriesIcon.setVisibility(View.GONE);
    }


    public class VideoListViewHolder extends RecyclerView.ViewHolder {
        ImageView poster, seriesIcon;
        TextView name;
        TextView year;
        FrameLayout background;

        public VideoListViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            seriesIcon = itemView.findViewById(R.id.typeIcon);
            name = itemView.findViewById(R.id.titleContainer);
            year = itemView.findViewById(R.id.year);
            background = itemView.findViewById(R.id.background);
        }
    }
}
