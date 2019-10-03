package ali.khaleghi.batman.service.repository

import android.content.Context

class VideoRepository private constructor(context: Context) {

    init {
        val retrofit = RetrofitConfig().getRetrofitConfig(context)
        videoService = retrofit.create(VideoService::class.java)
    }

    companion object {

        private lateinit var videoService: VideoService

        private var videoRepository: VideoRepository? = null

        @Synchronized
        fun getInstance(context: Context): VideoRepository {

            if (videoRepository == null) {
                videoRepository = VideoRepository(context)
            }
            return videoRepository!!
        }

    }


}
