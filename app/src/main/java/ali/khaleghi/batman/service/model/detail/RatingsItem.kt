package ali.khaleghi.batman.service.model.detail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RatingsItem(

	@SerializedName("Value")
	val value: String? = null,

	@SerializedName("Source")
	val source: String? = null
): Serializable