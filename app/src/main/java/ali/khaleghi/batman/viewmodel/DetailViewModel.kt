package ali.khaleghi.batman.viewmodel

import ali.khaleghi.batman.service.model.detail.DetailResponse
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mobilemediacomm.netflix.service.repository.VideoRepository


class DetailViewModel(app: Application, imdbID: String) : AndroidViewModel(app) {

    var videos: LiveData<DetailResponse> = VideoRepository.getInstance(app.applicationContext).getVideoDetail(imdbID)

    fun getObservableDetailResponse(): LiveData<DetailResponse> {
        return videos
    }
}
