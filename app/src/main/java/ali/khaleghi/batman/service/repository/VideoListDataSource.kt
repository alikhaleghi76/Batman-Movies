package ali.khaleghi.batman.service.repository

import ali.khaleghi.batman.service.model.video_list.VideoListItem
import ali.khaleghi.batman.service.model.video_list.VideoListResponse
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class VideoListDataSource(private val context: Context) : PageKeyedDataSource<Int, VideoListItem>() {


    //state 0 : not loading
    //state 1 : loading
    //state 2 : error
    companion object {
        val stateLiveData = MutableLiveData<Int>()
    }

    private var videoService: VideoService? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, VideoListItem>) {

        videoService = RetrofitConfig().getRetrofitConfig(context).create()

        stateLiveData.postValue(1)

        videoService?.getVideoList(1)?.enqueue(object : Callback<VideoListResponse> {

            override fun onResponse(call: Call<VideoListResponse>, response: Response<VideoListResponse>) {
                stateLiveData.postValue(0)
                if (response.body()?.search != null) {
                    val data = response.body()?.search
                    callback.onResult(data!!, null, 2)
                }
            }

            override fun onFailure(call: Call<VideoListResponse>, t: Throwable) {
                stateLiveData.postValue(2)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, VideoListItem>) {

        videoService = RetrofitConfig().getRetrofitConfig(context).create()

        stateLiveData.postValue(1)
        videoService?.getVideoList(params.key)?.enqueue(object : Callback<VideoListResponse> {

            override fun onResponse(call: Call<VideoListResponse>, response: Response<VideoListResponse>) {
                stateLiveData.postValue(0)
                if (response.body()?.search != null) {
                    val data = response.body()?.search
                    callback.onResult(data!!, (params.key) + 1)
                }
            }

            override fun onFailure(call: Call<VideoListResponse>, t: Throwable) {
                stateLiveData.postValue(2)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, VideoListItem>) {

    }

}
