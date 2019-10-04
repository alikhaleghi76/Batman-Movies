package ali.khaleghi.batman.service.model.video_list

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoListResponse(

	@SerializedName("Response")
	val response: String? = null,

	@SerializedName("totalResults")
	val totalResults: String? = null,

	@SerializedName("Search")
	val search: List<VideoListItem?>? = null

): Serializable
