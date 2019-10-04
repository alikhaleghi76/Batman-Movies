package ali.khaleghi.batman.service.repository

import ali.khaleghi.batman.service.model.video_list.VideoListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {

    @GET("/")
    fun getVideoList(@Query("page") page: Int = 1,
                     @Query("apikey") apiKey: String = "3e974fca",
                     @Query("s") searchTerm: String = "batman"): Call<VideoListResponse>


}