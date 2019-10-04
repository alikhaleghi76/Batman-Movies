package ali.khaleghi.batman.viewmodel

import ali.khaleghi.batman.service.model.video_list.VideoListItem
import ali.khaleghi.batman.service.repository.VideoListDataSource
import ali.khaleghi.batman.service.repository.VideoListDataSourceFactory
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.Executor
import androidx.paging.LivePagedListBuilder
import java.util.concurrent.Executors


class VideoListViewModel(app: Application) : AndroidViewModel(app) {

    private var videoListDataSourceFactory: VideoListDataSourceFactory? = null
    private var dataSourceMutableLiveData: MutableLiveData<VideoListDataSource>? = null
    private var executor: Executor? = null
    private var pagedListLiveData: LiveData<PagedList<VideoListItem>>? = null

    private var loadingState: LiveData<Int> = VideoListDataSource.stateLiveData


    init {
        videoListDataSourceFactory = VideoListDataSourceFactory(app.applicationContext)
        dataSourceMutableLiveData = videoListDataSourceFactory?.mutableLiveData

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(2)
            .build()
        executor = Executors.newFixedThreadPool(5)
        pagedListLiveData = LivePagedListBuilder<Int, VideoListItem>(videoListDataSourceFactory!!, config)
            .setFetchExecutor(executor!!)
            .build()

    }

    fun getObservableGroupList(): LiveData<PagedList<VideoListItem>>? {
        return pagedListLiveData
    }

    fun getObservableLoadingState(): LiveData<Int>? {
        return loadingState
    }

}
