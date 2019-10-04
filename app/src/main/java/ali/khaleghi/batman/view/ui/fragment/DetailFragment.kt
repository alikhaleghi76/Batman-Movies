package ali.khaleghi.batman.view.ui.fragment

import ali.khaleghi.batman.R
import ali.khaleghi.batman.glide.DrawableAlwaysCrossFadeFactory
import ali.khaleghi.batman.service.model.detail.DetailResponse
import ali.khaleghi.batman.util.NetworkState
import ali.khaleghi.batman.util.Toaster
import ali.khaleghi.batman.util.fadeIn
import ali.khaleghi.batman.util.fadeOut
import ali.khaleghi.batman.view.adapter.RatesAdapter
import ali.khaleghi.batman.view.ui.activity.MainActivity
import ali.khaleghi.batman.viewmodel.DetailViewModel
import ali.khaleghi.batman.viewmodel.ViewModelFactory
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.home_header.*
import kotlin.math.abs
import kotlin.math.roundToInt

class DetailFragment(private val imdbID: String) : BaseFragment() {

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var headerPoster: ImageView
    private lateinit var backButton: ImageView
    private lateinit var title: TextView
    private lateinit var imdbRate: TextView
    private lateinit var year: TextView
    private lateinit var rated: TextView
    private lateinit var duration: TextView
    private lateinit var summary: TextView
    private lateinit var casts: TextView
    private lateinit var director: TextView
    private lateinit var writer: TextView
    private lateinit var typeIcon: ImageView
    private lateinit var rateRecycler: RecyclerView
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var toolbarContainer: LinearLayout
    private lateinit var root: CoordinatorLayout
    private lateinit var infoPlaceholder: View

    private lateinit var skeleton: SkeletonScreen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_detail, container, false)

        headerPoster = layout.findViewById(R.id.header_poster)
        backButton = layout.findViewById(R.id.back_button)
        title = layout.findViewById(R.id.value)
        imdbRate = layout.findViewById(R.id.imdbRate)
        year = layout.findViewById(R.id.year)
        rated = layout.findViewById(R.id.rated)
        duration = layout.findViewById(R.id.duration)
        summary = layout.findViewById(R.id.summary)
        casts = layout.findViewById(R.id.casts)
        director = layout.findViewById(R.id.director)
        director = layout.findViewById(R.id.director)
        writer = layout.findViewById(R.id.writer)
        typeIcon = layout.findViewById(R.id.typeIcon)
        rateRecycler = layout.findViewById(R.id.rateRecycler)
        appBarLayout = layout.findViewById(R.id.appBarLayout)
        toolbar = layout.findViewById(R.id.toolbar)
        toolbarTitle = layout.findViewById(R.id.toolbarTitle)
        toolbarContainer = layout.findViewById(R.id.toolbarContainer)
        root = layout.findViewById(R.id.root)
        infoPlaceholder = layout.findViewById(R.id.info_placeholder)

        backButton.setOnClickListener {
            if (activity is MainActivity)
                (activity as MainActivity).removeDetailFragment()
        }

        toolbar.bringToFront()
        toolbarContainer.bringToFront()


        skeleton = Skeleton.bind(infoPlaceholder)
                .load(R.layout.header_placeholder)
                .shimmer(true)
                .show()

        return layout
    }

    override fun onStart() {
        super.onStart()

        detailViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(activity?.application, imdbID)
        ).get(DetailViewModel::class.java)

        detailViewModel.getObservableDetailResponse().observe(this, Observer<DetailResponse> {
            if (it == null && context != null) {
                if (!NetworkState.isOnline(context!!)) {
                    Toaster.show(context, getString(R.string.check_network))
                    Handler().postDelayed({
                        if (activity is MainActivity) (activity as MainActivity).removeDetailFragment()
                    }, 1000)
                    return@Observer
                }
            }
            initViews(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(detailResponse: DetailResponse?) {
        if (context == null) return
        skeleton.hide()
        Glide.with(context!!).load(detailResponse?.poster)
                .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory(400)))
                .into(headerPoster)

        title.text = detailResponse?.title
        toolbarTitle.text = detailResponse?.title
        imdbRate.text = detailResponse?.imdbRating
        year.text = detailResponse?.year
        rated.text = detailResponse?.rated
        duration.text = detailResponse?.runtime
        summary.text = detailResponse?.plot
        casts.text = "${getString(R.string.staring)}: ${detailResponse?.actors}"
        director.text = "${getString(R.string.director)}: ${detailResponse?.director}"
        writer.text = "${getString(R.string.writer)}: ${detailResponse?.writer}"

        if (detailResponse?.type != null && detailResponse.type == "series") {
            typeIcon.setImageResource(R.drawable.ic_series)
            typeIcon.visibility = View.VISIBLE
        } else if (detailResponse?.type != null && detailResponse.type == "game") {
            typeIcon.setImageResource(R.drawable.ic_game)
            typeIcon.visibility = View.VISIBLE
        } else
            typeIcon.visibility = View.GONE

        if (detailResponse?.ratings != null && detailResponse.ratings.isNotEmpty()) {
            rateRecycler.layoutManager = LinearLayoutManager(context)
            rateRecycler.adapter = RatesAdapter(detailResponse.ratings)
            rateRecycler.isNestedScrollingEnabled = false
        }

        if (detailResponse?.imdbRating?.toFloatOrNull() != null) {
            val rateFloat = detailResponse.imdbRating.toFloat()
            if (rateFloat < 5) {
                imdbRate.setTextColor(resources.getColor(R.color.colorRed))
            }
            else if (rateFloat >= 5 && rateFloat < 7) {
                imdbRate.setTextColor(resources.getColor(R.color.colorOrange))
            }
            else if (rateFloat > 7) {
                imdbRate.setTextColor(resources.getColor(R.color.colorGreen))
            }
        }

        val colorPrimaryDark ="#" + Integer.toHexString(
                        ContextCompat.getColor(context!!, R.color.colorPrimaryDark))

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout, i: Int ->
            val actionBarColor = setColorAlpha(
                    colorPrimaryDark,
                    abs(i / appBarLayout.totalScrollRange.toDouble() * 2)
            )
            toolbar.setBackgroundColor(Color.parseColor(actionBarColor))
            if (abs(i / appBarLayout.totalScrollRange.toDouble() * 2) > 0.5f)
                toolbarTitle.fadeIn()
            else
                toolbarTitle.fadeOut()
        })
    }

    private fun setColorAlpha(originalColor: String, alpha: Double): String {
        var originalColor = originalColor
        if (originalColor.length == 9) originalColor = "#" + originalColor.substring(3, 9)
        var alphaFixed = (alpha * 255).roundToInt()
        if (alphaFixed > 255) alphaFixed = 255
        var alphaHex = java.lang.Long.toHexString(alphaFixed.toLong())
        if (alphaHex.length == 1) {
            alphaHex = "0$alphaHex"
        }
        originalColor = originalColor.replace("#", "#$alphaHex")


        return originalColor
    }

}
