package com.mobilemediacomm.netflix.service.repository

import ali.khaleghi.batman.service.model.detail.DetailResponse
import ali.khaleghi.batman.service.repository.RetrofitConfig
import ali.khaleghi.batman.service.repository.VideoService
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoRepository private constructor(context: Context) {

    init {
        val retrofit = RetrofitConfig().getRetrofitConfig(context)
        videoService = retrofit.create(VideoService::class.java)
    }

    companion object {

        private lateinit var videoService: VideoService

        private var videoRepository: VideoRepository? = null

        fun getInstance(context: Context): VideoRepository {
                if (videoRepository == null) {
                    videoRepository = VideoRepository(context)
                }
                return videoRepository!!
            }

    }

    /**
     * Function to get details of a video
     *
     *  @param imdbID: imdb id of video
     */
    fun getVideoDetail(imdbID: String): LiveData<DetailResponse> {

            val data = MutableLiveData<DetailResponse>()

            videoService.getVideoDetail(imdbID).enqueue(object : Callback<DetailResponse> {

                override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {

                }
            })

            return data
        }

}
