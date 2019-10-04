package ali.khaleghi.batman.service.model.video_list

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import android.provider.Contacts.Photos
import androidx.annotation.NonNull
import android.R.id
import androidx.recyclerview.widget.DiffUtil



data class VideoListItem(

	@SerializedName("Type")
	val type: String? = null,

	@SerializedName("Year")
	val year: String? = null,

	@SerializedName("imdbID")
	val imdbID: String? = null,

	@SerializedName("Poster")
	val poster: String? = null,

	@SerializedName("Title")
	val title: String? = null

): Serializable {

	companion object {
		val CALLBACK: DiffUtil.ItemCallback<VideoListItem> = object : DiffUtil.ItemCallback<VideoListItem>() {
			override fun areItemsTheSame(videoListItem: VideoListItem, t1: VideoListItem): Boolean {
				return videoListItem.imdbID === t1.imdbID
			}

			override fun areContentsTheSame(videoListItem: VideoListItem, t1: VideoListItem): Boolean {
				return true
			}
		}
	}
}