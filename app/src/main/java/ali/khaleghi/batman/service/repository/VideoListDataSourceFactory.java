package ali.khaleghi.batman.service.repository;

import ali.khaleghi.batman.service.model.video_list.VideoListItem;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class VideoListDataSourceFactory extends DataSource.Factory<Integer, VideoListItem> {

    VideoListDataSource videoListDataSource;
    MutableLiveData<VideoListDataSource> mutableLiveData;

    Context context;

    public VideoListDataSourceFactory(Context context) {
        mutableLiveData = new MutableLiveData<>();
        this.context = context;
    }


    @Override
    public DataSource create() {
        videoListDataSource = new VideoListDataSource(context);
        mutableLiveData.postValue(videoListDataSource);
        return videoListDataSource;
    }

    public MutableLiveData<VideoListDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}